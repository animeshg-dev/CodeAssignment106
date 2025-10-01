package com.swissre.service;

import com.swissre.dto.Violation;
import com.swissre.model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeAnalyzer {
    private final Map<String, Employee> employees = new HashMap<>();
    private final Map<String, List<Employee>> managerToSubs = new HashMap<>();
    private final List<RuleChecker> ruleCheckers = new ArrayList<>();

    public EmployeeAnalyzer(List<Employee> employeeList) {
        for (Employee e : employeeList) {
            employees.put(e.getId(), e);
            if (e.getManagerId() != null) {
                managerToSubs.computeIfAbsent(e.getManagerId(), k -> new ArrayList<>()).add(e);
            }
        }
    }

    public void registerRuleChecker(RuleChecker checker) {
        ruleCheckers.add(checker);
    }

    public List<Violation> analyze() {
        List<Violation> results = new ArrayList<>();
        for (RuleChecker checker : ruleCheckers) {
            results.addAll(checker.check(employees, managerToSubs));
        }
        return results;
    }
}
