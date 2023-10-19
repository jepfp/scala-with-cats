package catexamples

// Define a simplified Profunctor trait
//    B --> C - the function 'wrapped' in the the profunctor
//    A --> B - a function that allows to preprocess the input for the wrapped function (contravariant part of the profunctor)
//    C --> D - a function that allows to postprocess the output for the wrapped function (covariant part of the profunctor, which is equivalent to the map of a functor)

trait Profunctor[P[_, _]] {
  def dimap[A, B, C, D](p: P[B, C])(f: A => B)(g: C => D): P[A, D]
}

// Define a data processor type
case class DataProcessor[A, B](process: A => Either[String, B])

// Make DataProcessor a Profunctor
implicit val dataProcessorProfunctor: Profunctor[DataProcessor] = new Profunctor[DataProcessor] {
  def dimap[A, B, C, D](p: DataProcessor[B, C])(f: A => B)(g: C => D): DataProcessor[A, D] = {
    DataProcessor(a => p.process(f(a)).map(g))
  }
}

// Example usage
case class RawInput(value: String)

case class ValidatedData(value: String)

case class FormattedOutput(value: String)

def validate(input: RawInput): Either[String, ValidatedData] = {
  if (input.value.nonEmpty) Right(ValidatedData(input.value))
  else Left("Validation Error: Input is empty")
}

def format(data: ValidatedData): FormattedOutput = {
  FormattedOutput(s"Formatted: ${data.value}")
}

def preprocess(input: String): RawInput = {
  RawInput(input.trim)
}

// Create a DataProcessor
val processor: DataProcessor[RawInput, ValidatedData] = DataProcessor(validate)

// Use dimap to create a processing pipeline
val pipeline: DataProcessor[String, FormattedOutput] = dataProcessorProfunctor.dimap(processor)(preprocess)(format)


object ProfunctorMain {
  def main(args: Array[String]): Unit = {
    // Happy path
    pipeline.process("abc") match {
      case Right(output) => println(output.value)
      case Left(error) => println(error)
    }

    // Error path
    pipeline.process(" ") match {
      case Right(output) => println(output.value)
      case Left(error) => println(error)
    }
  }
}