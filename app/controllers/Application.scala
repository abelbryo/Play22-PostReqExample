package controllers

import play.api.libs.concurrent.Execution.Implicits.defaultContext

import play.api._
import play.api.mvc._
import play.api.Play.current
import play.api.libs.json.Json
import play.api.libs.ws._
import scala.concurrent.Future
import java.net.URLEncoder
import play.api.libs.iteratee._

import java.util.Properties
import net.tanesha.recaptcha.ReCaptchaFactory
import net.tanesha.recaptcha.ReCaptchaImpl
import net.tanesha.recaptcha.ReCaptchaResponse

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def newEntry = Action(parse.json) {
    implicit request =>

      play.Logger.debug(new java.util.Date().toString + " === " + request.body)

      val response = request.body.\("recaptcha_response_field").toString
      val challenge = request.body.\("recaptcha_challenge_field").toString
      val address = request.remoteAddress

      val privateKey = current.configuration.getString("recaptcha.privatekey").get

      play.Logger.debug(privateKey)

      val postParameters = Map(
        "privatekey" -> Seq(privateKey),
        "remoteip" -> Seq(address),
        "challenge" -> Seq(challenge),
        "response" -> Seq(response))

      WS.url("http://www.google.com/recaptcha/api/verify").post(postParameters).map {
        response =>
          play.Logger.debug(response.body.toString)
      }

      /// WS.url("http://www.google.com/recaptcha/api/verify").postAndRetrieveStream(postParameters) {
      ///     headers =>
      ///       Iteratee.foreach { byte =>
      ///          val response = new String(byte.map(_.toChar))
      ///          val responseByte = response.toCharArray.map(_.toByte)
      ///           play.Logger.debug(response)
      ///           println(responseByte.length)
      ///           println(byte.length)
      ///       }
      ///   }

      val redirect_url = Map("path" -> routes.Application.afterCreate.toString())

      Ok(Json.toJson(redirect_url))

  }

  def afterCreate = Action {
    implicit request =>
      Ok("You have been redirected to here.").as("text/html")
  }

}
