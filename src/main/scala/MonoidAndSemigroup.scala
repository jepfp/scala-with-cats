class MonoidAndSemigroup {

  trait Semigroup[A] {
    def combine(x: A, y: A): A
  }

  trait Monoid[A] extends Semigroup[A] {
    def empty: A
  }

  object Monoid {
    def apply[A](implicit monoid: Monoid[A]) =
      monoid
  }

  // Exercise for Boolean

  class booleanAndMonoid extends Monoid[Boolean] {
    override def empty: Boolean = true

    override def combine(x: Boolean, y: Boolean): Boolean = x && y
  }

  //...

  // Exercise for Sets

  //We need to deﬁne setUnionMonoid as a method rather than a value so we can accept the type parameter A
  implicit def setUnioneMonoid[A]: Monoid[Set[A]] =
    new Monoid[Set[A]] {
      override def empty: Set[A] = Set.empty

      override def combine(x: Set[A], y: Set[A]): Set[A] = x union y
    }

  //shows the combination of two monoids
  class intAddMonoid extends Monoid[Int] {
    override def empty: Int = 0

    override def combine(x: Int, y: Int): Int = x + y
  }

  val intSetMonoid = Monoid[Set[Int]]

  // Set intersection form a Semigroup (no monoid because there is no empty element
  implicit def setIntersectionSemigroup[A]: Semigroup[Set[A]] = (x: Set[A], y: Set[A]) => x intersect y
}
