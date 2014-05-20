#!/usr/bin/env python
import json
import urllib
import htmlentitydefs
import re
"""
@author Nassim Drissi
@credits Nils Bouchardon
"""

jsonOutput = [] 


with open('./shows.txt') as fp:
    for line in fp:
        line = line.replace('\n','')
        line = line.replace('?','')
        values = line.split("\t")
        title = values[1]
        print 'TITLE: ' + title
        param = {'t':title}
        url = "http://www.omdbapi.com/?" + urllib.urlencode(param)
    
        print "Request at url " + url
        response = urllib.urlopen(url)
        jsonRating = json.load(response)
        if jsonRating["Response"] == 'True':
            tvinfo = {'title': title,'id':values[0],'rating':jsonRating["imdbRating"],'nbVotes':jsonRating["imdbVotes"],'genre':jsonRating["Genre"], 'plot':jsonRating["Plot"],'poster':jsonRating["Poster"]}
        else:
            tvinfo = {'title': title,'id':values[0],'rating':"not found"}
        jsonOutput.append(tvinfo)

with open("imdb_ratings_small.json","w") as outfile:
        json.dump(jsonOutput,outfile, encoding='latin-1')


