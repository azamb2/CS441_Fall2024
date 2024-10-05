import org.apache.hadoop.fs.Path
import org.apache.hadoop.io._
import org.apache.hadoop.mapred._
import scala.jdk.CollectionConverters._
import java.io.IOException
import scala.collection.mutable.ArrayBuffer
import org.slf4j.{Logger, LoggerFactory}

object MapReduceProgram {
  // Logger instance
  private val logger: Logger = LoggerFactory.getLogger(MapReduceProgram.getClass)

  // Custom Writable class to hold an array of Text objects
  class TextArrayWritable extends ArrayWritable(classOf[Text]) {
    def this(strings: Array[String]) = {
      this()
      // Convert strings to Text objects and set them in the superclass
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
      // Convert input value to String
      val line: String = value.toString

      // Retrieve word embeddings from Word2Vec application
      val w2Word = Word2VecApp().tokenEmbeddingOne(line)
      Word2VecApp().printVocabulary(w2Word)

      // Process each token in the line
      line.split(" ").foreach { token =>
        val WordOne = token.toLowerCase
        // Get word vector for the token
        val vec = Word2VecApp().getWordVector(w2Word, WordOne).map(_.toString) ++ Array("1.0") // Adding frequency as a string

        // Set the token as the key
        word.set(WordOne)
        // Collect the token and its corresponding embedding
        output.collect(word, new TextArrayWritable(vec))

        // Log the processed token and its vector
        logger.info(s"Processed token: $WordOne with vector: ${vec.mkString(", ")}")
      }
    }
  }

  // Reducer class
  class Reduce extends MapReduceBase with Reducer[Text, TextArrayWritable, Text, TextArrayWritable] {
    override def reduce(key: Text, values: java.util.Iterator[TextArrayWritable], output: OutputCollector[Text, TextArrayWritable], reporter: Reporter): Unit = {
      // Convert iterator to a list for processing
      val embeddings = values.asScala.toList

      // Log the key being reduced and the number of embeddings
      logger.info(s"Reducing key: $key with ${embeddings.length} embeddings")

      // Accumulate embeddings and frequencies
      val summedEmbedding = embeddings.map(_.get().map(_.toString.toDouble).init)
        .reduce((vec1, vec2) => vec1.zip(vec2).map { case (v1, v2) => v1 + v2 })

      // Calculate total frequency
      val totalFrequency = embeddings.map(_.get().last.toString.toDouble).sum

      // Average the embedding by the total frequency
      val averagedEmbedding = summedEmbedding.map(_ / totalFrequency)

      // Prepare the output as TextArrayWritable
      val resultArray = averagedEmbedding.map(_.toString) ++ Array(totalFrequency.toString)
      output.collect(key, new TextArrayWritable(resultArray))

      // Log the result for the key
      logger.info(s"Reduced key: $key with averaged embedding: ${averagedEmbedding.mkString(", ")} and total frequency: $totalFrequency")
    }
  }

  // Main method to run MapReduce
  @main def runMapReduce(inputPath: String, outputPath: String, output2: String): Unit = {
    // Shard the input file for efficient processing
    ShardWords.shardWords(inputPath, 10000, outputPath)

    // Configure the MapReduce job
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

    // Log the configuration and paths
    logger.info(s"Running MapReduce job with input path: $outputPath and output path: $output2")

    // Run the job
    JobClient.runJob(conf)
  }
}
