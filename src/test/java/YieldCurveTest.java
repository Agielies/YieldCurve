import java.time.LocalDate;
import java.util.List;
import junit.framework.TestCase;

public class YieldCurveTest extends TestCase {
    private List<LocalDate> dates;
    private List<Double> bidRates;
    private List<Double> askRates;
    private YieldCurve yieldCurve;

    public YieldCurveTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Initialize the test data
        dates = List.of(
            LocalDate.of(2024, 5, 17), LocalDate.of(2024, 8, 15), LocalDate.of(2024, 11, 13),
            LocalDate.of(2025, 2, 11), LocalDate.of(2025, 5, 12), LocalDate.of(2025, 8, 10),
            LocalDate.of(2025, 11, 8), LocalDate.of(2026, 2, 6), LocalDate.of(2026, 5, 7)
        );

        bidRates = List.of(
            4.50, 5.00, 6.00, 7.20, 7.60, 8.10, 9.00, 10.00, 11.30
        );

        askRates = List.of(
            4.55, 5.05, 6.05, 7.25, 7.65, 8.15, 9.05, 10.05, 11.35
        );

        yieldCurve = new YieldCurve(dates, bidRates, askRates);
    }

    public void testGetRate_Bid() {
        // Test getting bid rate for a specific date
        assertEquals(6.0, yieldCurve.getRate(LocalDate.of(2024, 11, 13), RateType.BID));
        assertEquals(4.75, yieldCurve.getRate(LocalDate.of(2024, 7, 1), RateType.BID));
    }

    public void testGetRate_Ask() {
        // Test getting ask rate for a specific date
        assertEquals(6.05, yieldCurve.getRate(LocalDate.of(2024, 11, 13), RateType.ASK));
        assertEquals(4.8, yieldCurve.getRate(LocalDate.of(2024, 7, 1), RateType.ASK));
    }

    public void testGetRate_Mid() {
        // Test getting mid rate for a specific date
        assertEquals(6.025, yieldCurve.getRate(LocalDate.of(2024, 11, 13), RateType.MID));
        assertEquals(4.775, yieldCurve.getRate(LocalDate.of(2024, 7, 1), RateType.MID));
        
    }

    public void testGetRate_ExceptionBeforeFirstDate() {
    // Test exception handling for date before the first date in the yield curve
    try {
        yieldCurve.getRate(LocalDate.of(2024, 5, 16), RateType.BID);
        fail("Expected IllegalArgumentException was not thrown");
    } catch (IllegalArgumentException e) {
        assertEquals("Date is before the first date in the yield curve.", e.getMessage());
    }
}

    public void testExtrapolationAfterLastDate() {
        // Test extrapolation for date after the last date in the yield curve
        assertEquals(11.3, yieldCurve.getRate(LocalDate.of(2027, 1, 1), RateType.BID));
        assertEquals(11.35, yieldCurve.getRate(LocalDate.of(2027, 1, 1), RateType.ASK));
        assertEquals(11.325, yieldCurve.getRate(LocalDate.of(2027, 1, 1), RateType.MID));

    }
}
