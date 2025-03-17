package sammancoaching;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PensionContributionCalculator {

    private final DatabaseAccessLayer databaseAccessLayer;

    public PensionContributionCalculator(DatabaseAccessLayer databaseAccessLayer) {
        this.databaseAccessLayer = databaseAccessLayer;
    }

    public BigDecimal calculatePensionContribution(int employeeId, double baseContributionPercentage) {
        Employee employee = databaseAccessLayer.getEmployeeById(employeeId);
        return calculatePensionContribution(employee.getAnnualSalary(),
                baseContributionPercentage, employee.getTenure(), employee.getSeniority());
    }

    public static BigDecimal calculatePensionContribution(BigDecimal annualSalary, double baseContributionPercentage, int tenure, SeniorityLevel seniority) {
        if (annualSalary.compareTo(BigDecimal.ZERO) < 0 || baseContributionPercentage < 0 || tenure < 0) {
            throw new IllegalArgumentException("Values must be non-negative.");
        }
        
        double tenureBonus = 0;
        if (tenure >= 10) {
            // BUG: Should be a bonus of 3.5 for over 10 years
            tenureBonus = 5;
        } else if (tenure >= 5) {
            tenureBonus = 2;
        }
        // BUG: Should be a bonus of 5.0 for over 25 years of tenure

        double seniorityBonus = seniority.getPensionContributionBonus();
        double totalContributionPercentage = baseContributionPercentage + tenureBonus + seniorityBonus;

        // BUG: Should be RoundingMode.HALF_UP
        return annualSalary
                .multiply(BigDecimal.valueOf(totalContributionPercentage))
                .divide(new BigDecimal("100"), 2, RoundingMode.CEILING);
    }

}

