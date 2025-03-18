package sammancoaching;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SalaryPensionContributionCalculationTest {

    public static final double BASE_PERCENTAGE = 5.0;
    public static final double LONG_TENURE_PERCENTAGE = 3.5;
    public static final double MEDIUM_TENURE_PERCENTAGE = 2;
    public static final double NO_TENURE_PERCENTAGE = 0.0;
    public static final double LEADERSHIP_TEAM_PERCENTAGE = 2.5;
    public static final double MID_SENIORITY_PERCENTAGE = 3.0;
    public static final double NORMAL_BASE_SALARY = 60000.0;


    @Test
    public void belowZeroSalary_fails() {
        assertThrows(IllegalArgumentException.class, ()-> PensionContributionCalculator.calculatePensionContribution(
                BigDecimal.valueOf(-1), BASE_PERCENTAGE, -1, null));
    }

    @Test
    public void belowZeroBaseContribution_fails() {
        assertThrows(IllegalArgumentException.class, ()-> PensionContributionCalculator.calculatePensionContribution(
                BigDecimal.valueOf(NORMAL_BASE_SALARY), -1, 0, null));
    }

    @Test
    public void juniorEmployeeWithNoTenure_BasicContribution() {
        BigDecimal annualSalary = BigDecimal.valueOf(NORMAL_BASE_SALARY);
        int tenure = 0;
        SeniorityLevel seniority = new JuniorEmployee();

        var actualContribution = PensionContributionCalculator.calculatePensionContribution(
                annualSalary, BASE_PERCENTAGE, tenure, seniority);

        assertEquals(NORMAL_BASE_SALARY * (BASE_PERCENTAGE / 100),
                actualContribution.doubleValue(), 0.001);
    }

    @Test
    public void midLevelRecentHire_MediumContribution() {
        BigDecimal annualSalary = BigDecimal.valueOf(NORMAL_BASE_SALARY);
        int tenure = 0;
        SeniorityLevel seniority = new MidLevel();

        var actualContribution = PensionContributionCalculator.calculatePensionContribution(
                annualSalary, BASE_PERCENTAGE, tenure, seniority);

        assertEquals(NORMAL_BASE_SALARY *
                        ((BASE_PERCENTAGE + NO_TENURE_PERCENTAGE + MID_SENIORITY_PERCENTAGE) / 100),
                actualContribution.doubleValue(), 0.001);
    }

    @Test
    public void midLevelMediumTenure_MediumLargeContribution() {
        BigDecimal annualSalary = BigDecimal.valueOf(NORMAL_BASE_SALARY);
        int tenure = 5;
        SeniorityLevel seniority = new MidLevel();

        var actualContribution = PensionContributionCalculator.calculatePensionContribution(
                annualSalary, BASE_PERCENTAGE, tenure, seniority);

        assertEquals(NORMAL_BASE_SALARY *
                        ((BASE_PERCENTAGE + MEDIUM_TENURE_PERCENTAGE + MID_SENIORITY_PERCENTAGE) / 100),
                actualContribution.doubleValue(), 0.001);
    }

    @Test
    public void leadershipWithLongTenure_MaximumContribution() {
        BigDecimal annualSalary = BigDecimal.valueOf(NORMAL_BASE_SALARY);
        int tenure = 25;
        SeniorityLevel seniority = new LeadershipTeam();

        var actualContribution = PensionContributionCalculator.calculatePensionContribution(
                annualSalary, BASE_PERCENTAGE, tenure, seniority);

        assertEquals(NORMAL_BASE_SALARY *
                        ((BASE_PERCENTAGE + LONG_TENURE_PERCENTAGE + LEADERSHIP_TEAM_PERCENTAGE) / 100),
                actualContribution.doubleValue(), 0.001);
    }
}

