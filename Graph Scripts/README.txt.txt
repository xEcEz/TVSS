These scripts are used to create a few useful graphs or clusters.


graph_creator :
This script creates a graph readable by Gephi to display the nodes, their k-nearest neighbors, and their top topics (as labels to color them using Gephi's partitioning tool)
Usage : python graph_creator.py <path_to_topic_proportions> <path_to_shows>  <number_of_neighbors> <output_path.gml>
	path_to_topic_proportions : the output of the LDA algorithm, specificaly the gamma file
	path_to_shows : path to a file containing the name of the shows with format 'show_id	show_name'
	number_of_neighboor : number of links for each node.
	output_path.gml : output path to be specified by user, in the gml format so that Gephi can import it
Note : This method uses the networkx package, and creates a temporary file called gml_graph that can be deleted.

k-means-clustering : 
This script clusters the corpus into k clusters of shows using the k means algorithm.
Usage : python k_means_cluster.py <topic-document-distribution.txt>  <shows> <number-cluster> <output_path> 
	topic-document_distribution : the output of the LDA algorithm, specificaly the gamma file
	shows : path to a file containing the name of the shows with format 'show_id	show_name'
	number-cluster : number of the clusters, parameter of the k-means algorithm
	output_path : path on wich the results should be written