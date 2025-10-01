package com.swissre.service;

import com.swissre.dto.Violation;
import com.swissre.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SalaryRuleChecker implements RuleChecker {
    private static final double MIN_FACTOR = 1.2;
    private static final double MAX_FACTOR = 1.5;

    @Override
    public List<Violation> check(Map<String, Employee> employees, Map<String, List<Employee>> managerToSubs) {
        List<Violation> violations = new ArrayList<>();

        for (Map.Entry<String, List<Employee>> entry : managerToSubs.entrySet()) {
            Employee manager = employees.get(entry.getKey());
            if (manager == null)
                continue;

            List<Employee> subs = entry.getValue();
            if (subs == null || subs.isEmpty())
                continue;

            double avgSalary = subs.stream().mapToDouble(Employee::getSalary).average().orElse(0.0);
            double minAllowed = avgSalary * MIN_FACTOR;
            double maxAllowed = avgSalary * MAX_FACTOR;
            double managerSalary = manager.getSalary();

            if (managerSalary < minAllowed) {
                violations.add(new Violation(Violation.Type.SALARY,
                        manager.getId(), manager.getFullName(),
                        String.format("Salary %.2f is LESS than min allowed %.2f (short by %.2f)",
                                managerSalary, minAllowed, (minAllowed - managerSalary))));
            } else if (managerSalary > maxAllowed) {
                violations.add(new Violation(Violation.Type.SALARY,
                        manager.getId(), manager.getFullName(),
                        String.format("Salary %.2f is MORE than max allowed %.2f (extra %.2f)",
                                managerSalary, maxAllowed, (managerSalary - maxAllowed))));
            }
        }
        return violations;
    }
}
