package java8.functionalprograming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Tushar Chokshi @ 6/16/16.
 */
public class Department {
    private String name;
    private List<Employee> employees = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setDepartment(this);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void addEmployees(Employee... emps) {
        Optional.ofNullable(emps)
                .ifPresent((emps1) -> Arrays.stream(emps1)
                                            .forEach((emp) -> Optional.ofNullable(emp)
                                                                      .ifPresent((e) -> addEmployee(e))));
/*
        if(emps != null) {
            Arrays.stream(emps).forEach((emp) -> Optional.ofNullable(emp).ifPresent((e) -> addEmployee(e)));
        }
*/
    }

    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                ", employees=" + employees +
                '}';
    }
}
