package sammancoaching;

public interface DatabaseAccess {
    Employee getEmployeeById(int employeeId);

    double lookupValue(String namedConstant);
}
