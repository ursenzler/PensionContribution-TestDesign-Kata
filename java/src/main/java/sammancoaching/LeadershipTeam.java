package sammancoaching;

class LeadershipTeam implements SeniorityLevel {
    @Override
    public double getPensionContributionBonus() {
        // BUG: Should be 2.5 for leadership team level employees
        return 5.0;
    }
}
