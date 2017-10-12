package iot.pood.management.security

import com.typesafe.config.ConfigFactory
import iot.pood.base.exception.Exceptions.IncorrectConfigurationException
import iot.pood.base.model.security.SecurityMessages.JwtToken
import iot.pood.management.base.BaseTest

import scala.concurrent.duration
import scala.concurrent.duration.Duration

/**
  * Created by rafik on 12.10.2017.
  */
class SecurityConfigTest extends BaseTest{

  "Security config" should "return correct configuration" in {

    val config = ConfigFactory.parseString(
      """
        |security {
        |  expiration = 2 seconds
        |  secret_key = "thisjusasodifsodifj"
        |  header = "HS256"
        |}
      """.stripMargin)
      config.getConfig("security")
    val securityConfig = SecurityConfig.securityConfig(config)
    securityConfig shouldBe a [SecurityConfig]
    securityConfig.expiration.toSeconds equals (2)
    securityConfig.secretKey should === ("thisjusasodifsodifj")
  }

  it should "throw exception IncorrectConfigurationException" in {

    val config = ConfigFactory.parseString(
      """
        |security {
        |  expiration = 2 xxxx
        |  secret_key = "thisjusasodifsodifj"
        |  header = "HS256"
        |}
      """.stripMargin)
    a [IncorrectConfigurationException] shouldBe thrownBy {
      SecurityConfig.securityConfig(config)
    }
  }
}
