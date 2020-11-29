-- Bo Peng
-- bo.peng@vanderbilt.edu
-- Project 2 part 2

-- Database creation

DROP DATABASE IF EXISTS personalitydb;
CREATE DATABASE IF NOT EXISTS personalitydb;
USE personalitydb;

DROP TABLE IF EXISTS megatable1;
CREATE TABLE IF NOT EXISTS megatable1 (
userID VARCHAR(100),
openness DECIMAL(2, 1) UNSIGNED,
agreeableness DECIMAL(2,1) UNSIGNED,
emotional_stability DECIMAL(2,1) UNSIGNED,
conscientiousness DECIMAL(2,1) UNSIGNED,
extraversion DECIMAL(2,1) UNSIGNED,
assigned_metric VARCHAR(100),
assigned_condition VARCHAR(100),
movie_1 INT UNSIGNED,
predicted_rating_1 DECIMAL(12, 11) UNSIGNED,
movie_2 INT UNSIGNED,
predicted_rating_2 DECIMAL(12, 11) UNSIGNED,
movie_3 INT UNSIGNED,
predicted_rating_3 DECIMAL(12, 11) UNSIGNED,
movie_4 INT UNSIGNED,
predicted_rating_4 DECIMAL(12, 11) UNSIGNED,
movie_5 INT UNSIGNED,
predicted_rating_5 DECIMAL(12, 11) UNSIGNED,
movie_6 INT UNSIGNED,
predicted_rating_6 DECIMAL(12, 11) UNSIGNED,
movie_7 INT UNSIGNED,
predicted_rating_7 DECIMAL(12, 11) UNSIGNED,
movie_8 INT UNSIGNED,
predicted_rating_8 DECIMAL(12, 11) UNSIGNED,
movie_9 INT UNSIGNED,
predicted_rating_9 DECIMAL(12, 11) UNSIGNED,
movie_10 INT UNSIGNED,
predicted_rating_10 DECIMAL(12, 11) UNSIGNED,
movie_11 INT UNSIGNED,
predicted_rating_11 DECIMAL(12, 11) UNSIGNED,
movie_12 INT UNSIGNED,
predicted_rating_12 DECIMAL(12, 11) UNSIGNED,
is_personalized TINYINT UNSIGNED,
enjoy_watching TINYINT UNSIGNED
) ENGINE=INNODB;

DROP TABLE IF EXISTS megatable2;
CREATE TABLE IF NOT EXISTS megatable2 (
user_id VARCHAR(100),
movie_id INT UNSIGNED,
rating DECIMAL(2, 1) UNSIGNED,
tstamp DATETIME
)  ENGINE=INNODB;



-- Data load-in

LOAD DATA INFILE "/Applications/MAMP/db/mysql57/personalitydb/2018-personality-data.csv"
INTO TABLE megatable1
FIELDS TERMINATED BY ","
LINES TERMINATED BY "\n"
IGNORE 1 ROWS;

LOAD DATA INFILE "/Applications/MAMP/db/mysql57/personalitydb/2018_ratings.csv"
INTO TABLE megatable2
FIELDS TERMINATED BY ","
LINES TERMINATED BY "\n"
IGNORE 1 ROWS;


SELECT * 
FROM megatable1
LIMIT 10;

SELECT *
FROM megatable2
LIMIT 10;