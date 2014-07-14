package controllers

import play.api.libs.concurrent.Execution.Implicits.defaultContext

import play.api._
import play.api.mvc._
import play.api.libs.json.Json

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def newEntry = Action(parse.json) {
    implicit request =>
      play.Logger.debug(new java.util.Date().toString + " === " + request.body)
      val fname = request.body.\("first_name")
      val lname = request.body.\("last_name")
      val sex = request.body.\("sex")
      val age = request.body.\("age")
      play.Logger.debug(new java.util.Date().toString + " === " + fname)
      play.Logger.debug(new java.util.Date().toString + " === " + lname)
      play.Logger.debug(new java.util.Date().toString + " === " + sex)
      play.Logger.debug(new java.util.Date().toString + " === " + age)
      val redirect_url = Map("path" -> routes.Application.afterCreate.toString())
      Ok(Json.toJson(redirect_url))
  }

  def afterCreate = Action {
    implicit request =>
      Ok("You have been redirected to here.").as("text/html")
  }

}
