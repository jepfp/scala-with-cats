import cats.kernel.Monoid

object SuperAdder {

  import cats.syntax.semigroup._ // for |+|

  // Exercise SuperAdder
  def add[A](items: List[A])(implicit monoid: Monoid[A]) : A = {
    items.foldLeft(Monoid[A].empty)(_ |+| _)
  }

  def main(args: Array[String]): Unit = {
    import cats.syntax.show._ //for show
    import cats.instances.int._ //to provide a monoid for Int
    import cats.instances.option._ //to provide a monoid for Option

    println(add(List(2, 4, 5)).show)
    println(add(List(Some(2), None, Some(4), Some(5))).show)
  }
}
