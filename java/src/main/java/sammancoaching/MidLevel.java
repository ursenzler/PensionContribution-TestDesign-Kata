package sammancoaching;

class MidLevel implements SeniorityLevel {
    @Override
    public double getPensionContributionBonus(SalaryContributionPercentages databaseAccess) {
        return databaseAccess.lookupValue(SalaryContributionPercentages.MID_SENIORITY_PERCENTAGE);
    }
}