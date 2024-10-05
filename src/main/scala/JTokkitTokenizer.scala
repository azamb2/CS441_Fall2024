import com.knuddels.jtokkit.Encodings
import com.knuddels.jtokkit.api.{EncodingType, IntArrayList}
import scala.jdk.CollectionConverters._

object JTokkitTokenizer {
  // Initialize the encoding registry
  private val encodingRegistry = Encodings.newDefaultEncodingRegistry()
  // Get the tokenizer for a specific encoding type (e.g., CL100K_BASE)
  private val tokenizer = encodingRegistry.getEncoding(EncodingType.CL100K_BASE)

  // Function to tokenize a single word and return its token ID
  def getTokenIds(word: String): String = {
    val tokens = tokenizer.encode(word).toString // Tokenize the word
    if (tokens.isEmpty) {
      throw new IllegalArgumentException("The input string did not generate any tokens.")
    }
//    val tok = tokens.to
//    println(tok)
    // Print all token IDs for debugging
//    println(s"Token IDs for '$word': ${tokens.toArray.mkString(", ")}")
    // Return all token IDs as an array
    tokens
  }

//  def combineNumbers(arr: Array[Int]): Int = {
//    val concatenatedString = arr.map(_.toString).mkString("")
//    val combinedInt = concatenatedString.toInt
//    combinedInt
//  }

  // Function to tokenize a full sentence and return an array of token IDs
  def tokenizeSentence(sentence: String): Array[Int] = {
    val tokens = tokenizer.encode(sentence) // Tokenize the sentence
    if (tokens.isEmpty) {
      throw new IllegalArgumentException("The input sentence did not generate any tokens.")
    }
    tokens.toArray// Convert IntArrayList to Array[Int]
  }

  // Function to decode a sequence of token IDs back to a string
  def decodeTokens(tokenIds: Array[Int]): String = {
    val tokenList = new IntArrayList()
    tokenIds.foreach(tokenList.add) // Add all token IDs to IntArrayList
    val decodedText = tokenizer.decode(tokenList) // Decode the token IDs
    println(s"Decoded string: $decodedText")
    decodedText
  }

  // Function to decode a single token ID
  def decodeToken(tokenId: Int): String = {
    val tokenList = new IntArrayList()
    tokenList.add(tokenId) // Add the single token ID to IntArrayList
    val decodedText = tokenizer.decode(tokenList) // Decode the token ID
//    println(s"Decoded token ID $tokenId: $decodedText") // Print the decoded string
    decodedText
  }

}
