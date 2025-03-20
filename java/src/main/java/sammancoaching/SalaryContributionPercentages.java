package sammancoaching;

/**
 * This class lets you look up the actual percentages for specific tenure and seniority.
 * Typical values for these percentages:
 *
 * "NO_TENURE_PERCENTAGE" = 0.0
 * "MEDIUM_TENURE_PERCENTAGE" = 2.0
 * "LONG_TENURE_PERCENTAGE" = 3.5
 * "LEADERSHIP_TEAM_PERCENTAGE" = 2.5
 * "MID_SENIORITY_PERCENTAGE" = 3.0
 * "BASE_CONTRIBUTION_RATE" = 5.0
 */
public class SalaryContributionPercentages {
        public static final String NO_TENURE_PERCENTAGE = "NO_TENURE_PERCENTAGE";
        public static final String MEDIUM_TENURE_PERCENTAGE = "MEDIUM_TENURE_PERCENTAGE";
        public static final String LONG_TENURE_PERCENTAGE = "LONG_TENURE_PERCENTAGE";
        public static final String LEADERSHIP_TEAM_PERCENTAGE = "LEADERSHIP_TEAM_PERCENTAGE";
        public static final String MID_SENIORITY_PERCENTAGE = "MID_SENIORITY_PERCENTAGE";
        public static final String BASE_CONTRIBUTION_RATE = "BASE_CONTRIBUTION_RATE";

        private final DatabaseAccess databaseAccess;

    public SalaryContributionPercentages(DatabaseAccess databaseAccess) {
        this.databaseAccess = databaseAccess;
    }

    double lookupValue(String namedConstant) {
        return databaseAccess.lookupValue(namedConstant);
    }
}
