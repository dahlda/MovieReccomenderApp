-- Bo Peng
-- bo.peng@vanderbilt.edu
-- Project 2 

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
userID VARCHAR(100),
movieID INT UNSIGNED,
rating DECIMAL(2, 1) UNSIGNED,
tstamp DATETIME
)  ENGINE=INNODB;

LOAD DATA INFILE "C:/wamp64/tmp/2018-personality-data.csv"
INTO TABLE megatable1
FIELDS TERMINATED BY ","
LINES TERMINATED BY "\n"
IGNORE 1 ROWS;

LOAD DATA INFILE "C:/wamp64/tmp/2018_ratings.csv"
INTO TABLE megatable2
FIELDS TERMINATED BY ","
LINES TERMINATED BY "\n"
IGNORE 1 ROWS;

-- This table gives the personality info of each person
DROP TABLE IF EXISTS personality;
CREATE TABLE personality (
userID VARCHAR(100),
openness DECIMAL(2, 1) UNSIGNED CHECK(openness >= 1 AND openness <= 7),
agreeableness DECIMAL(2,1) UNSIGNED CHECK(agreeableness >= 1 AND agreeableness <= 7),
emotional_stability DECIMAL(2,1) UNSIGNED CHECK(emotional_stability >= 1 AND emotional_stability <= 7),
conscientiousness DECIMAL(2,1) UNSIGNED CHECK(conscientiousness >= 1 AND conscientiousness <= 7),
extraversion DECIMAL(2,1) UNSIGNED CHECK(extraversion >= 1 AND extraversion <= 7),
PRIMARY KEY(userID)
) ENGINE=INNODB;

-- This table gives the assigned metric and condition for each person with the first movie in the assigned movie list
-- Assigned Metric: one of the follows (serendipity, popularity, diversity, default). Each user, besides being assessed 
-- their personality, was evaluated their preferences for a list of 12 movies manipulated with serendipity, popularity, diversity 
-- value or all 
-- Assigned Condition: one of the follows (high, medium, low, default(none)). Based on the assigned metric, and this assigned condition, 
-- the list of movies was generated for the users. For example: if the assigned metric is serendipity and the assigned condition 
-- is high, the movies in the list are highly serendipitous. 
DROP TABLE IF EXISTS metricIDcondition;
CREATE TABLE metricIDcondition (
userID VARCHAR(100),
assigned_metric VARCHAR(100) NOT NULL,
assigned_condition VARCHAR(100) NOT NULL,
movie_1 INT UNSIGNED NOT NULL,
PRIMARY KEY(userID)
) ENGINE=INNODB;

-- This table gives the full movie list based on the assigned metric and condition and the first movie in the list. It also
-- gives the predicted rating for the each movie in the movie list
DROP TABLE IF EXISTS metricConditionMovies;
CREATE TABLE metricConditionMovies (
assigned_metric VARCHAR(100),
assigned_condition VARCHAR(100),
movie_1 INT UNSIGNED NOT NULL,
predicted_rating_1 INT UNSIGNED,
movie_2 INT UNSIGNED NOT NULL,
predicted_rating_2 INT UNSIGNED,
movie_3 INT UNSIGNED NOT NULL,
predicted_rating_3 INT UNSIGNED,
movie_4 INT UNSIGNED NOT NULL,
predicted_rating_4 INT UNSIGNED, 
movie_5 INT UNSIGNED NOT NULL,
predicted_rating_5 INT UNSIGNED,
movie_6 INT UNSIGNED NOT NULL,
predicted_rating_6 INT UNSIGNED,
movie_7 INT UNSIGNED NOT NULL,
predicted_rating_7 INT UNSIGNED,
movie_8 INT UNSIGNED NOT NULL,
predicted_rating_8 INT UNSIGNED,
movie_9 INT UNSIGNED NOT NULL,
predicted_rating_9 INT UNSIGNED,
movie_10 INT UNSIGNED NOT NULL,
predicted_rating_10 INT UNSIGNED,
movie_11 INT UNSIGNED NOT NULL,
predicted_rating_11 INT UNSIGNED,
movie_12 INT UNSIGNED NOT NULL,
predicted_rating_12 INT UNSIGNED,
PRIMARY KEY(assigned_metric, assigned_condition, movie_1)
) ENGINE=INNODB;

