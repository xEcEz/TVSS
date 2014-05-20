import sys
import numpy 
import networkx as nx
import matplotlib.pyplot  as plt
import re


def norm(v,w):
    num = numpy.dot(v,w)
    denom = numpy.dot(v,v)*numpy.dot(w,w)
    return num / float(denom)

if(__name__ == '__main__'):
    if(len(sys.argv) != 5) :
        print 'usage : python graph_creator.py <path_to_topic_proportions> <path_to_shows>  <number_of_neighbors> <output_path.gml>'

    #We load the data.
    print 'Info : Loading data...'
    path_to_proportion = sys.argv[1]
    path_to_shows = sys.argv[2]
    degre = int(sys.argv[3])
    path_to_output = sys.argv[4]
    proportions = numpy.loadtxt(path_to_proportion)
    number_of_shows,number_of_topics = numpy.shape(proportions)
    
    #We create the similarity matrix.
    similarity = numpy.zeros((number_of_shows,number_of_shows))
    
    print 'Info : Computing similarity...'
    for i in range(0,number_of_shows):
        for j in range(0,number_of_shows):
            similarity[i,j] = norm(proportions[i,:],proportions[j,:])
            denom = norm(proportions[i,:], proportions[i,:])*norm(proportions[j,:], proportions[j,:])
            similarity[i,j] = similarity[i,j]/denom
            
    #We now want to find for each graph the #degre# number of closest neighbors in terms of similarity
    #We want to make something better here, we want to be able to remove itself from the recommended list.
    print 'Info : Creating k-nearest graph...'
    graph =[]
    temp_sorted_similarity = []
    for i in range(0,number_of_shows):
        temp_sorted_similarity = sorted(similarity[i,:],reverse=True)
        not_over = True
        number_select = 0
        current_index = 0
        while(not_over):
            top = temp_sorted_similarity[current_index]
            index = numpy.where(similarity[i,:] == top)
            for ind in index : 
                if ind[0] != i :
                    if number_select < degre : 
                        graph.append([i,ind[0]])
                        number_select = number_select +1
                    else : 
                        not_over = False
            current_index = current_index + 1
    print 'Info : We loading shows...'
    #create graph
    
    
    #Load show info
    shows = []
    show_reader = open(path_to_shows,'r')
    for i in range(0,number_of_shows):
        string = show_reader.readline()
        string = string.split('\t')[1]
        #re.sub(r'([^\s\w]|_)+', '', string)
        string = re.sub(r'\W+','',string)
        shows.append(string)
    #We are going to try to make a more meaningfull clustering.
    #We use only the top topic for the cluster.
    
    topic_assignments = [0 for i in range(0, number_of_shows)]
    for i in range(0,number_of_shows):
        index = numpy.argmax(proportions[i,:])
        topic_assignments[i]=index
        
    
    
    #Now we want to set attributes : namely the cluster ID and the name of the node (title of the show)
   # cluster_attribute = {str(0),str(assignments[0])}


    G = nx.Graph()
    
    print 'Info : adding edges and nodes...'
    for edge in graph :
        G.add_edge(edge[0],edge[1])

    for i in range(0,number_of_shows):
        G.add_node(str(i), label = shows[i], Cluster = str(topic_assignments[i]))
 
    pos = nx.random_layout(G)  

    edge_c = [0 for i in range(0,len(G.edges()))]

    #nx.set_node_attributes(G,Cluster,cluster_attribute)
  #  nx.draw(G,pos, node_color = values, edge_color = edge_c, edge_alpha = 0)
    nx.write_gml(G,'gml_graph')
    
    #We now are going to open the gml file, will parse it, and will update it.
    
    gml_file_reader = open('gml_graph','r')
    gml_file_writer = open(path_to_output,'w')
    not_over = True
    char = '    '
    not_done_with_id = True
    i=0
    while(not_over):
        line = gml_file_reader.readline()
        if line ==  ']\n' or line == ']':
            gml_file_writer.write(line)
            not_over=False
        if 'id ' in line :
            id = line.split(' ')
            id = id[5].split('\n')[0]
            id = int(id)
            if id < len(shows):
                gml_file_writer.write(line)
                title = shows[id]
                cluster = str(topic_assignments[id])
                gml_file_writer.write(char+ 'label '+ title + '\n' )
                gml_file_writer.write(char + 'Cluster ' + cluster + '\n')
                gml_file_reader.readline()
                if(id == (len(shows)-1)):
                    new_line = gml_file_reader.readline()
                    gml_file_writer.write(new_line)
                    gml_file_reader.readline()
                
            if id == len(shows):
                not_done_with_id = True
                while(not_done_with_id):
                    temp_line=gml_file_reader.readline()
                    if 'edge [' in temp_line :
                        not_done_with_id = False
                        gml_file_writer.write(temp_line)
                        
        else :
            gml_file_writer.write(line)
                
