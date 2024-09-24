import org.apache.hadoop.fs.Path
import org.apache.hadoop.conf._
import org.apache.hadoop.io._
import org.apache.hadoop.util._
import org.apache.hadoop.mapred._
import opennlp.tools.tokenize.{TokenizerME, TokenizerModel}
import java.io.FileInputStream
import java.io.IOException
import java.util
import scala.jdk.CollectionConverters._
import scala.io.Source
import scala.collection.mutable.ArrayBuffer

object MapReduceProgram:
  val keep = ArrayBuffer[Int]()
  class Map extends MapReduceBase with Mapper[LongWritable, Text, Text, IntWritable]:
//    private final val one = new IntWritable(counter)
    private val word = new Text()
    val SlidingWindow = 3;
    @throws[IOException]
    override def map(key: LongWritable, value: Text, output: OutputCollector[Text, IntWritable], reporter: Reporter): Unit = {
      val line: String = value.toString
      // Split the line while keeping spaces
      var counter = 0
      // Set the token (word) in Hadoop's Text object
        // Get all token IDs for the current token (assuming this returns an Array of Int)
        val tokenIds = JTokkitTokenizer.tokenizeSentence(line) // Assuming it returns an array of Int
        // sliding window
        for (i <- 0 until(tokenIds.length - SlidingWindow + 1)) {
          val currentCount = new IntWritable(counter)
          val window = tokenIds.slice(i,i + SlidingWindow)
          word.set(window.mkString("Array(", ", ", ")"))
//          println(s"${window.mkString("Array(", ", ", ")")}, $counter")
          output.collect(word, currentCount)
          counter += 1
        }
        println(tokenIds.length)
    }

  class Reduce extends MapReduceBase with Reducer[Text, IntWritable, Text, IntWritable]:
    override def reduce(key: Text, values: util.Iterator[IntWritable], output: OutputCollector[Text, IntWritable], reporter: Reporter): Unit = {
      // Convert Java iterator to Scala iterable
      val valuesList = values.asScala
      val sum = valuesList.map(_.get()).sum
//      println(s"Key: ${key.toString}, Sum: $sum")
      output.collect(key, new IntWritable(sum))
//      // Print the word and its associated token IDs
//      println(s"Word: ${key.toString}, Token IDs: ${tokenIds.mkString(", ")}")
//      // Emit each token ID individually
//      tokenIds.foreach { tokenId =>
//        output.collect(key, new IntWritable(tokenIds)) // Emit each token ID with its word
//      }
    }

  @main def runMapReduce(inputPath: String, outputPath: String, output2: String): Unit =
    // Call shardWords from the ShardWords object
    ShardWords.shardWords(inputPath, 10000, outputPath)

    // Configure Hadoop job
    val conf: JobConf = new JobConf(this.getClass)
    conf.setJobName("TokenCount")
    conf.set("fs.defaultFS", "local")
    conf.set("mapreduce.job.maps", "1")
    conf.set("mapreduce.job.reduces", "1")
    conf.setOutputKeyClass(classOf[Text])
    conf.setOutputValueClass(classOf[IntWritable])
    conf.setMapperClass(classOf[Map])
    conf.setReducerClass(classOf[Reduce])
    conf.setInputFormat(classOf[TextInputFormat])
    conf.setOutputFormat(classOf[TextOutputFormat[Text, IntWritable]])

    // Set input and output paths for the job
    FileInputFormat.setInputPaths(conf, new Path(outputPath))
    FileOutputFormat.setOutputPath(conf, new Path(output2))

    // Run the Hadoop job
    JobClient.runJob(conf)
