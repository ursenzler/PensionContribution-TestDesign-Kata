package sammancoaching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SalaryPensionContributionCalculationTest {

    public static final double NORMAL_BASE_SALARY = 60000.0;
    private SalaryContributionPercentages fakePercentages;

    @BeforeEach
    public void setUp() {
        var salaryPercentages = new HashMap<String, Double>();
        salaryPercentages.put("LONG_TENURE_PERCENTAGE", 3.5);
        salaryPercentages.put("MEDIUM_TENURE_PERCENTAGE", 2.0);
        salaryPercentages.put("NO_TENURE_PERCENTAGE", 0.0);
        salaryPercentages.put("LEADERSHIP_TEAM_PERCENTAGE", 2.5);
        salaryPercentages.put("MID_SENIORITY_PERCENTAGE", 3.0);
        salaryPercentages.put("BASE_CONTRIBUTION_RATE", 5.0);

        fakePercentages = new SalaryContributionPercentages(null) {
            @Override
            public double lookupValue(String namedConstant) {
                return salaryPercentages.get(namedConstant);
            }
        };
    }

    @Test
    public void belowZeroSalary_fails() {
        assertThrows(IllegalArgumentException.class, ()-> PensionContributionCalculator.calculatePensionContribution(
                BigDecimal.valueOf(-1), -1, null, fakePercentages));
    }


    @Test
    public void juniorEmployeeWithNoTenure_BasicContribution() {
        BigDecimal annualSalary = BigDecimal.valueOf(NORMAL_BASE_SALARY);
        int tenure = 0;
        SeniorityLevel seniority = new JuniorEmployee();

        var actualContribution = PensionContributionCalculator.calculatePensionContribution(
                annualSalary, tenure, seniority, fakePercentages);

        assertEquals(3000.0,
                actualContribution.doubleValue(), 0.001);
    }

    @Test
    public void midLevelRecentHire_MediumContribution() {
        BigDecimal annualSalary = BigDecimal.valueOf(NORMAL_BASE_SALARY);
        int tenure = 0;
        SeniorityLevel seniority = new MidLevel();

        var actualContribution = PensionContributionCalculator.calculatePensionContribution(
                annualSalary, tenure, seniority, fakePercentages);

        assertEquals(4800.0,
                actualContribution.doubleValue(), 0.001);
    }

    @Test
    public void midLevelMediumTenure_MediumLargeContribution() {
        BigDecimal annualSalary = BigDecimal.valueOf(NORMAL_BASE_SALARY);
        int tenure = 5;
        SeniorityLevel seniority = new MidLevel();

        var actualContribution = PensionContributionCalculator.calculatePensionContribution(
                annualSalary, tenure, seniority, fakePercentages);

        assertEquals(6000.0,
                actualContribution.doubleValue(), 0.001);
    }

    @Test
    public void leadershipWithLongTenure_MaximumContribution() {
        BigDecimal annualSalary = BigDecimal.valueOf(NORMAL_BASE_SALARY);
        int tenure = 25;
        SeniorityLevel seniority = new LeadershipTeam();

        var actualContribution = PensionContributionCalculator.calculatePensionContribution(
                annualSalary, tenure, seniority, fakePercentages);

        assertEquals(6600.0,
                actualContribution.doubleValue(), 0.001);
    }
}

