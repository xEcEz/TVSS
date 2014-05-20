#!/usr/bin/env python

import urllib
import zipfile
import urllib2
import json
import re
import os
import zipfile
"""
@author Nassim Drissi
@credits Nils Bouchardon
"""

if not os.path.isdir('downloaded'):
    os.makedirs('downloaded')
    
json_data = open('./tvshows.json')
data = json.load(json_data)

for tvshow in data:
        title = " ".join(tvshow["title"]).encode('utf-8')
        nbSeasons = float("".join(tvshow["nbSeasons"]))
        i = 1
        outputFilename = "downloaded/" + title
        idTvShow = re.sub("tvshow-|.html","", str(" ".join(tvshow["link"])))
        link = re.search("^[0-9]{1,5}",idTvShow).group(0).encode('utf-8')
        while i <= nbSeasons:
                outputFilename = "downloaded/" + title + str(i) + ".zip"
                url = "http://www.tvsubtitles.net/download-"+link+"-"+str(i)+"-en.html"
                print "URL to fetch: " + url
                response = urllib2.urlopen(url)
                print "status: "+ str(response.getcode())
                zippedData = response.read()

                print "Saving to ",outputFilename
                output = open(outputFilename,'wb')
                output.write(zippedData)
                output.close()

                try:
                        zfobj = zipfile.ZipFile(outputFilename)
                        for name in zfobj.namelist():
                            uncompressed = zfobj.read(name)

                            if not os.path.isdir('extracted'):
                                    os.makedirs('extracted')

                            # save uncompressed data to disk
                            outputFilename = "extracted/" +title + "/" +"Season "+str(i)+"/"+ name
                            if not os.path.isdir("extracted/" + title + "/" + "Season "+ str(i)):
                                    os.makedirs("extracted/"+title+ "/" + "Season "+ str(i))
                            
                            print "Saving extracted file to ",outputFilename
                            output = open(outputFilename,'wb')
                            output.write(uncompressed)
                            output.close()
                except (zipfile.BadZipfile):
                        print "BadzipFile season " + str(i)
                        pass
                i = i+1



