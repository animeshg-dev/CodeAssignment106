# CodeAssignment106

This project analyzes the organizational structure of a company based on employee data provided in a CSV file.  
It checks for the following:

- Managers earning less or more than allowed compared to their direct subordinates.
- Employees with reporting lines that are too long (more than 4 managers between them and the CEO).

---

## Technologies Used

- Java SE (openjdk 24.0.2 2025-07-15)
- Maven
- JUnit 4.13.2 for unit testing
- [Apache Commons CSV](https://commons.apache.org/proper/commons-csv/) for parsing CSV files

> **Note:** Apache Commons CSV is used because it is efficient and can handle large CSV files, including files with up to 100,000 rows.

---

## Build

Make sure you have Maven installed. Then, from the project root directory:

```
mvn clean package
```

## Run

To run the program, provide the path to your CSV file as an argument:

```
java -jar target/Animesh_Ghosh_CodeAssignment106-1.0-SNAPSHOT-shaded.jar path/to/employees.csv

```

## Run Tests

To run the unit tests:

```
mvn test
```

## CSV File Format

The CSV file should have the following structure:

```
Id,firstName,lastName,salary,managerId
123,Joe,Doe,60000,
124,Martin,Chekov,45000,123
125,Bob,Ronstad,47000,123
300,Alice,Hasacat,50000,124
305,Brett,Hardleaf,34000,300
```
Note: managerId is empty for the CEO. Other employees reference their direct manager by Id.

