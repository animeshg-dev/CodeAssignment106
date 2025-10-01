package com.swissre.service;

import com.swissre.dto.Violation;
import com.swissre.model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportingDepthChecker implements RuleChecker {
    private static final int MAX_DEPTH = 5; // CEO at depth 1
    private final Map<String, Integer> depthCache = new HashMap<>();

    @Override
    public List<Violation> check(Map<String, Employee> employees, Map<String, List<Employee>> managerToSubs) {
        List<Violation> violations = new ArrayList<>();

        for (Employee e : employees.values()) {
            int depth = calculateDepth(e, employees);
            if (depth > MAX_DEPTH) {
                violations.add(new Violation(Violation.Type.DEPTH,
                        e.getId(), e.getFullName(),
                        String.format("Depth = %d (too long by %d)", depth, (depth - MAX_DEPTH))));
            }
        }
        return violations;
    }

    private int calculateDepth(Employee e, Map<String, Employee> employees) {
        if (depthCache.containsKey(e.getId())) return depthCache.get(e.getId());

        int depth = 1;
        Employee current = e;
        while (current.getManagerId() != null) {
            Employee manager = employees.get(current.getManagerId());
            if (manager == null) break;
            if (depthCache.containsKey(manager.getId())) {
                depth += depthCache.get(manager.getId());
                break;
            }
            depth++;
            current = manager;
        }
        depthCache.put(e.getId(), depth);
        return depth;
    }
}
