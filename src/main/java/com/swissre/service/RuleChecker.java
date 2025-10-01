package com.swissre.service;

import com.swissre.dto.Violation;
import com.swissre.model.Employee;

import java.util.List;
import java.util.Map;

public interface RuleChecker {
    List<Violation> check(Map<String, Employee> employees, Map<String, java.util.List<Employee>> managerToSubs);
}
