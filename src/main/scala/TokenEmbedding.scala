import breeze.linalg._

object TokenEmbedding {

  // Assume you have a pre-trained embedding matrix
  // For simplicity, we'll generate random embeddings for each token
  def getEmbeddingMatrix(tokenId: Int, embeddingSize: Int = 512): DenseVector[Double] = {
    // Random embedding for now
    DenseVector.rand(embeddingSize)
  }
  // Create a tensor for a batch of token sequences (N, L, E)
  def createTensor(tokenSequences: Array[Array[Int]], embeddingSize: Int): DenseMatrix[Double] = {
    val batchSize = tokenSequences.length
    val sequenceLength = tokenSequences.head.length
    val tensor = DenseMatrix.zeros[Double](batchSize * sequenceLength, embeddingSize)

    // Fill the tensor with token embeddings
    for (i <- tokenSequences.indices; j <- tokenSequences(i).indices) {
      val tokenId = tokenSequences(i)(j)
      tensor(i * sequenceLength + j, ::) := getEmbeddingMatrix(tokenId, embeddingSize).t
    }
    tensor
  }
  // Example of sliding window token sequences to tensor
  def slidingWindowToTensor(slidingWindows: Array[Array[Int]], embeddingSize: Int): DenseMatrix[Double] = {
    createTensor(slidingWindows, embeddingSize)
  }
}
