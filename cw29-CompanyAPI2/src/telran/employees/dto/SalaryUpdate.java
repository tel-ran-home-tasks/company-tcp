package telran.employees.dto;

import java.io.Serializable;

public class SalaryUpdate implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private final int salary;

    public SalaryUpdate(int id, int salary) {
        this.id = id;
        this.salary = salary;
    }

    public int getId() { return id; }
    public int getSalary() { return salary; }

    @Override
    public String toString() {
        return "SalaryUpdate{id=" + id + ", salary=" + salary + "}";
    }
}
