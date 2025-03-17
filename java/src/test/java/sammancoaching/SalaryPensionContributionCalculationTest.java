package sammancoaching;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SalaryPensionContributionCalculationTest {

    private double NO_TENURE_PERCENTAGE = 0.0;
    private double BASE_PERCENTAGE = 5.0;
    private double MID_SENIORITY_PERCENTAGE = 3.0;
    private double BASE_SALARY = 60000.0;

    @Test
    public void Mid_Level_Recent_Hire_expects_Standard_Rate_Plus_Medium_Bonus() {
        BigDecimal annualSalary = BigDecimal.valueOf(BASE_SALARY);
        double baseContributionPercentage = BASE_PERCENTAGE;
        int tenure = 0;
        SeniorityFactor seniority = new MidLevel();

        var actualContribution = PensionContributionCalculator.calculatePensionContribution(
                annualSalary, baseContributionPercentage, tenure, seniority);

        assertEquals(BASE_SALARY *
                        ((BASE_PERCENTAGE + NO_TENURE_PERCENTAGE + MID_SENIORITY_PERCENTAGE) / 100),
                actualContribution.doubleValue(), 0.001);
    }
}

