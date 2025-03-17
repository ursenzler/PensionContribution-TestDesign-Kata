package sammancoaching;

import java.math.BigDecimal;

class Employee {
    private final BigDecimal annualSalary;
    private final int tenure;
    private final SeniorityLevel seniority;

    public Employee(BigDecimal annualSalary, int tenure, SeniorityLevel seniority) {
        this.annualSalary = annualSalary;
        this.tenure = tenure;
        this.seniority = seniority;
    }

    public BigDecimal getAnnualSalary() {
        return annualSalary;
    }

    public int getTenure() {
        return tenure;
    }

    public SeniorityLevel getSeniority() {
        return seniority;
    }
}
