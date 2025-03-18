package sammancoaching;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PensionContributionCalculator {

    private final DatabaseAccessLayer databaseAccessLayer;
    private final double baseContributionPercentage;

    public PensionContributionCalculator(DatabaseAccessLayer databaseAccessLayer) {
        this.databaseAccessLayer = databaseAccessLayer;
        this.baseContributionPercentage = databaseAccessLayer.lookupValue("BASE_CONTRIBUTION_RATE");
    }

    public BigDecimal calculatePensionContribution(int employeeId) {
        Employee employee = databaseAccessLayer.getEmployeeById(employeeId);
        return calculatePensionContribution(employee.getAnnualSalary(),
                baseContributionPercentage, employee.getTenure(), employee.getSeniority());
    }

    static BigDecimal calculatePensionContribution(BigDecimal annualSalary,
                                                   double baseContributionPercentage,
                                                   int tenureYears,
                                                   SeniorityLevel seniority) {
        // BUG: Should throw an IllegalArgumentException if either annualSalary or baseContributionPercentage are below zero
        if (annualSalary.compareTo(BigDecimal.ZERO) < 0 || baseContributionPercentage < 0) {
            throw new IllegalArgumentException("Values must be non-negative.");
        }

        double tenureBonus = 0;
        // BUG: Should be a bonus of 3.5 for 10 years or more
        if (tenureYears >= 10) {
            tenureBonus = 3.5;
        } else if (tenureYears >= 5) {
            tenureBonus = 2;
        }

        // BUG: some of the seniority bonuses need adjusting - look in the relevant source files for them
        double seniorityBonus = seniority.getPensionContributionBonus();
        double totalContributionPercentage = baseContributionPercentage + tenureBonus + seniorityBonus;

        return annualSalary
                .multiply(BigDecimal.valueOf(totalContributionPercentage))
                .divide(new BigDecimal("100"), RoundingMode.HALF_UP);
    }

}

