package catexamples



object Usage {
  implicit val catPrintable: Printable[Cat] = (value: Cat) => s"${value.name} is a ${value.age} year-old ${value.color} cat."

  final case class Cat(name: String, age: Int, color: String)


}
