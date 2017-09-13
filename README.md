# AutoSuggest

### Auto Suggestion Scenarios:
**Normal Search:**
http://localhost:8080/suggest_cities?start=port&atmost=10

**Fuzzy Search:**
http://localhost:8080/suggest_cities?start=oprtblair&atmost=10&fuzzy=true

**Three types of Fuzzy search has been implemented.**

**1. Levenshtein**
http://localhost:8080/suggest_cities?start=oprtblair&atmost=10&fuzzy=true&alg=levenshtein&threshold=4

**2. Jaro Winkler Distance**
http://localhost:8080/suggest_cities?start=oprtblair&atmost=10&fuzzy=true&alg=jaro
http://localhost:8080/suggest_cities?start=oprtblair&atmost=10&fuzzy=true

**3. Apache commons inbuilt Fuzzy Score search**
http://localhost:8080/suggest_cities?start=oprtblair&atmost=10&fuzzy=fuzzyscore

**URL Params Explained:**

* **start:** start of the search word - **required**
* **atmost:** maximum results required to be displayed -**optional, default is 10**
* **fuzzy:** (true/false): fuzzy search -**optional, default is false**
* **algo:** Fuzzy search algorithm (**Jaro** by default.) -**optional, default is jaro. Applicable only if fuzzy is true**
* **threshold:** (only for Levenshtein): the more the value of threshold, the  liberal the results will be. - **optional, if fuzzy is true and levenshtein algorithm chosen then default is 3**


### Endpoint to generate JSON response
* Normal
http://localhost:8080/suggest_cities1?start=port&atmost=10

* Fuzzy
http://localhost:8080/suggest_cities1?start=oprtblair&atmost=10&fuzzy=true


### How to run:

**Method 1: Build & Run**
* Clone code 
```
git clone https://github.com/cvank/AutoSuggest
```
* go to parent folder
* run command - ```mvn clean install```
* cd to target directory
* And run following command.
```java -jar assesment-0.0.1-SNAPSHOT.jar```

**Method 2:**
* Go to run folder at below location
https://github.com/cvank/AutoSuggest/tree/master/run
* download jar
* And run command
```java -jar assesment-0.0.1-SNAPSHOT.jar```
