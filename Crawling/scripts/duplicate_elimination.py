#!/usr/bin/env python

import os
import re

"""
@author Nassim Drissi
@credits Nils Bouchardon
"""

for dirname, dirnames, filenames in os.walk('.'):
    # print path to all filenames.
    previous_episode = "null"
    for filename in filenames:
        #matches beginning of file (i.e: Breaking Bad - 1x01)
        episode_search = re.search("^.+[0-9]{1,2}x[0-9]{1,2}", filename)
        if episode_search:
            print "MATCH regexp"
            episode = episode_search.group(0)
        else:
            episode = filename
            print "FAILED regexp, episode: " + episode
            
        print "---episode " + episode

        if episode == previous_episode:
            os.remove(os.path.join(dirname,filename))
            print "Duplicate found"+episode

        previous_episode = episode
        
        
