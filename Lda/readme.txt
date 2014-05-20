I- PROJECT DESCRIPTION:

The Lda project contains the classes to run the Latent Dirichlet Allocation algorithm on a corpus of words.
The goal of the LDA algorithm is to take a corpus and a number of topics K and to produce K clusters of words 
each one representing a topic and then for each document produce a per-topic distribution.

This project is a hadoop distributed implementation for LDA using the variational inference for the parameter
estimation.

------------------------------------------------
II- INPUT OUTPUT FORMAT

i-Input:
1- The input that it takes is a corpus of words in the following form:
d1\tw1,o1 w2,o2 w3,o3....wn,on
d2\tw1,o1 ....
Where d is the id of the document, w is the index of a word and o is the occurence of that word in d.
Note that it is better to not record the words that have 0 occurences in one document for performance reasons.

2- A number of topics K


ii- Output:
1- the lambda file which represents the distribution of words for each topic, it has V lines with
the following format, where V represents the size of the vocabulary:
pr(w1|t1) pr(w1|t2) ... pr(w1|tk)
pr(w2|t1) pr(w2|t2) ... pr(w2|tk)

2- the alpha file which represents the dirichlet parameter for the distribution of topics over documents.
the document contains one line of k values, one value per topic.

3- the gamma file which represents the distribution of topics for each TV-show, the document contains D lines
where each line has the following format, where D is the number of documents in the corpus:
pr(t1|d1) pr(t2|d1) ... pr(tk|d1)
pr(t1|d2) pr(t2|d2) ... pr(tk|d2)

4- the gradient file that could be ignored 

------------------------------------------------
III- RUN THE PROJECT

Execute the class Run with the following arguments:
<path to corpus file> <path to output directory> <K> <D> <V> <number of iterations>
where K, D and V are as defined previously. For the dataset we tested 40 iterations is a good number.

------------------------------------------------
IV- CODE DESCRIPTION

The code contains 6 classes:
1- Run: the class run contains the method main. In that main we execute the iterations of the LDA algorithm
where each iteration is a hadoop job followed by operations done in the driver.

2- LdaMapper: it takes a line of TV-show and computes an update on gamma, an update in lambda and an update
in gradient

3- LdaReducer: just collects the different updates of the mapper and sum them

4- Driver: takes the gradient, the previous alpha and updates the new alpha

5- FileSystemHandler: it's a class that deals with the different reads and writes of the files in the HDFS

6- Parameters: just contain some static variables that needs to be shared by all the mappers and reducers


 
 
