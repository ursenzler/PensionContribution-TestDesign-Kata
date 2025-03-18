package sammancoaching;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SalaryPensionContributionCalculationTest {

    public static final double LONG_TENURE_PERCENTAGE = 5.0;
    public static final double LEADERSHIP_TEAM_PERCENTAGE = 5.0;
    private double NO_TENURE_PERCENTAGE = 0.0;
    private double MEDIUM_TENURE_PERCENTAGE = 2.0;
    private double BASE_PERCENTAGE = 5.0;
    private double MID_SENIORITY_PERCENTAGE = 3.0;
    private double BASE_SALARY = 60000.0;


    @Test
    public void belowZeroInput_shouldFail() {
        assertThrows(IllegalArgumentException.class, ()-> PensionContributionCalculator.calculatePensionContribution(
                BigDecimal.valueOf(-1), -1, -1, new JuniorEmployee()));
    }

    @Test
    public void juniorEmployeeWithNoTenure_expects_BaseRate() {
        BigDecimal annualSalary = BigDecimal.valueOf(BASE_SALARY);
        double baseContributionPercentage = BASE_PERCENTAGE;
        int tenure = 0;
        SeniorityLevel seniority = new JuniorEmployee();

        var actualContribution = PensionContributionCalculator.calculatePensionContribution(
                annualSalary, baseContributionPercentage, tenure, seniority);

        assertEquals(BASE_SALARY * (BASE_PERCENTAGE / 100),
                actualContribution.doubleValue(), 0.001);
    }

    @Test
    public void midLevelRecentHire_expects_MediumBonus() {
        BigDecimal annualSalary = BigDecimal.valueOf(BASE_SALARY);
        double baseContributionPercentage = BASE_PERCENTAGE;
        int tenure = 0;
        SeniorityLevel seniority = new MidLevel();

        var actualContribution = PensionContributionCalculator.calculatePensionContribution(
                annualSalary, baseContributionPercentage, tenure, seniority);

        assertEquals(BASE_SALARY *
                        ((BASE_PERCENTAGE + NO_TENURE_PERCENTAGE + MID_SENIORITY_PERCENTAGE) / 100),
                actualContribution.doubleValue(), 0.001);
    }

    @Test
    public void midLevelMediumTenure_expects_MediumLargeBonus() {
        BigDecimal annualSalary = BigDecimal.valueOf(BASE_SALARY);
        double baseContributionPercentage = BASE_PERCENTAGE;
        int tenure = 5;
        SeniorityLevel seniority = new MidLevel();

        var actualContribution = PensionContributionCalculator.calculatePensionContribution(
                annualSalary, baseContributionPercentage, tenure, seniority);

        assertEquals(BASE_SALARY *
                        ((BASE_PERCENTAGE + MEDIUM_TENURE_PERCENTAGE + MID_SENIORITY_PERCENTAGE) / 100),
                actualContribution.doubleValue(), 0.001);
    }

    @Test
    public void leadershipWithLongTenure_expects_MaximumContribution() {
        BigDecimal annualSalary = BigDecimal.valueOf(BASE_SALARY);
        double baseContributionPercentage = BASE_PERCENTAGE;
        int tenure = 25;
        SeniorityLevel seniority = new LeadershipTeam();

        var actualContribution = PensionContributionCalculator.calculatePensionContribution(
                annualSalary, baseContributionPercentage, tenure, seniority);

        assertEquals(BASE_SALARY *
                        ((BASE_PERCENTAGE + LONG_TENURE_PERCENTAGE + LEADERSHIP_TEAM_PERCENTAGE) / 100),
                actualContribution.doubleValue(), 0.001);
    }
}

