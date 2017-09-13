# AutoSuggest

### Auto Suggestion Scenarios:
** Normal Search:
http://localhost:8080/suggest_cities?start=port&atmost=10

** Fuzzy Search:
http://localhost:8080/suggest_cities?start=oprtblair&atmost=10&fuzzy=true&threshold=4


** Endpoint to generate JSON response
* Normal
http://localhost:8080/suggest_cities1?start=port&atmost=10

** Fuzzy
http://localhost:8080/suggest_cities1?start=oprtblair&atmost=10&fuzzy=true&threshold=4


*** How to run:

** Method 1: Build & Run
Clone code 
git clone https://github.com/cvank/AutoSuggest
go to parent folder
run command - mvn clean install
cd to target directory
and run command.
java -jar assesment-0.0.1-SNAPSHOT.jar

*** Method 2:
go to run folder
https://github.com/cvank/AutoSuggest/tree/master/run
download jar
and run command
java -jar assesment-0.0.1-SNAPSHOT.jar
