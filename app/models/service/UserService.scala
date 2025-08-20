package models.service

import javax.inject.Inject
import javax.inject.Singleton
import java.util.UUID
import java.time.Instant

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import play.api.db.slick.HasDatabaseConfigProvider
import play.api.db.slick.DatabaseConfigProvider

import slick.jdbc.JdbcProfile

import models.domain.entity.User
import models.repo.UserRepo
import models.domain.dto.request.UserCreate

@Singleton
class UserService @Inject() (
    private val userRepo: UserRepo
)(using ExecutionContext) {
  def create(user: UserCreate): Future[Int] =
    userRepo.create(user.toDomain())
}
