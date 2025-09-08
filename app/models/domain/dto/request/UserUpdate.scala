package models.domain.dto.request

import models.domain.entity.User

case class UserUpdate(
    email: Option[String],
    username: Option[String],
    password: Option[String]
) {
  def toDomain(): User = User(
    email.getOrElse(""),
    username.getOrElse(""),
    password.getOrElse("")
  )
}

object UserUpdate {
  def unapply(
      user: UserUpdate
  ): Option[(Option[String], Option[String], Option[String])] =
    Some(Tuple.fromProductTyped(user))
}
