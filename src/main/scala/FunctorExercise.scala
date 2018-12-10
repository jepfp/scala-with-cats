import cats.Functor
import cats.syntax.functor._ // syntax to have map available on the branch or leaf


object FunctorExercise {

  sealed trait Tree[+A]

  final case class Branch[A](left: Tree[A], right: Tree[A])
    extends Tree[A]

  final case class Leaf[A](value: A) extends Tree[A]


  object Tree {
    //needed, because the implicit val is for Functor[Tree] but not for Branch or Leaf
    def branch[A](left: Tree[A], right: Tree[A]): Tree[A] =
      Branch(left, right)

    def leaf[A](value: A): Tree[A] =
      Leaf(value)
  }

  implicit val treeFunctor: Functor[Tree] =
    new Functor[Tree] {
      def map[A, B](tree: Tree[A])(func: A => B): Tree[B] =
        tree match {
          case Branch(left, right) => Branch(map(left)(func), map(right)(func))
          case Leaf(v) => Leaf(func(v))
        }
    }

  def main(args: Array[String]): Unit = {

    Tree.leaf(100).map(_ * 2)

    Tree.branch(Tree.leaf(10), Tree.leaf(20)).map(_ * 2)
  }
}
