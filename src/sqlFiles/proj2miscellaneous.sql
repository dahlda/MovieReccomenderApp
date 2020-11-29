-- Doug Dahl

-- Miscellaneous Front end app procedures

DROP PROCEDURE RECCOMEND_MOVIES_BY_PERSONALITY;

SELECT * from megatable1;

DROP PROCEDURE RECCOMEND_MOVIES_BY_PERSONALITY;

DELIMITER $$
CREATE PROCEDURE RECCOMEND_MOVIES_BY_PERSONALITY(IN openness float, IN agree float, IN es float, IN c float, IN ex float)
BEGIN
SELECT mid FROM(
SELECT mt.movie_id as mid, MAX(mt.rating) as maxrating
FROM megatable2 mt
LEFT JOIN megatable1 mo
ON mt.user_id = mo.userID
WHERE mo.openness = openness and mo.agreeableness = agree and mo.emotional_stability = es and mo.conscientiousness = c and mo.extraversion = ex
GROUP BY mid
) as x
ORDER BY maxrating;
END
$$


CALL RECCOMEND_MOVIES_BY_PERSONALITY(7.0, 7.0, 7.0, 7.0, 7.0);
DROP PROCEDURE TOP_MOVIES;

DELIMITER $$
CREATE PROCEDURE TOP_MOVIES()
BEGIN
SELECT movie_id FROM(
SELECT movie_id, AVG(rating)
FROM megatable2 m
GROUP BY movie_id
HAVING AVG(rating) > 4.5
ORDER BY movie_id DESC) temp
LIMIT 100;
END
$$

select * from megatable1 where openness = 7.0 and agreeableness = 7.0 and emotional_stability = 7.0
limit 15;

select * from megatable2
ORDER BY tstamp desc
limit 10;

DROP PROCEDURE INSERT_RATING;
DELIMITER $$
CREATE PROCEDURE INSERT_RATING(IN uid VARCHAR(100), IN mid INT, in rate decimal(2,1), in dt VARCHAR(255))
BEGIN
INSERT INTO megatable2 (user_id, movie_id, rating, tstamp, date_time)
VALUES (uid, mid, rate, (SELECT NOW()), dt);
end;
$$

CALL TOP_MOVIES();