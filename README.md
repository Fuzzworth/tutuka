#Tutuka
Tutuka Technical interview

## Installation
```
docker run -it -p [host port]:80 fuzzworth/tutuka:0.0.2
```
## Live Demo
URL http://3.133.141.157/ 
+ http://tutuka.nyangasi.info/ (should the DNS have successfully propagated)
    
## Design considerations

### Data
I made some assumptions about the cardinality of the data that would be ingested;

```
    private String fileName;
    private String profileName;
    private LocalDateTime transactionDate;
    private Double transactionAmount;
    private String transactionNarrative;
    private String transactionDescription;
    private BigInteger transactionID;
    private int transactionType;
    private String walletReference;
```

The assumptions above where based of a careful look of the 
example csv files and some deduction. I assumed the transactionID would
represent a large integer, I opted for the BigInteger class instead of
the primitive type int.

### Algorithm

The algorithm is executed entirely in memory using a combination of HashSet and
HashMaps. I opted to not use any third party applications instead attempting, in the spirit
of the assignment, to show my own logical steps to reach a solution.

```
For each line of the first csv:
    parse line into transaction model
    if line succesfully parsed:
        add transaction to unmatched set
        increment total count for file one records
        if transaction already exists in set:
            add transaction to duplicates set
    else
        add line to malformed Set
        if line already exists in malformed Set:
            add to duplicate malformed set

For each line of the second csv:
    parse line into transaction model
    if line succesfully parsed:
        add transaction to secondFileTransaction set
        increment total count for file second csv records
        if transaction already exists in set:
            add transaction to duplicates set
    else
        add line to malformed Set
        if line already exists in malformed Set:
            add to duplicate malformed set
    
    if transaction exists in unmatched set
        remove from unmateched set
        increment matched count by one
    else
        add transaction to unmatched set   

remove duplicates from unmatched set

count matched and unmatched transactions

process similarities
```

The unmatched algorithm, the algorithm to match transactions based on 
similarity, I implemented  compares the fields of all unmatched transactions. 
This algorithm is perhaps the most costly as it iterates over the list once 
per transaction comparing several fields of a transaction and producing a 
list of possible matches. Not too happy with the running time of this algorithm.

 ### Other Considerations
 
 Choose to go with the Spring Boot Web Framework to handle requests and responses 
 for the web application I will be submitting as the solution. Spring boot is 
 a mature and opinionated framework that allows for testing and modularity, 
 for this reason I have chosen to go with it. Solutions will be coded 
 using Java 11. I have furthermore decided to containerise the application 
 using docker and will host it publicly on amazon using the Fargate platform 
 as well as make the source code and docker image available publicly on 
 github and docker hub respectively.
 
 I decided to use Thymeleaf as well as Bootstrap and JQuery to keep to the process flow that 
 accompanied the task.