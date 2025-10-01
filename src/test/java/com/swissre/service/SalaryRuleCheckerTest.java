package com.swissre.service;

import com.swissre.dto.Violation;
import com.swissre.model.Employee;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SalaryRuleCheckerTest {

    @Test
    public void testManagerEarningLessThanAllowed() {
        Employee manager = new Employee("1", "Alice", "Smith", 45000, null); // was 50000
        Employee e1 = new Employee("2", "Bob", "Jones", 40000, "1");
        Employee e2 = new Employee("3", "Charlie", "Brown", 42000, "1");

        Map<String, Employee> employees = new HashMap<>();
        employees.put(manager.getId(), manager);
        employees.put(e1.getId(), e1);
        employees.put(e2.getId(), e2);

        Map<String, List<Employee>> managerToSubs = new HashMap<>();
        managerToSubs.put("1", Arrays.asList(e1, e2));

        SalaryRuleChecker checker = new SalaryRuleChecker();
        List<Violation> violations = checker.check(employees, managerToSubs);

        assertEquals(1, violations.size());
        assertTrue(violations.get(0).getMessage().contains("LESS"));
    }

    @Test
    public void testManagerEarningMoreThanAllowed() {
        Employee manager = new Employee("1", "Alice", "Smith", 100000, null);
        Employee e1 = new Employee("2", "Bob", "Jones", 40000, "1");
        Employee e2 = new Employee("3", "Charlie", "Brown", 42000, "1");

        Map<String, Employee> employees = new HashMap<>();
        employees.put(manager.getId(), manager);
        employees.put(e1.getId(), e1);
        employees.put(e2.getId(), e2);

        Map<String, List<Employee>> managerToSubs = new HashMap<>();
        managerToSubs.put("1", Arrays.asList(e1, e2));

        SalaryRuleChecker checker = new SalaryRuleChecker();
        List<Violation> violations = checker.check(employees, managerToSubs);

        assertEquals(1, violations.size());
        assertTrue(violations.get(0).getMessage().contains("MORE"));
    }

    @Test
    public void testManagerWithinAllowedRange() {
        Employee manager = new Employee("1", "Alice", "Smith", 60000, null);
        Employee e1 = new Employee("2", "Bob", "Jones", 40000, "1");
        Employee e2 = new Employee("3", "Charlie", "Brown", 42000, "1");

        Map<String, Employee> employees = new HashMap<>();
        employees.put(manager.getId(), manager);
        employees.put(e1.getId(), e1);
        employees.put(e2.getId(), e2);

        Map<String, List<Employee>> managerToSubs = new HashMap<>();
        managerToSubs.put("1", Arrays.asList(e1, e2));

        SalaryRuleChecker checker = new SalaryRuleChecker();
        List<Violation> violations = checker.check(employees, managerToSubs);

        assertTrue(violations.isEmpty());
    }
}