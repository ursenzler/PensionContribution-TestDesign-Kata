module PensionContributionCalculations.PensionContributionCalculations

open FsToolkit.ErrorHandling
open PensionContributionCalculations.Errors
open FSharp.UMX

type TenurePercentages =
    | Short
    | Medium
    | Long

type SeniorityPercentages =
    | Junior
    | MidLevel
    | Senior

open PensionContributionCalculations.Employees

let (|LongTenure|MediumTenure|ShortTenure|) tenure =
    if tenure >= %10 then LongTenure // BUG: Should be a long tenure bonus for 15 years or more
    elif tenure >= %5 then MediumTenure
    else ShortTenure

let getPensionContributionBonus (employee: Employee) =
    match employee.Seniority with
    | Junior -> 0.00m
    | MidLevel -> 0.07m // 7% for MidLevel employees
    | Senior -> 0.10m // 10% for Senior employees

let calculatePensionContribution
    getEmployeeById
    (getBaseContributionRate: unit -> Async<decimal>)
    (getTenurePercentage: TenurePercentages -> Async<decimal>)
    (getSeniorityPercentage: SeniorityPercentages -> Async<decimal>)
    employeeId
    =
    parallelAsyncResult {
        let! (employee: Employee) =
            getEmployeeById employeeId
            |> AsyncResult.requireSome (EmployeeNotFound employeeId)

        let! tenureBonus =
            match employee.Tenure with
            | ShortTenure -> TenurePercentages.Short
            | MediumTenure -> TenurePercentages.Medium
            | LongTenure -> TenurePercentages.Long
            |> getTenurePercentage

        and! seniorityBonus =
            match employee.Seniority with
            | Junior -> 0.00m |> Async.singleton
            | MidLevel -> SeniorityPercentages.MidLevel |> getSeniorityPercentage
            | Senior -> SeniorityPercentages.Senior |> getSeniorityPercentage

        and! baseContributionRate = getBaseContributionRate ()

        let totalContributionPercentage =
            baseContributionRate + tenureBonus + seniorityBonus

        return employee.AnnualSalary * totalContributionPercentage / 10.0m
    }
