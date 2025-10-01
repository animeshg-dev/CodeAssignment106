package com.swissre;

import com.swissre.dto.Violation;
import com.swissre.model.Employee;
import com.swissre.service.EmployeeAnalyzer;
import com.swissre.service.ReportingDepthChecker;
import com.swissre.service.SalaryRuleChecker;
import com.swissre.util.CsvEmployeeReader;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: java -jar org-analyzer.jar <employees.csv>");
            return;
        }

        String filePath = args[0];
        List<Employee> employees = CsvEmployeeReader.readEmployees(filePath);

        EmployeeAnalyzer analyzer = new EmployeeAnalyzer(employees);
        analyzer.registerRuleChecker(new SalaryRuleChecker());
        analyzer.registerRuleChecker(new ReportingDepthChecker());

        List<Violation> violations = analyzer.analyze();

        System.out.println("===== Analysis Report =====");
        violations.forEach(System.out::println);
    }
}
