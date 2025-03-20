package sammancoaching;

class LeadershipTeam implements SeniorityLevel {
    @Override
    public double getPensionContributionBonus(SalaryContributionPercentages databaseAccess) {
        // BUG: Should be LEADERSHIP_TEAM_PERCENTAGE
        return databaseAccess.lookupValue(SalaryContributionPercentages.MID_SENIORITY_PERCENTAGE);
    }
}
