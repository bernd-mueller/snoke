# snoke
Towards a Semantic NoSQL Knowledge Environment (SNOKE)

The Maven project snoke contains implementation for various applications ranging from the conversion of ontology formats, UIMA engine components, neo4j database handlers to language models with DeepLearning4j. Accordingly, the maven project is structured into the respective modules:
<ol>
<li><strong>snoke.dl</strong> for implementations for the training of paragraph vector models with DeepLearning4j</li>
<li><strong>snoke.neo4j</strong> for implementations with the Neo4j database incluing importers for MesH and ATC</li>
<li><strong>snoke.uima</strong> for implementations of UIMA engine components such as readers, annotators, and writers</li>
<li><strong>snoke.ontology</strong> for implementations of converters of various ontologies</li>
</ol>
The API documentation is available on https://bernd-mueller.github.io/snoke/javadoc/
