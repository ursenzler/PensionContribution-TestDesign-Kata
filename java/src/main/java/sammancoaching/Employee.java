package sammancoaching;

import java.math.BigDecimal;

class Employee {
    private final BigDecimal annualSalary;
    private final int tenure;
    private final SeniorityFactor seniority;

    public Employee(BigDecimal annualSalary, int tenure, SeniorityFactor seniority) {
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

    public SeniorityFactor getSeniority() {
        return seniority;
    }
}
