import caliban.beerdb.{Beer, Queries, UntappdDetails}
import caliban.client.SelectionBuilder
import sttp.client3.UriContext
import cats.effect._
import sttp.client3.http4s.Http4sBackend

//noinspection ScalaWeakerAccess
object Main extends IOApp.Simple {
  // See https://beerdb.gregd.me/graphiql
  val serverUrl = uri"https://beerdb.gregd.me/api/graphql"

  case class UntappdView(style: String)

  case class BeerView(brewery: String, name: String, untappd: Option[UntappdView])

  val untappdView: SelectionBuilder[UntappdDetails, UntappdView] = UntappdDetails.style.map(UntappdView)
  val beerView: SelectionBuilder[Beer, BeerView] = (Beer.brewery ~ Beer.name ~ Beer.untappd(untappdView)).mapN(BeerView)

  def run: IO[Unit] = Http4sBackend.usingDefaultEmberClientBuilder[IO]().use { backend =>
    val request = Queries.getLatestBeers(beerView).toRequest(serverUrl)
    for {
      resp <- backend.send(request)
      beers <- IO.fromEither(resp.body)
      _ <- IO.println(beers)
    } yield ()
  }
}