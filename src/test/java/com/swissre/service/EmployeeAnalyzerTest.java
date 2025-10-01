package com.swissre.service;

import com.swissre.dto.Violation;
import com.swissre.model.Employee;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class EmployeeAnalyzerTest {

    @Test
    public void testAnalyzerDetectsBothSalaryAndDepthIssues() {
        // CEO
        Employee ceo = new Employee("1", "Alice", "CEO", 100000, null);
        // Salary issue (manager earning less than allowed)
        Employee m1 = new Employee("2", "Bob", "Manager", 50000, "1");
        Employee e1 = new Employee("3", "Charlie", "Dev", 48000, "2");
        Employee e2 = new Employee("4", "David", "Dev", 47000, "2");
        // Depth issue (too deep in hierarchy)
        Employee m2 = new Employee("5", "Eve", "Lead", 60000, "4");
        Employee m3 = new Employee("6", "Frank", "Senior Dev", 55000, "5");
        Employee m4 = new Employee("7", "Grace", "Junior Dev", 50000, "6");

        List<Employee> employees = Arrays.asList(ceo, m1, e1, e2, m2, m3, m4);

        EmployeeAnalyzer analyzer = new EmployeeAnalyzer(employees);
        analyzer.registerRuleChecker(new SalaryRuleChecker());
        analyzer.registerRuleChecker(new ReportingDepthChecker());

        List<Violation> violations = analyzer.analyze();

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getType() == Violation.Type.SALARY));
        assertTrue(violations.stream().anyMatch(v -> v.getType() == Violation.Type.DEPTH));
    }
}