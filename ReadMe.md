# Monzo Crawler
It is Basic crawler use BFS algorithm to crawl any url with given number of Threads.

# Create JAR file
- Run `mvn clean compile assembly:single`

# Run JAR file
- Run generated file in the target folder called monzo-1.0-SNAPSHOT-jar-with-dependencies with the command ` java -jar target/monzo-1.0-SNAPSHOT-jar-with-dependencies {arg1} {arg2}` passing the 2 mandatory arguments
- A file called `crawler-result.html` will be generated with a table containing all the URLs crawled along with their links

# Arguments
2 arguments are needed when running the jar file
- {arg1} URL with the format https://monzo.com or https://www.monzo.com
- {arg2} Number of threads to use. Must be greater than 0

# Example
`java -jar target/monzo-1.0-SNAPSHOT-jar-with-dependencies.jar https://monzo.com 20 `
This command will crawl the website https://monzo.com with 20 threads and create a crawl-result-html file.