WEBSITE :
=========

The website is built with the Play! framework in order to run java code on server side.

- The java code is in /app/controllers/ directory
- The HTML (mixed with scala) code is in /app/views/ directory


You can directly access to the TVShow page in public/results/shows/ and they are sorted by id (you can find the id on the file /data/shows.txt). 

You can access the Topic page on the file public/results/topics/

If you want to run the whole website, you have to install the play framework
(http://www.playframework.com/). Then run the server by taping « play run » in the console on the top directory /recommender/.


Architecture :

There is two main pages index and result.
After selecting one or more TVShow on the main page, click on the «Recommend» button, the function Recommender.start() is called.

 