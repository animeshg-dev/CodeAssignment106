package com.swissre.util;

import com.swissre.model.Employee;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvEmployeeReader {
    public static List<Employee> readEmployees(String filePath) throws IOException {

        List<Employee> employees = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withFirstRecordAsHeader().withTrim())) {

            for (CSVRecord record : csvParser) {
                String id = record.get("Id");
                String firstName = record.get("firstName");
                String lastName = record.get("lastName");
                double salary = Double.parseDouble(record.get("salary"));
                String managerId = record.isSet("managerId") ? record.get("managerId") : null;

                employees.add(new Employee(id, firstName, lastName, salary, managerId));
            }
        }
        return employees;
    }
}
