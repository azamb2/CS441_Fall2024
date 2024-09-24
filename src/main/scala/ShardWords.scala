import java.io.{File, PrintWriter}
import scala.io.Source

object ShardWords {
  def shardWords(inputFile: String, shardSize: Int, outputDirectory: String): Unit = {
    val source = Source.fromFile(new File(inputFile))
    // Read the entire file, replacing multiple newlines with a single one
    val text = source.getLines().mkString("\n").replaceAll("\\n+", "\n")
    source.close()

    // Split the text into words, keeping punctuation and symbols but not multiple newlines
    val words = text.split("\\s+").filter(_.nonEmpty).toList

    words.grouped(shardSize).zipWithIndex.foreach { case (shard, index) =>
      val outputFile = new File(outputDirectory, s"shard_${index + 1}.txt")  // Create a unique filename in the specified directory
      val writer = new PrintWriter(outputFile)
      writer.println(shard.mkString(" "))
      writer.close()
      //      println(s"Shard ${index + 1} written to ${outputFile.getAbsolutePath}")
    }
  }
}
