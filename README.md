## TV-Shows Analysis & Recommender System ##
===============================================================================================================================================

1. Description
2. Files Organisation
3. Example of Results

===============================================================================================================================================

### 1. Description ###

TVSS aims to recommend TV-Shows based on their similarity computed over a subtitle analysis of their content.
Here is the current version of the graph representing clusters of similar shows and some labelled topics we identified in them:

![Clusters of Shows](/Graph Scripts/graph.jpg)

The goal of the project is to analyze the content of TV-Shows according to certain topics, via a subtitles analysis. 
In order to achieve that, we acquired a large data set of subtitles of good quality (~1100 shows) and then, using an hadoop implementation of the LDA algorithm, analyze the topics present in each show. 
For example, if we consider the show "Homeland", the resulting score regarding topics could be : 60% terrorism, 20% psychology, 10% espionage and 10% romance.

As a final result, we have differents things:
- For each TV-show, we have a detailed information page that contains the different topics the show is made off and their weight.
- A content-based recommender systems for TV-shows, where given one TV-show, the system can propose the most similar TV-shows to the latter.

===============================================================================================================================================

### 2. Files Organisation ###

The project is divided in 4 parts :
  - Crawling
  - Pre-processing (Cleaning the data)
  - Processing  (LDA)
  - Post-processing (Website & Recommender System)

In each sub folder you can find a README that explains how to run the part in question.

===============================================================================================================================================

### 3. Example of Results ###

Here, you can find an example of results :

- Game of Thrones :
  - [Top Words/Topics](/PostProcessing/GOT1.jpg)
  - [Visuals](https://raw.githubusercontent.com/xEcEz/TVSS/master/PostProcessing/GOT2.jpg)
  - Recommendations: (Title - Similarity)
      1. Crusoe - 90.4 %
      2. Krod Mandoon and the Flaming Sword of Fire - 90.36 %
      3. 1066 The Battle for Middle Earth - 89.49 %
      4. Divine? The Series - 89.01 %
      5. Kung Fu - 87.93 %
      6. Roar - 86.81 %
      7. Neverwhere - 85.84 %
      8. The Pillars Of The Earth - 84.46 %
      9. Rome - 83.69 %
      10. Poltergeist The Legacy - 83.54 %
      11. Atlantis - 83.27 %
      12. Reign - 81.94 %
      13. Ancient Rome - The Rise and Fall of an Empire - 81.49 %
      14. Kings - 80.69 %
      15. Thor & Loki Blood Brothers - 80.58 %

- The Simpsons :
  - Recommendations: (Title - Similarity)
      1. Men Behaving Badly - 99.39 %
      2. Beavis and Butt-Head - 99.22 %
      3. The Cleveland Show - 99.03 %
      4. Family Guy - 98.04 %
      5. The Penguins Of Madagascar - 97.59 %
      6. Robot Chicken - 97.45 %
      7. American Dad! - 97.32 %
      8. Futurama - 97.28 %
      9. My Name Is Earl - 95.2 %
      10. South Park - 95.2 %
      11. Raising Hope - 94.98 %
      12. Neighbors From Hell - 94.75 %
      13. The Ren & Stimpy Show - 93.94 %
      14. Clerks - 93.63 %
      15. Key And Peele - 93.46 %

===============================================================================================================================================

### And more to come !!! Stay tuned. ###
