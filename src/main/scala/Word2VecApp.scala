import org.deeplearning4j.models.word2vec.Word2Vec
import org.deeplearning4j.text.sentenceiterator.CollectionSentenceIterator
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.LowCasePreProcessor
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory
import scala.jdk.CollectionConverters._

/**
 * A utility class for training and interacting with a Word2Vec model using Deeplearning4j.
 */
class Word2VecApp {

  /**
   * Retrieves the word vector for a given word from the Word2Vec model.
   *
   * @param model The trained Word2Vec model.
   * @param word  The word for which to retrieve the vector.
   * @return An array of doubles representing the word vector.
   */
  def getWordVector(model: Word2Vec, word: String): Array[Double] = {
    // Get the word vector for the specified word
    model.getWordVector(word)
  }

  /**
   * Retrieves the sum of the word vector components for a given word.
   *
   * @param model The trained Word2Vec model.
   * @param word  The word for which to compute the sum of vector components.
   * @return The sum of the word vector components as a Double. Returns 0.0 if the word is not in the vocabulary.
   */
  def getWordVectorSum(model: Word2Vec, word: String): Double = {
    // Check if the word exists in the vocabulary
    if (model.hasWord(word)) {
      // Retrieve the word vector and sum its components
      model.getWordVector(word).map(_.toDouble).sum
    } else {
      // Return 0.0 if the word is not found
      0.0
    }
  }

  /**
   * Prints all words in the vocabulary of the Word2Vec model.
   *
   * @param word2Vec The trained Word2Vec model.
   */
  def printVocabulary(word2Vec: Word2Vec): Unit = {
    // Retrieve all words in the vocabulary
    val vocab = word2Vec.getVocab.words()
    println("Vocabulary:")
    // Iterate and print each word
    vocab.asScala.foreach(println)
  }

  /**
   * Retrieves the frequency of a specific word in the Word2Vec model's vocabulary.
   *
   * @param word     The word whose frequency is to be retrieved.
   * @param word2Vec The trained Word2Vec model.
   * @return The frequency of the word as an Int. Returns 0 if the word is not in the vocabulary.
   */
  def getWordFrequency(word: String, word2Vec: Word2Vec): Int = {
    // Check if the word exists in the vocabulary
    if (word2Vec.hasWord(word)) {
      // Retrieve the VocabWord object associated with the word
      val vocabWord = word2Vec.getVocab.wordFor(word)
      // Get the frequency from the VocabWord object
      vocabWord.getElementFrequency.toInt
    } else {
      // Return 0 if the word is not found
      0
    }
  }

  /**
   * Trains a Word2Vec model on a single sentence and returns the trained model.
   *
   * @param sentence The input sentence for training the Word2Vec model.
   * @return The trained Word2Vec model.
   */
  def tokenEmbeddingOne(sentence: String): Word2Vec = {
    // Wrap the sentence into a List and convert it to a Java Collection
    val sentenceList = List(sentence).asJava

    // Initialize the tokenizer factory and set the preprocessor to lowercase all tokens
    val tokenizerFactory = new DefaultTokenizerFactory()
    tokenizerFactory.setTokenPreProcessor(new LowCasePreProcessor())

    // Create a sentence iterator from the collection of sentences
    val sentenceIterator = new CollectionSentenceIterator(sentenceList)

    // Configure and build the Word2Vec model with specified parameters
    val word2Vec = new Word2Vec.Builder()
      .minWordFrequency(1)             // Minimum word frequency to consider
      .tokenizerFactory(tokenizerFactory) // Tokenizer factory with preprocessor
      .iterate(sentenceIterator)       // Sentence iterator
      .layerSize(100)                  // Size of the word vectors
      .windowSize(5)                   // Window size for context words
      .seed(42)                        // Seed for reproducibility
      .build()

    // Train the Word2Vec model on the provided data
    println("Training Word2Vec model...")
    word2Vec.fit()
    println("Model training complete.")

    // Return the trained Word2Vec model
    word2Vec
  }
}