-- This table gives the feedback of the person based on whether their opinion of the movie list
DROP TABLE IF EXISTS feedback;
CREATE TABLE feedback (
userID VARCHAR(100),
is_personalized TINYINT UNSIGNED CHECK(is_personalized IN(1, 2, 3, 4, 5)),
enjoy_watching TINYINT UNSIGNED CHECK(enjoy_watching IN(1, 2, 3, 4, 5)),
PRIMARY KEY(userID)
) ENGINE=INNODB;

-- This table gives rating of a specific movie by a specific person
DROP TABLE IF EXISTS movieRating;
CREATE TABLE movieRating (
userID VARCHAR(100),
movieID INT UNSIGNED,
rating DECIMAL(2, 1) UNSIGNED,
tstamp DATETIME,
PRIMARY KEY(userID, movieID),
CONSTRAINT fk_rating_personality
	FOREIGN KEY(userID)
    REFERENCES personality(userID)
) ENGINE=INNODB;

-- Inserts the data into the normalized tables
INSERT INTO personality
SELECT userID,
		openness,
        agreeableness,
        emotional_stability,
        conscientiousness,
        extraversion
FROM megatable1
GROUP BY userID;

INSERT INTO metricIDcondition
SELECT userID,
		assigned_metric,
        assigned_condition,
        movie_1
FROM megatable1
GROUP BY userID;

INSERT INTO metricConditionMovies
SELECT assigned_metric,
		assigned_condition,
        movie_1,
		predicted_rating_1,
		movie_2,
		predicted_rating_2,
		movie_3,
		predicted_rating_3,
		movie_4,
		predicted_rating_4, 
		movie_5,
		predicted_rating_5,
		movie_6,
		predicted_rating_6,
		movie_7,
		predicted_rating_7,
		movie_8,
		predicted_rating_8,
		movie_9,
		predicted_rating_9,
		movie_10,
		predicted_rating_10,
		movie_11,
		predicted_rating_11,
		movie_12,
		predicted_rating_12
FROM megatable1
GROUP BY assigned_metric, assigned_condition, movie_1;

INSERT INTO feedback
SELECT userID,
		is_personalized,
        enjoy_watching
FROM megatable1
WHERE is_personalized
GROUP BY userID;

INSERT INTO movieRating
SELECT userID,
		movieID,
        rating,
        tstamp
FROM megatable2
GROUP BY userID, movieID;

-- Trigger on the feedback table that makes sure the user's feedback is a valid entry
DROP TRIGGER IF EXISTS feedback_before_insert;

DELIMITER //

CREATE TRIGGER feedback_before_insert
BEFORE INSERT
ON feedback
FOR EACH ROW
BEGIN
	IF NEW.is_personalized > 5 OR NEW.enjoy_watching > 5 THEN
		SIGNAL SQLSTATE "22003"
		SET	MESSAGE_TEXT = "Cannot have feedback rating over 5.",
		MYSQL_ERRNO = 1264;	
	ELSEIF NEW.is_personalized < 1 OR NEW.enjoy_watching < 1 THEN
		SIGNAL SQLSTATE "22003"
		SET	MESSAGE_TEXT = "Cannot have a feedback rating less than 1.",
		MYSQL_ERRNO = 1264;
	END IF;

END //

DELIMITER ;

-- Trigger on the ratings movieRating table that makes sure the rating is a valid value
DROP TRIGGER IF EXISTS movieRating_before_insert

DELIMITER //
CREATE TRIGGER movieRating_before_insert
BEFORE INSERT
ON movieRating
FOR EACH ROW
BEGIN
	IF rating < 0.5 THEN
		SIGNAL SQLSTATE "22003"
		SET	MESSAGE_TEXT = "Movie rating cannot be less than 0.5.",
		MYSQL_ERRNO = 1264;
	ELSEIF rating > 5.0 THEN
		SIGNAL SQLSTATE "22003"
		SET	MESSAGE_TEXT = "Movie rating cannot be greater than 5.",
		MYSQL_ERRNO = 1264;
	END IF;
    
