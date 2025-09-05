package controllers

import javax.inject.Singleton
import javax.inject.Inject

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import play.api.mvc.*
import play.api.mvc.ControllerComponents
import play.api.mvc.BaseController
import play.api.mvc.Request
import play.api.mvc.AnyContent
import play.api.libs.json.*
import play.api.data.*
import play.api.data.Forms.*
import play.api.i18n.*

import models.domain.dto.request.UserCreate

@Singleton
class UserController @Inject() (
    val controllerComponents: ControllerComponents,
    private val userService: models.service.UserService
)(using ExecutionContext)
    extends BaseController
    with I18nSupport {

  def create(): Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[UserCreate] match {
      case JsSuccess(userCreate, _) =>
        userService
          .create(userCreate)
          .map { userId =>
            Created(
              Json.obj("id" -> userId, "message" -> "User created successfully")
            )
          }
          .recover { case ex: Exception =>
            InternalServerError(Json.obj("error" -> ex.getMessage))
          }
      case JsError(errors) =>
        Future.successful(
          BadRequest(Json.obj("errors" -> JsError.toJson(errors)))
        )
    }
  }

  def create1() = Action.async { implicit request =>
    userCreateForm
      .bindFromRequest()
      .fold(
        formWithErrors => {
          Future.successful(
            BadRequest(Json.obj("errors" -> formWithErrors.errorsAsJson))
          )
        },
        userData => {
          userService
            .create(userData)
            .map { success =>
              Created(
                Json
                  .obj("id" -> success, "message" -> "User created successfully")
              )
            }
            .recover { case ex: Exception =>
              InternalServerError(Json.obj("error" -> ex.getMessage))
            }
        }
      )
  }

}

val userCreateForm = Form(
  mapping(
    "email"    -> email,
    "username" -> nonEmptyText,
    "password" -> nonEmptyText(minLength = 6)
  )(UserCreate.apply)(UserCreate.unapply)
)
