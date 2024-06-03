# Yield Curve

The Yield Curve Program is designed to handle and retrieve bid, ask, and mid rates for specified dates.

To build and run the project, use Maven:

1. **Build the project:**

    ```sh
    mvn compile
    ```

2. **Run tests:**

    ```sh
    mvn test
    ```

3. **Generate code coverage report:**

    ```sh
    mvn jacoco:report
    ```
4. **Generate documentation:**

    ```sh
    javadoc -d docs src/main/java/*.java
    ```

## Example 

1. **Instantiate a Yield Curve:**

    ```java

    List<LocalDate> dates = List.of(
        LocalDate.of(2024, 5, 17), LocalDate.of(2024, 8, 15), LocalDate.of(2024, 11, 13),
        LocalDate.of(2025, 2, 11), LocalDate.of(2025, 5, 12), LocalDate.of(2025, 8, 10),
        LocalDate.of(2025, 11, 8), LocalDate.of(2026, 2, 6), LocalDate.of(2026, 5, 7)
    );

    List<Double> bidRates = List.of(4.50, 5.00, 6.00, 7.20, 7.60, 8.10, 9.00, 10.00, 11.30);
    List<Double> askRates = List.of(4.55, 5.05, 6.05, 7.25, 7.65, 8.15, 9.05, 10.05, 11.35);

    YieldCurve yieldCurve = new YieldCurve(dates, bidRates, askRates);
    ```

1. **Retrieve Bid, Ask and Mid Rate**
    ```java
    LocalDate queryDate = LocalDate.of(2025, 3, 15);
    double bidRate = yieldCurve.getRate(queryDate, RateType.BID);
    double askRate = yieldCurve.getRate(queryDate, RateType.ASK);
    double midRate = yieldCurve.getRate(queryDate, RateType.MID);

    System.out.println("Bid Rate: " + bidRate);
    System.out.println("Ask Rate: " + askRate);
    System.out.println("Mid Rate: " + midRate);    
    ```

## Efficiency Mechanisms

- **Binary Search:** Utilized to quickly find the nearest date indices, improving the efficiency of rate retrieval operations.
- **Stream API:** Used for transforming dates into days from the start date, ensuring concise and readable code.
- **Caching Calculations:** Mid rates are pre-calculated and stored to avoid redundant calculations, enhancing runtime performance.

## Program Design
- **Modular Approach**: The `YieldCurve` class encapsulates all rate-related data and operations, promoting modularity and ease of maintenance.
- **Encapsulation**: Data encapsulation within the class prevents direct manipulation from external sources, ensuring data integrity.
- **Object-Oriented Paradigm**: The class design leverages object-oriented principles, utilizing classes and methods to organize code logically.
- **Abstraction**: Complex implementation details are abstracted away, providing a simplified interface for rate retrieval.
- **Efficiency Considerations**: Efficient algorithms such as binary search are used for fast date retrieval and interpolation.

## Solution Approach

The solution approach was to:

1. Develop a working solution that adheres to the exact specifications.
2. Refactor the code to improve its structure, readability, and maintainability.
3. Write comprehensive unit tests to ensure the correctness of the implementation.
4. Optimize the solution to improve performance and efficiency.

## Data Structures
- **List**: 
  - Used to store dates, bid rates, ask rates, and calculated mid rates, ensuring ordered and indexed data access.
  - Lists are particularly suitable here because they maintain the order of insertion, which is crucial for date-based operations.
- **Map**: 
  - Employed to store different rate types (`BID`, `ASK`, `MID`) associated with their respective lists, allowing efficient rate retrieval based on the rate type.
  - The `Map` data structure allows for O(1) average-time complexity for rate retrieval operations based on the type of rate.
- **ArrayList**: 
  - Used for dynamic array operations, providing fast access and update times.
  - The `ArrayList` implementation is efficient for read operations, which are frequent in this application.
- **Collections**: 
  - Used for binary search operations to efficiently locate indices within the list of days from the start date.
  - The `Collections` utility class provides methods for searching and sorting, which are essential for maintaining the integrity of date-based operations.

## Data Structures

- **Primitive Types:** Used wherever possbile for efficiency.
- **LocalDate:** Used for representing dates such as settlement date, next coupon date, etc.
- **MonthDay:** Used for representing dates that recur annually, such as coupon dates.
- **Map:** Used for storing and retrieving bond details efficiently, for debuggin and testing purposes.
- **Array** Used to return clean and dirty prices, when both are required.
- **Enums and Records:** Utilized for representing bond types and details in a type-safe manner.

## Potential Enhancements
- **Use of Records**: 
  - Records could be used to store `TenorPoint` objects, encapsulating dates, bid rates, and ask rates in a single immutable data structure.
- **Sorting Dates**: 
  - Dates should be sorted beforehand to ensure the binary search operates correctly, maintaining the integrity of rate retrieval.
- **Dynamic Updates**: 
  - The program could be enhanced to allow dynamic addition of `TenorPoint` objects within the `YieldCurve`, improving flexibility.

## Tools Used

- **Maven:** For project management and build automation.
- **Jacoco:** For code coverage analysis.
- **Checkstyle:** For ensuring code adheres to a consistent style.


