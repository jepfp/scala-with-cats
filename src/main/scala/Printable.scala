import Usage.Cat
import cats.Show
import cats.syntax.show.toShow

// Type class
// API. Defines, what we want to be able to do
trait Printable[A] {
  def format(value: A): String
}

// Type class implementation
// Concrete implementations to handle different types
object PrintableInstances {
  implicit val printString: Printable[String] = (value: String) => "String is: " + value

  implicit val printInt: Printable[Int] = new Printable[Int] {
    override def format(value: Int): String = "Int is: " + value
  }
}

object PrintableWithInterfaceObject {
  // methods that need such an implicit object as an implicit parameter
  // this way how it is done is called with "interface objects"
  def format[A](value: A)(implicit printable: Printable[A]): String = printable.format(value)
  def print[A](value: A)(implicit printable: Printable[A]): Unit = println(printable.format(value))


  import PrintableInstances._
  import Usage._

  def main(args: Array[String]): Unit = {
    val anInt = 6
    val aString = "Hello"

    print(anInt)
    print(aString)

    val cat = Cat("Kleo", 3, "green")

    print(cat)
  }
}

// Second way of how a type class can be used: Interface Syntax
// Provide some extension methods for our Cat
object PrintableSyntax {
  implicit class PrintableOps[A](value: A) {
    def format(implicit printableOp: Printable[A]): String = printableOp.format(value)

    def print(implicit printableOp: Printable[A]): Unit = println(printableOp.format(value))
  }
}

object PrintableWithInterfaceSyntax {
  import Usage._
  import PrintableSyntax._

  def main(args: Array[String]): Unit = {
    val cat = Cat("Kleo", 3, "green")

    cat.print
  }
}

object PrintableWithCats {
  implicit val catShow : Show[Cat] = Show.show(value => s"${value.name} is a ${value.age} year-old ${value.color} cat.")

  def main(args: Array[String]): Unit = {
    val cat = Cat("Kleo", 3, "grey")

    println(cat.show)
  }
}



