package catexamples

import cats.kernel.Monoid

object SuperAdder {

  import cats.syntax.semigroup.* // for |+|

  // Exercise SuperAdder
  def add[A](items: List[A])(implicit monoid: Monoid[A]) : A = {
    items.foldLeft(Monoid[A].empty)(_ |+| _)
  }

  def main(args: Array[String]): Unit = {
    import cats.instances.int.*
    import cats.instances.option.*
    import cats.syntax.show.* //to provide a monoid for Option

    println(add(List(2, 4, 5)).show)
    println(add(List(Some(2), None, Some(4), Some(5))).show)
  }
}