END //

DELIMITER ;

-- Trigger that creates an audit for all the new movie ratings
CREATE TABLE ratingsAudit (
	newUserID VARCHAR(100),
	newMovieID INT UNSIGNED,
	newRating DECIMAL(2, 1) UNSIGNED,
	newTstamp DATETIME
)

DROP TRIGGER IF EXISTS movieRating_after_insert

DELIMITER //
CREATE TRIGGER movieRating_after_insert
AFTER INSERT
ON movieRating
FOR EACH ROW
BEGIN
	INSERT INTO ratingsAudit
    VALUES (NEW.userID, NEW.movieID, NEW.rating, NEW.tstamp);
    
END //

DELIMITER ;

-- Stored procedure for when the user decides to change their rating on a particular movie
DROP PROCEDURE IF EXISTS changeRating;

DELIMITER //
CREATE PROCEDURE changeRating(IN myID VARCHAR(100), IN myMovie INT UNSIGNED, IN myRating TINYINT UNSIGNED)
BEGIN
	DECLARE sql_error INT DEFAULT FALSE;
    
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET sql_error = TRUE;
    
    START TRANSACTION;
    
    UPDATE movieRating
    SET rating = myRating, tstamp = NOW()
    WHERE userID = myID AND movieID = myMovie;
    
    IF sql_error = FALSE THEN
		COMMIT;
        SELECT "The user's rating for a particular movie has been changed." AS msg;
	ELSE
		ROLLBACK;
        SELECT "User's rating change has been rolled back" AS msg;
	END IF;

END //

DELIMITER ;

-- Store Procedure for when the user decides to change their personality attributes
DROP PROCEDURE IF EXISTS changePersonality;

DELIMITER //
CREATE PROCEDURE changePersonality(IN myID VARCHAR(100), IN myOpenness DECIMAL(2, 1) UNSIGNED, IN myAgreeableness DECIMAL(2, 1) UNSIGNED, 
IN myEmotional_stability DECIMAL(2, 1) UNSIGNED, IN myConscientiousness DECIMAL(2, 1) UNSIGNED, IN myExtraversion DECIMAL(2, 1) UNSIGNED)
BEGIN
	DECLARE sql_error INT DEFAULT FALSE;
    
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET sql_error = TRUE;
    
    START TRANSACTION;
    
    UPDATE personality
    SET openness = myOpenness, 
		agreeableness = myAgreeableness, 
		emotional_stability = myEmotional_stability, 
		conscientiousness = myConscientiousness,
        extraversion = myExtraversion
    WHERE userID = myID;
    
    IF sql_error = FALSE THEN
		COMMIT;
        SELECT "The user's personality attribute has been changed." AS msg;
	ELSE
		ROLLBACK;
        SELECT "User's rating change has been rolled back" AS msg;
	END IF;

END //

DELIMITER ;

-- A view of each person and the list of movies they were given with the predicted rating
CREATE OR REPLACE VIEW personMovieList AS
SELECT userID,
		movie_1 ,
		predicted_rating_1,
		movie_2,
		predicted_rating_2,
		movie_3,
		predicted_rating_3,
		movie_4,
		predicted_rating_4, 
		movie_5,
		predicted_rating_5,
		movie_6,
		predicted_rating_6,
		movie_7,
		predicted_rating_7,
		movie_8,
		predicted_rating_8,
		movie_9,
		predicted_rating_9,
		movie_10,
		predicted_rating_10,
		movie_11,
		predicted_rating_11,
		movie_12,
		predicted_rating_12
FROM metricIDcondition
	JOIN metricConditionMovies USING(assigned_metric, assigned_condition, movie_1);

-- A view of each person's personality corresponding to what their feedback ratings were
CREATE OR REPLACE VIEW personalityFeedback AS
SELECT userID,
		openness,
        agreeableness,
        emotional_stability,
        conscientiousness,
        extraversion,
        is_personalized,
        enjoy_watching
FROM personality
	JOIN feedback USING(userID);

