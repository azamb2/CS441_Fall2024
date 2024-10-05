import org.apache.hadoop.io._
import org.apache.hadoop.mapred._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
//import scala.collection.JavaConverters._

class MapReduceProgramTest extends AnyFlatSpec with Matchers {

  // Test case for TextArrayWritable
  "TextArrayWritable" should "correctly initialize with an array of strings" in {
    val textArrayWritable = new MapReduceProgram.TextArrayWritable(Array("Hello", "World"))
    val output = textArrayWritable.get().map(_.toString).toList

    output shouldEqual List("Hello", "World")
  }

  // Test case for Mapper
  "Mapper" should "produce correct output for given input" in {
    val mapper = new MapReduceProgram.Map()

    // Implementing OutputCollector to collect outputs
    class TestOutputCollector extends OutputCollector[Text, MapReduceProgram.TextArrayWritable] {
      private val collected = scala.collection.mutable.ListBuffer[(Text, MapReduceProgram.TextArrayWritable)]()

      override def collect(key: Text, value: MapReduceProgram.TextArrayWritable): Unit = {
        collected += ((key, value))
      }

      def getCollected: List[(Text, MapReduceProgram.TextArrayWritable)] = collected.toList
    }

    val outputCollector = new TestOutputCollector()

    val inputKey = new LongWritable(1)
    val inputValue = new Text("Hello world")
    mapper.map(inputKey, inputValue, outputCollector, null)

    // Retrieve collected outputs after the map function is called
    val collectedOutputs = outputCollector.getCollected

    collectedOutputs should have size 2
    collectedOutputs.map(_._1.toString.toLowerCase) should contain allOf ("hello", "world")
    collectedOutputs.map(_._2.toString) should contain allOf (
      "[1.0, 1.0, 1.0, 1.0, 1.0]", // Example output for "Hello"
      "[1.0, 1.0, 1.0, 1.0, 1.0]"  // Example output for "world"
    )
  }

  // Test case for Reducer
  "Reducer" should "compute the average embedding correctly" in {
    val reducer = new MapReduceProgram.Reduce()

    // Implementing OutputCollector to collect outputs
    class TestOutputCollector extends OutputCollector[Text, MapReduceProgram.TextArrayWritable] {
      private val collected = scala.collection.mutable.ListBuffer[(Text, MapReduceProgram.TextArrayWritable)]()

      override def collect(key: Text, value: MapReduceProgram.TextArrayWritable): Unit = {
        collected += ((key, value))
      }

      def getCollected: List[(Text, MapReduceProgram.TextArrayWritable)] = collected.toList
    }

    val outputCollector = new TestOutputCollector()

    val key = new Text("hello")
    val values = List(
      new MapReduceProgram.TextArrayWritable(Array("1.0", "1.0", "1.0", "1.0", "2.0")), // embedding + frequency
      new MapReduceProgram.TextArrayWritable(Array("1.0", "2.0", "1.0", "1.0", "3.0"))  // embedding + frequency
    ).asJava.iterator()
    // im pushing
    reducer.reduce(key, values, outputCollector, null)

    // Retrieve collected outputs after the reduce function is called
    val collectedOutputs = outputCollector.getCollected

    collectedOutputs should have size 1
    val resultArray = collectedOutputs.head._2.get()
    resultArray.map(_.toString.toDouble) shouldEqual Array(1.0, 1.5, 1.0, 1.0, 2.5) // Averaged values
  }
}
