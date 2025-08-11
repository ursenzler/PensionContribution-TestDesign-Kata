module PensionContributionCalculations

open FsToolkit.ErrorHandling
open Xunit
open FSharp.UMX
open Swensen.Unquote
open PensionContributionCalculations
open PensionContributionCalculations.Employees
open PensionContributionCalculations.PensionContributionCalculations

let getBaseContributionRate () = 0.05m |> Async.singleton

let getTenurePercentage id =
    match id with
    | TenurePercentages.Short -> 0.00m
    | TenurePercentages.Medium -> 0.05m
    | TenurePercentages.Long -> 0.10m
    |> Async.singleton

let getSeniorityPercentage id =
    match id with
    | SeniorityPercentages.Junior -> 0.00m
    | SeniorityPercentages.MidLevel -> 0.07m
    | SeniorityPercentages.Senior -> 0.10m
    |> Async.singleton

[<Fact>]
let ``a junior with a short tenure gets only the base contribution rate`` () =
    async {
        let employeeId = 1

        let getEmployeeById id =
            if id = 1 then
                { AnnualSalary = %50000m
                  Tenure = %2
                  Seniority = SeniorityLevel.Junior }
                |> Some
            else
                None
            |> Async.singleton

        let! result =
            calculatePensionContribution
                getEmployeeById
                getBaseContributionRate
                getTenurePercentage
                getSeniorityPercentage
                employeeId

        test <@ result = Ok 250.00m<Salary> @>
    }
