package models.domain.entity

import java.util.UUID
import java.time.Instant

import play.api.libs.json.Writes
import play.api.libs.json.JsValue
import play.api.libs.json.Json

case class User(
    id: UUID,
    email: String,
    username: String,
    password: String,
    createdAt: Instant,
    updatedAt: Instant
) {}

object User {
  def apply(email: String, username: String, password: String): User = User(
    UUID.randomUUID(),
    email,
    username,
    password,
    Instant.now(),
    Instant.now()
  )
  given Writes[User] = new Writes[User] {
    def writes(o: User): JsValue = Json.obj(
      "id"       -> o.id,
      "email"    -> o.email,
      "username" -> o.username
    )
  }
}
