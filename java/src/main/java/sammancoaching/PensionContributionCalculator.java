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

        return getBigDecimal(baseContributionPercentage, employee);
    }

    public static BigDecimal getBigDecimal(double baseContributionPercentage, Employee employee) {
        if (employee.getAnnualSalary().compareTo(BigDecimal.ZERO) < 0 || baseContributionPercentage < 0 || employee.getTenure() < 0) {
            throw new IllegalArgumentException("Values must be non-negative.");
        }

        double tenureBonus = 0;
        if (employee.getTenure() >= 10) {
            tenureBonus = 5; // Additional 5% for employees with 10+ years
        } else if (employee.getTenure() >= 5) {
            tenureBonus = 2; // Additional 2% for employees with 5+ years
        }
        double seniorityBonus = employee.getSeniority().getPensionContributionBonus();
        double totalContributionPercentage = baseContributionPercentage + tenureBonus + seniorityBonus;

        return employee.getAnnualSalary()
                .multiply(BigDecimal.valueOf(totalContributionPercentage))
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }
}

