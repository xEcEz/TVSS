import numpy
import sys 
import random
import math

if(__name__ == '__main__'):
    if(len(sys.argv) != 5):
        print'usage : python k_means_cluster.py <topic-document-distribution.txt>  <shows> <number-cluster> <output_path>'
        sys.exit(1)
    #loading parameters
    
    path_to_composition = sys.argv[1]
    path_to_shows = sys.argv[2]
    nb_clusters = int(sys.argv[3])
    output_path = sys.argv[4]
    
    #we are going to load the files onto the system
    #use a matrix here.
    composition_reader = open(path_to_composition,'r')
    shows_reader = open(path_to_shows,'r')
    data_points = numpy.loadtxt(path_to_composition)

    nb_of_shows, nb_topics = numpy.shape(data_points)
    #we define a center vector and an assignment vector
    center = numpy.zeros((nb_clusters,nb_topics)) #size = nb_clusters
    assignments = numpy.zeros((nb_of_shows,nb_clusters))
  #size = nb_of_shows
    
    #we define a number of iteration run : 
    num_iteration = 250
    
    #we initialize the centers of the clusters.
    
    for i in range(0,nb_clusters):
        for j in range(0,nb_topics-1):
             center[i,j]=random.random()
    
    
    for iter in range(1,num_iteration):
        #assign based on shortest distance.
        print iter
        for i in range(0,nb_of_shows):
            min_dist = 1000
            min_index = 0
            for j in range(0,nb_clusters):
                index = numpy.where(assignments[i,:]==1)
                temp = numpy.subtract(data_points[i,:],center[j,:])
                dist = numpy.dot(temp,temp)
                dist = math.sqrt(dist)
                if(dist<min_dist) :
                    min_dist = dist
                    min_index = j
            assignments[i,index]=0
            m,n = numpy.shape(assignments)
            assignments[i,min_index]=1
        #M-Step : update the cluster centers
        
        for k in range(0,nb_clusters):
           for i in range(0,nb_topics):
               u = 0
               denom = 0
               for n in range(0,nb_of_shows):
                   u = u + assignments[n,k]*data_points[n,i]
                   denom = denom + assignments[n,k]
               if(denom != 0):
                   center[k,i] = u/ float(denom)
               else :
                   center[k,i] = 0
    print 'we have finished the loop.'
    print 'We are now going to ouput the shows in each cluster.'
    
    #We need to load the shows file
    shows = []
    for i in range(0,nb_of_shows):
        shows.append(shows_reader.readline())
        
    #We output the result
 
    output_writer = open(output_path,'w')
    print 'we may be facing an issue here...'

    print assignments
    for k in range(0,nb_clusters):
        print 'Cluster' +str(k)
        print center[k,:]
        output_writer.write('Cluster : ' + str(k))
        output_writer.write('\n')
        for i in range(0,nb_of_shows):
            if(assignments[i,k] == 1):
                output_writer.write(str(i))
                output_writer.write('\n')
                print shows[i]
        output_writer.write('\n')
    output_writer.write('end')

                

        
