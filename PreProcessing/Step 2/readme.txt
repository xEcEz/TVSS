Due to computational time constraint, this pre-processing is done after the first pre-processing stage.

It does transform the set of documents in a compact one.
It also removes words that occur in more than 90% of shows, some stopWords (including names) that went through the first preprocessing step and also words that doesn't exist.

- It takes as input every files in the folder called "preprocessed"
- You can find the output in the folder f_preprocessed, there is 3 files:
	vocabulary, of the form: vid1, word1 \n vid2, word2 ....
	show, of the form: sid1, show1 \n sid2, show2 ....
	corpus, of the form: sid \t vid, wordCount of vid in sid \n ....
