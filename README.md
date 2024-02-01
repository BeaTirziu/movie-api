
# API Documentation
## Access Swagger UI
While the application is running, open a web browser and navigate to the Swagger UI:
- `http://localhost:8080/swagger-ui/index.html` via the default URL
- `http://localhost:8080/docs` via your custom URL 

Replace 8080 with your actual port if different.

##  Explore API Documentation
You should see the Swagger UI interface with your API documentation. Explore the available endpoints, make requests, and view the documentation generated from your annotations.

# Security
## Applied simplified measure by using environment variables
### Configure API Key as environment variable
Set your API key as an environment variable via the following command:
- `export MOVIE_API_KEY=c3daaf8e64cab6bbfc87164852bdc9f7`

### Update your application properties
In your application.properties file, do not hardcode the API key. Instead, refer to the environment variable.
This promotes security best practices by avoiding hardcoding sensitive information directly into the code.

## Additional measure you could take
- When communicating with external APIs, make sure to use secure channels (e.g., HTTPS) to encrypt data during transit. This helps protect the API key from interception.
- Be cautious with logging. Avoid logging API keys or sensitive information. If logging is necessary for debugging or monitoring, use appropriate tools and ensure that logs are not accessible to unauthorized users.
- Apply secure deployment practices, including using secure containers and securing server configurations.
- Consider using API management services that provide additional security features, such as rate limiting, access controls, and analytics.

# Testing
### Post a movie search request via CURL from command line
Valid examples
- `curl -i -X POST -H 'Content-Type: application/json' -d '{"genre":"thriller","actor":"Brad Pitt","year":"2009"}' http://localhost:8080/movies`
- `curl -i -X POST -H 'Content-Type: application/json' -d '{"year":"2009","pages":"10"}' http://localhost:8080/movies`
- `curl -i -X POST -H 'Content-Type: application/json' -d '{"pages":"2","year":"2010"}' http://localhost:8080/movies`
- `curl -i -X POST -H 'Content-Type: application/json' -d '{"genre":"thriller"}' http://localhost:8080/movies`
`curl -i -X POST -H 'Content-Type: application/json' -d '{"pages":"2"}' http://localhost:8080/movies`
- `curl -i -X POST -H 'Content-Type: application/json' -d '{}' http://localhost:8080/movies`

Erroneous examples
- `curl -i -X POST -H 'Content-Type: application/json' -d '{"ge":"thriller"}' http://localhost:8080/movies`

## Test your connection directly in browser
`https://api.themoviedb.org/3/discover/movie?api_key=c3daaf8e64cab6bbfc87164852bdc9f7&year=2001`

# Pagination
Configurable number of pages displayed via the 'pages' variable. Default display is one page.

# Run within the Docker container
## Run from command line via JAR
Build an executable JAR
- `mvn clean package`

Run the JAR file
- `java -jar target/movies-api-0.0.1-SNAPSHOT.jar`

## Run within the Docker container
After creating the Dockerfile in the root directory, run
- `mvn clean install`

Build the Docker image using the Dockerfile. Go to the root directory containing the Dockerfile and run
- `sudo docker build -t  movie-api .`

Run the Docker container based on the image you just built
- `sudo docker run -p 8080:8080 movie-api:latest`

From another terminal post a request via CURL to test that the application is running in the container
- `curl -i -X POST -H 'Content-Type: application/json' -d '{"year":"2009","pages":"10"}' http://localhost:8080/movies`
