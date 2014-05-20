## TV-Shows Analysis & Recommender System ##

=================================================================================================================================================

1. Description
2. Files Organisation
3. Result examples

=================================================================================================================================================

1. Description

The goal of the project is to analyze the content of TV-Shows according to certain topics, via a subtitles analysis. 
In order to achieve that, we acquired a large data set of subtitles of good quality (~1100 shows) and then, using an hadoop implementation of the LDA algorithm, analyze the topics present in each show. 

For example, if we consider the show "Homeland", the resulting score regarding topics could be : 60% terrorism, 20% psychology, 10% espionage and 10% romance.

As a final result, we have two things:
    - For each TV-show, we have a detailed information page that contains the different topics the show is made off and their weight.
    - A content-based recommender systems for TV-shows, where given one TV-show, the system can propose the most similar TV-shows to the latter.

=================================================================================================================================================

II.

The project is divided in 4 parts :
  - Crawling
  - Pre-processing (Cleaning the data)
  - Processing  (LDA)
  - Post-processing (Website & Recommender System)

In each sub folder you can find a README that explains how to run the part in question.

