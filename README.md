# hatee

A Twitter like project. 

The name "hatee" comes from the lyrics of the song [Ylvis - The Fox](https://en.wikipedia.org/wiki/The_Fox_(What_Does_the_Fox_Say%3F)) "Hatee-hatee-hatee-ho!".


## Priority Queue
### 0: Implement APIs
* 0.1: Implement `Status` placeholder API 
* 0.2: Implement `Comment` placeholder API
* 0.3: Implement `Status-Composite` placeholder API
* 0.4: Dockerize
* 0.5: Add API Description using Swagger <=
* 0.6: Implement Persistance Layer for each microservice
* ...
### 1: Implement SPA

## APIs
* Get a single status `GET $HOST:$PORT/status/{statusID}`
* Get a comments for given status `GET $HOST:$PORT/comments?statusId={statusId}`
* Get a composite status (status with its comments) `GET $HOST:$PORT/status-composite/{statusID}`
* ...

