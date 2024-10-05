import org.apache.hadoop.fs.Path
import org.apache.hadoop.io._
import org.apache.hadoop.mapred._
import scala.jdk.CollectionConverters._
import java.io.IOException
import scala.collection.mutable.ArrayBuffer

object MapReduceProgram {

  // Custom Writable class to hold an array of Text objects
  class TextArrayWritable extends ArrayWritable(classOf[Text]) {
    def this(strings: Array[String]) = {
      this()
      val texts = strings.map(new Text(_))
      super.set(texts.asInstanceOf[Array[Writable]])
    }
    // Override toString for better logging (for debug purposes)
    override def toString: String = this.get().map(_.toString).mkString("[", ", ", "]")
  }

  // Mapper class
  class Map extends MapReduceBase with Mapper[LongWritable, Text, Text, TextArrayWritable] {
    private val word = new Text()

    @throws[IOException]
    override def map(key: LongWritable, value: Text, output: OutputCollector[Text, TextArrayWritable], reporter: Reporter): Unit = {
      val line: String = value.toString
      val w2Word = Word2VecApp().tokenEmbeddingOne(line)
      Word2VecApp().printVocabulary(w2Word)

      line.split(" ").foreach { token =>
        val WordOne = token.toLowerCase
        val vec = Word2VecApp().getWordVector(w2Word, WordOne).map(_.toString) ++ Array("1.0") // Embedding as String array + frequency
        word.set(WordOne) // Set the token as the key
        output.collect(word, new TextArrayWritable(vec)) // Collect token and its embedding
      }
    }
  }

  // Reducer class
  class Reduce extends MapReduceBase with Reducer[Text, TextArrayWritable, Text, TextArrayWritable] {
    override def reduce(key: Text, values: java.util.Iterator[TextArrayWritable], output: OutputCollector[Text, TextArrayWritable], reporter: Reporter): Unit = {
      val embeddings = values.asScala.toList
      // Accumulate embeddings and frequencies
      val summedEmbedding = embeddings.map(_.get().map(_.toString.toDouble).init)
        .reduce((vec1, vec2) => vec1.zip(vec2).map { case (v1, v2) => v1 + v2 })
      val totalFrequency = embeddings.map(_.get().last.toString.toDouble).sum
      // Average the embedding by the total frequency
      val averagedEmbedding = summedEmbedding.map(_ / totalFrequency)
      // Prepare the output as TextArrayWritable
      val resultArray = averagedEmbedding.map(_.toString) ++ Array(totalFrequency.toString)
      output.collect(key, new TextArrayWritable(resultArray))
    }
  }

  // Main method to run MapReduce
  @main def runMapReduce(inputPath: String, outputPath: String, output2: String): Unit = {
    ShardWords.shardWords(inputPath, 10000, outputPath) // Sharding the input file
    val conf: JobConf = new JobConf(this.getClass)
    conf.setJobName("TokenCount")
    conf.set("fs.defaultFS", "local")
    conf.set("mapreduce.job.maps", "1")
    conf.set("mapreduce.job.reduces", "1")
    conf.setOutputKeyClass(classOf[Text])
    conf.setOutputValueClass(classOf[TextArrayWritable]) // Output is now TextArrayWritable
    conf.setMapperClass(classOf[Map])
    conf.setReducerClass(classOf[Reduce])
    conf.setInputFormat(classOf[TextInputFormat])
    conf.setOutputFormat(classOf[TextOutputFormat[Text, TextArrayWritable]])
    // Set input and output paths
    FileInputFormat.setInputPaths(conf, new Path(outputPath))
    FileOutputFormat.setOutputPath(conf, new Path(output2))
    // Run the job
    JobClient.runJob(conf)
  }
}
