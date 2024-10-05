//import org.nd4j.linalg.factory.Nd4j
//import org.nd4j.linalg.api.ndarray.INDArray
//import org.deeplearning4j.nn.conf.NeuralNetConfiguration
//import org.deeplearning4j.nn.conf.MultiLayerConfiguration
//import org.deeplearning4j.nn.conf.layers.EmbeddingLayer
//import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
//import org.nd4j.linalg.activations.Activation
//
//object EmbeddingTest {
//  def main(args: Array[String]): Unit = {
//    // Sample tokenized sentences and corresponding labels
//    val tokenizedSentences: Array[Array[Int]] = Array(
//      Array(1, 2, 3, 4),  // Example tokenized sentence 1
//      Array(2, 3, 4, 5),  // Example tokenized sentence 2
//      Array(3, 4, 5, 6),  // Example tokenized sentence 3
//      Array(4, 5, 6, 7)   // Example tokenized sentence 4
//    )
//
//    val labels: Array[Array[Int]] = Array(
//      Array(2, 3, 4, 5),  // Shifted input as labels for example
//      Array(3, 4, 5, 6),
//      Array(4, 5, 6, 7),
//      Array(5, 6, 7, 8)
//    )
//
//    // Parameters
//    val vocabSize = 10   // Total number of unique tokens in your vocabulary (e.g., 1-9, plus 0 for padding)
//    val embeddingDim = 5 // Size of the embedding vectors
//
//     //Create INDArray from tokenized sentences and labels
////    val inputFeature = Nd4j.create(tokenizedSentences)
////    val outputLabels = Nd4j.create(labels);
//
//    // Create INDArray from tokenized sentences and labels
////    val inputFeatures: INDArray = Nd4j.create(tokenizedSentences.flatten.map(_.toDouble), Array(tokenizedSentences.length, tokenizedSentences(0).length))
////    val outputLabels: INDArray = Nd4j.create(labels.flatten.map(_.toDouble), Array(labels.length, labels(0).length))
//
//    //    // Build the neural network configuration
////    val config: MultiLayerConfiguration = new NeuralNetConfiguration.Builder()
////      .list()
////      .layer(new EmbeddingLayer.Builder()
////        .nIn(vocabSize) // Input size (vocabulary size)
////        .nOut(embeddingDim) // Output size (embedding dimensions)
////        .activation(Activation.IDENTITY) // No activation function
////        .build())
////      .build()
////    val config = new NeuralNetConfiguration.Builder()
////      .list()
////      .layer(new EmbeddingLayer.Builder()
////        .nIn(vocabSize) // Input size (vocabulary size)
////        .nOut(embeddingDim) // Output size (embedding dimensions)
////        .activation(Activation.IDENTITY) // No activation function
////        .build())
////      .build();
////    // Initialize the model
////    val model: MultiLayerNetwork = new MultiLayerNetwork(config)
////    model.init()
////
////    // Number of training epochs
//////    val numEpochs = 100
//////    for (epoch <- 0 until numEpochs) {
//////      model.fit(inputFeatures, outputLabels) // Train the model
//////    }
////    // Retrieve learned embeddings
//////    val embeddings: INDArray = model.getLayer(0).getParam("W")
//////    println("Learned Embeddings:\n" + embeddings)
//  }
//}
