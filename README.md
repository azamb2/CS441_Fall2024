# CS441_Fall2024
Name: Abdul Gaffir Zambi   
# MapReduceProgram
This project implements a MapReduce program using Scala and Apache Hadoop to process text data. The program tokenizes input text, retrieves word embeddings, and computes averaged embeddings for each unique token. It is designed to be flexible and can handle large datasets through sharding.


#Features
Custom TextArrayWritable class for handling arrays of text objects.
Mapper and Reducer classes for processing text data and calculating embeddings.
Sharding of input data for efficient processing.
Detailed logging for debugging and monitoring.

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [Running Tests](#running-tests)
- [Project Structure](#project-structure)
- [License](#license)

## Installation

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd <repository-directory>
