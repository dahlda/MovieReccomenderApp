This application uses Java Spring and MySQL. This is a Maven project with RESTful web
and MySQL dependencies. There is also a google open source web connectivity framework that was
used to help make API calls. The API key that I used to make calls is hardcoded in the app, so that
shouldn't be an issue. The API calls themselves are made to https://themoviedb.org -- these 
calls are used to get poster and title information about movies, since the original db files did not 
have this information. Source control was managed using github. The project was written using 
Java 14.0.2 and MySQL 5.7.26 (MySQL Community Server (GPL)).