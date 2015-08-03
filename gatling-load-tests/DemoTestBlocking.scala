package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class DemoTestBlocking extends Simulation {

  val rampUpTimeSecs = 1
  val noOfUsers      = 9000

  val baseURL      = "http://localhost:8181"
  val baseName     = "demo-test"
  val requestName  = baseName + "-blocking-request"
  val scenarioName = baseName + "-blocking-scenario"
  val URI          = baseURL + "/demo/blocking"

  
  object DemoRequest {
    val request = exec(http(requestName)
       .get(URI))
  }  
  
  val httpConf = http
    .baseURL(baseURL)
    .acceptHeader("application/json;q=0.9,*/*;q=0.8")
	.contentTypeHeader("text/json;charset=UTF-8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
  
  
  val user = scenario(scenarioName).exec(DemoRequest.request)
  
  
  setUp(
    user.inject(rampUsers(noOfUsers) over (rampUpTimeSecs))
  ).protocols(httpConf)

}
