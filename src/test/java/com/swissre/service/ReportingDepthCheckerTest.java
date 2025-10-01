package com.swissre.service;

import com.swissre.dto.Violation;
import com.swissre.model.Employee;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ReportingDepthCheckerTest {

    @Test
    public void testEmployeeTooDeepInHierarchy() {
        Employee ceo = new Employee("1", "CEO", "Boss", 100000, null);
        Employee m1 = new Employee("2", "Manager1", "Smith", 80000, "1");
        Employee m2 = new Employee("3", "Manager2", "Jones", 70000, "2");
        Employee m3 = new Employee("4", "Manager3", "Brown", 60000, "3");
        Employee m4 = new Employee("5", "Manager4", "Taylor", 55000, "4");
        Employee m5 = new Employee("6", "Employee", "Green", 50000, "5");

        List<Employee> employees = Arrays.asList(ceo, m1, m2, m3, m4, m5);

        Map<String, Employee> empMap = new HashMap<>();
        for (Employee e : employees) empMap.put(e.getId(), e);

        Map<String, List<Employee>> managerToSubs = new HashMap<>();
        managerToSubs.put("1", Collections.singletonList(m1));
        managerToSubs.put("2", Collections.singletonList(m2));
        managerToSubs.put("3", Collections.singletonList(m3));
        managerToSubs.put("4", Collections.singletonList(m4));
        managerToSubs.put("5", Collections.singletonList(m5));

        ReportingDepthChecker checker = new ReportingDepthChecker();
        List<Violation> violations = checker.check(empMap, managerToSubs);

        assertEquals(1, violations.size());
        assertTrue(violations.get(0).getMessage().contains("too long"));
    }

    @Test
    public void testEmployeeWithinDepthLimit() {
        Employee ceo = new Employee("1", "CEO", "Boss", 100000, null);
        Employee m1 = new Employee("2", "Manager1", "Smith", 80000, "1");
        Employee m2 = new Employee("3", "Employee", "Jones", 60000, "2");

        List<Employee> employees = Arrays.asList(ceo, m1, m2);

        Map<String, Employee> empMap = new HashMap<>();
        for (Employee e : employees) empMap.put(e.getId(), e);

        Map<String, List<Employee>> managerToSubs = new HashMap<>();
        managerToSubs.put("1", Collections.singletonList(m1));
        managerToSubs.put("2", Collections.singletonList(m2));

        ReportingDepthChecker checker = new ReportingDepthChecker();
        List<Violation> violations = checker.check(empMap, managerToSubs);

        assertTrue(violations.isEmpty());
    }
}