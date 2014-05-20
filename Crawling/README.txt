I. Data folder

‘tvshow_crawler.py’
-> using the list crawled with scrappy/spiders/tvshows_spider.py, this script does the real work and extract the subtitles.

‘duplicate_elimination.py’
-> a lot of subtitles existed in two version for a single episode. This script solve this issue and remove duplicates.

‘imdb_score_short.py’
-> produces a short JSON file containing only the title of the TV show along with its imdb rating. This file is then transform to a txt format by using http://konklone.io/json/ to avoid compatibility problems with the JavaPLay framework.

‘imdb_score_merge.py’
-> produces data/imdd_ratings_merge.json file to be used by the visualization team.

---------------------------------------------------------------------------------
II. Scripts folder

’tvshows.json’ 
-> is the first file  we obtained by crawling tvsubtitles.net directory. It is further used to crawl the subtitles of each TV show.

‘imdb_ratings_short.txt’
-> is the file used to fill the imdb-rating informations for each TV show. Their exist two different files that contain informations about the TV show, the reason for that is the non-compatibility between JavaPlay and JSON.

‘shows.txt’ 
-> is the file given by the visualization team. The file is used to complete ‘imdb_ratings_merge.json’ and add to each TV show a unique identifier.

‘imdb_rating_merge.json’
-> is the complete file containing imdb’s information for each TV show: {title, identifier, imdb_rating, plot, number_of_votes, poster, genre}. It is used to display needed informations for the user.

--------------------------------------------------------------------------------
III. Scrappy folder

This is the library we used in order to crawl the data
