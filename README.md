# MapReduce Program for Word2Vec Processing

**Name:** Abdul Gaffir Zambi

## Description
This project implements a MapReduce program using Scala and Apache Hadoop to process text data with a focus on Word2Vec embeddings. The program tokenizes input text, retrieves word embeddings, and computes averaged embeddings for each unique token. It is designed for scalability, allowing efficient processing of large datasets through sharding.

## Instructions

### Development Environment Setup
1. **Install Required Tools**:
   - IntelliJ IDEA
   - JDK (Java Development Kit)
   - Scala runtime
   - IntelliJ Scala plugin
   - Simple Build Toolkit (SBT)
   - Ensure you have configured Scala monitoring tools for optimal functionality.

2. **Open the Project**:
   - Launch IntelliJ and open the project directory.

3. **Build the Project**:
   - Initiate the project build process. This may take some time as it involves downloading and incorporating library dependencies listed in the `build.sbt` file into the project's classpath.

### Alternative Command Line Execution
If you prefer running the project via the command line:
1. Open a terminal and navigate to the project directory.
2. Execute the following command to build the project:
   ```bash
   sbt clean compile assembly
Configuration
Before running the program, modify the application.conf file located at src/main/application.conf to specify the following directories according to your system setup:

originalPerturbedNodes: Output directory for the Cartesian product of Original x Perturbed Nodes.
InputToShardNodes: Set to originalPerturbedNodes.
outputForShardNodes: Output directory for sharded nodes.
originalPerturbedEdges: Output directory for the Cartesian product of Original x Perturbed Edges.
inputToShardEdges: Set to originalPerturbedEdges.
outputForShardEdges: Output directory for sharded edges.
originals: NGS file for the original graph.
originalNgsDirectory: Directory to locate the original NGS file.
PerturbedNgs: NGS file for the perturbed graph.
perturbedNgsDirectory: Directory to locate the perturbed NGS file.
nodesMapReduceInputPath: Directory for the MapReduce job input (nodes).
nodesMapReduceOutputPath: Output folder for the nodes MapReduce job.
edgesMapReduceInputPath: Directory for the MapReduce job input (edges).
edgesMapReduceOutputPath: Output folder for the edges MapReduce job.


Running the MapReduce Job
You can run the MapReduce job in two ways:

Locally:
Ensure Hadoop is installed on your machine.
Configure the necessary files in the NodesMapReduce and EdgesMapReduce classes.
On AWS:
Create a JAR file for the main class you wish to run.
Set one of the MapReduce classes as the main class in the build.sbt file.
Execute the command
  sbt clean compile assembly
This generates the NetGameSim.jar file.
Upload this JAR file and the sharded input files to AWS according to your configuration in application.conf.
Testing
This project includes multiple tests written in Scala. To run the tests from the terminal, navigate to the project directory and execute:  
sbt clean compile test
Features
Word2Vec Integration: Leverages Word2Vec for embedding and processing text data.
Custom Writable Classes: Efficiently handles text objects and embeddings.
Sharding Support: Optimizes processing for large datasets.
Detailed Logging: Facilitates debugging and monitoring of the MapReduce jobs.













