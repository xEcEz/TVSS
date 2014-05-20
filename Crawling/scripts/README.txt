‘tvshow_crawler.py’
-> using the list crawled with scrappy/spiders/tvshows_spider.py, this script does the real work and extract the subtitles.

‘duplicate_elimination.py’
-> a lot of subtitles existed in two version for a single episode. This script solve this issue and remove duplicates.

‘imdb_score_short.py’
-> produces a short JSON file containing only the title of the TV show along with its imdb rating. This file is then transform to a txt format by using http://konklone.io/json/ to avoid compatibility problems with the JavaPLay framework.

‘imdb_score_merge.py’
-> produces data/imdd_ratings_merge.json file to be used by the visualization team.