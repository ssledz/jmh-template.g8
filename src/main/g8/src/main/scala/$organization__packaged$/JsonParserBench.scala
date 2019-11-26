package $organization$

import java.util.concurrent.TimeUnit

import com.github.plokhotnyuk.jsoniter_scala.core.readFromString

import io.circe._
import io.circe.generic.semiauto._
import io.circe.parser.decode
import org.openjdk.jmh.annotations._
import JsonParserBench.Person

@State(Scope.Thread)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Array(Mode.Throughput))
class JsonParserBench {

  val json =
    """
      |{
      |  "username" : "Slavik",
      |  "address" : {
      |    "street" : "Aleje Jerozolimskie",
      |    "city" : "Warsaw"
      |  }
      |}
      |""".stripMargin

  @Benchmark
  def circeBench: Either[Error, Person] = {
    decode[Person](json)
  }

  @Benchmark
  def jsoniterBench: Person = {
    readFromString[Person](json)
  }

}

object JsonParserBench {

  import com.github.plokhotnyuk.jsoniter_scala.core._
  import com.github.plokhotnyuk.jsoniter_scala.macros._

  implicit lazy val personDecoder: Decoder[Person] = deriveDecoder[Person]

  implicit lazy val addressDecoder: Decoder[Address] = deriveDecoder[Address]

  implicit val codec: JsonValueCodec[Person] = JsonCodecMaker.make[Person](CodecMakerConfig)

  case class Person(username: String, address: Address, email: Option[String])

  case class Address(street: String, city: String)

}