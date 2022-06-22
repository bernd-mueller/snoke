[![DOI](https://zenodo.org/badge/267342945.svg)](https://zenodo.org/badge/latestdoi/267342945)[![Build Status](https://api.travis-ci.org/bernd-mueller/snoke.svg?branch=master&status=passed)](https://app.travis-ci.com/github/bernd-mueller/snoke)

2021-12-14, Part 1: Response to current zero day security exploit for any source code dependent on log4j:
Most of the neo4j code is dead because essential dependencies are all affected by the log4j exploit. Anyways, I haven't used this package for several years. It might be necessary to simply discard the package. Not much of a loss... though.

2021-12-14, Part 2: Requiring attention cloning.

2022-06-22: Code is not looping.

# snoke
Towards a Semantic NoSQL (Not only SQL) Knowledge Environment (SNOKE)

The Maven project snoke contains implementation for various applications ranging from the conversion of ontology formats, UIMA engine components, neo4j database handlers to language models with DeepLearning4j. Accordingly, the maven project is structured into the respective modules:
<ol>
<li><strong>snoke.dl</strong> for implementations for the training of paragraph vector models with DeepLearning4j</li>
<li><strong>snoke.neo4j</strong> for implementations with the Neo4j database incluing importers for MesH and ATC</li>
<li><strong>snoke.uima</strong> for implementations of UIMA engine components such as readers, annotators, and writers</li>
<li><strong>snoke.ontology</strong> for implementations of converters of various ontologies</li>
<li><strong>snoke.swag</strong> for implementations of API endpoints</li>
</ol>
The API documentation is available on https://bernd-mueller.github.io/snoke/javadoc/
