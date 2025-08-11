namespace PensionContributionCalculations.Employees

type SeniorityLevel =
    | Junior
    | MidLevel
    | Senior

[<Measure>]
type Tenure

[<Measure>]
type Salary

type Employee = { AnnualSalary: decimal<Salary>; Tenure: int<Tenure>; Seniority: SeniorityLevel }