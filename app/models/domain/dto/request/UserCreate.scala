package models.domain.dto.request

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
