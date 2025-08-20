package models.repo

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

@Singleton
class UserRepo @Inject() (
    protected val dbConfigProvider: DatabaseConfigProvider
)(using ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  import dbConfig.profile.api.*

  class UserTable(tag: Tag) extends Table[User](tag, "USERS") {
    def id        = column[UUID]("ID", O.PrimaryKey)
    def email     = column[String]("EMAIL")
    def username  = column[String]("USERNAME")
    def password  = column[String]("PASSWORD")
    def createdAt = column[Instant]("CREATED_AT")
    def updatedAt = column[Instant]("UPDATED_AT")

    def * = (id, email, username, password, createdAt, updatedAt).mapTo[User]
  }

  lazy val users = TableQuery[UserTable]

  def get(id: UUID): Future[User] =
    db.run(users.filter(_.id === id).result.head)

  def create(user: User): Future[Int] =
    db.run(users += user)
}
