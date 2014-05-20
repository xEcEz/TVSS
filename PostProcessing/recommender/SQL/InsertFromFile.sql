LOAD DATA LOCAL 
    INFILE '/Users/claire/Documents/Informatique/play-2.2.2/myFirstApp/recommender_system/data/data.txt' 
	INTO TABLE shows		
	COLUMNS TERMINATED BY '\t' LINES TERMINATED BY '\n'
	(@col1,@col2,@col3, @col4, @col5, @col6, @col7, @col8) 
	set title=@col1,theme1=@col2,theme2=@col3,theme3=@col4,theme4=@col5,theme5=@col6,theme6=@col7,theme7=@col8;