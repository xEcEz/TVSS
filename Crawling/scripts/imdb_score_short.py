#!/usr/bin/env python

import json
import urllib
import htmlentitydefs

"""
@author Nassim Drissi
@credits Nils Bouchardon
"""

json_data = open('./tvshows.json')
data = json.load(json_data)
jsonOutput = [] 


for tvshow in data:
    title = "".join(tvshow["title"]).encode('utf-8')
    param = {'t':title}
    url = "http://www.omdbapi.com/?" + urllib.urlencode(param)
    
    print "Request at url " + url
    response = urllib.urlopen(url)
    jsonRating = json.load(response)
    if jsonRating["Response"] == 'True':
        tvinfo = {'title': title,'rating':jsonRating["imdbRating"]}
    else:
        tvinfo = {'title': title, 'rating':"not found"}
    jsonOutput.append(tvinfo)

with open("imdb_ratings_small.json","w") as outfile:
        json.dump(jsonOutput,outfile)
