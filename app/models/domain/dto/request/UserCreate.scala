package models.domain.dto.request

import play.api.libs.json.Format
import play.api.libs.json.Json

import models.domain.entity.User

case class UserCreate(
    email: String,
    username: String,
    password: String
) {
  def toDomain(): User = User(
    email,
    username,
    password
  )
}

object UserCreate {
  def unapply(user: UserCreate): Option[(String, String, String)] = Some(
    Tuple.fromProductTyped(user)
  )
  given userCreateFormat: Format[UserCreate] = Json.format[UserCreate]
}
