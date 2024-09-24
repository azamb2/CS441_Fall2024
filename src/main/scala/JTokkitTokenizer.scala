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

  // Main method for testing
  def main(args: Array[String]): Unit = {
    // Test tokenization of a word
//    val word = "learn to come to school on time"
//    val tokenId = getTokenIds(word)
//    println(s"Token ID for '$word': ${tokenId.mkString("Array(", ", ", ")")}")
//    val str = "44396,  291, 704"
//    val intArray: Array[Int] = str.split(", ").map(_.toInt)
    // Test the function
    val arr = Array(44396, 291, 704)
////    val result = combineNumbers(arr)
//    println(result) // Output: 1345



    // Print the array to verify
//    println(intArray.mkString(", "))

    // Test tokenization of a sentence
//    val sentence = "The Project Gutenberg eBook of The philosophy of Jake Haiden (late Jacob K. Huff) This ebook is for the use of anyone anywhere in the United States and most other parts of the world at no cost and with almost no restrictions whatsoever."
//    val tokenIds = tokenizeSentence(sentence)
//    println(s"Token IDs for '$sentence': ${tokenIds.mkString(", ")}")

    // Test decoding of token IDs
    val decodedSentence = decodeTokens(arr)
    println(s"Decoded sentence: $decodedSentence")

    // Test decoding a single token ID
//    val singleTokenId = tokenIds.head // Get the first token ID for testing
//    decodeToken(singleTokenId)
  }
}
