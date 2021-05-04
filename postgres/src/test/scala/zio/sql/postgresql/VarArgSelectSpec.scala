package zio.sql.postgresql

import zio.test.Assertion._
import zio.test._

object VarArgSelectSpec extends DefaultRunnableSpec with PostgresModule with ShopSchema {
  import Customers._
  import FunctionDef._

  def spec = suite("Var args calls")(
    test("works with simple select") {
      val customConcat = variadicFunc[String]("concat")
      val query = select(customConcat("Name: ", fName, lName)) from customers
      assert(renderRead(query)) {
        equalTo("SELECT concat('Name: ',customers.first_name,customers.last_name) FROM customers")
      }
    }
  )
}
