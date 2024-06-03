import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;

public class YieldCurve {
    private final List<Double> daysFromStart;
    private final double firstDate;
    private final Map<RateType, List<Double>> rateMap;

    /**
     * Constructs a YieldCurve object with the given dates, bid rates, and ask
     * rates.
     *
     * @param dates    The list of dates for the yield curve.
     * @param bidRates The list of bid rates corresponding to the dates.
     * @param askRates The list of ask rates corresponding to the dates.
     */
    public YieldCurve(final List<LocalDate> dates, final List<Double> bidRates, final List<Double> askRates) {
        firstDate = dates.get(0).toEpochDay();
        this.daysFromStart = dates.stream().mapToDouble(date -> date.toEpochDay() - firstDate).boxed().toList();
        rateMap = Map.of(
                RateType.BID, bidRates,
                RateType.ASK, askRates,
                RateType.MID, calculateMidRates(bidRates, askRates));
    }

    /**
     * Calculates the mid rates based on the bid and ask rates.
     *
     * @param bidRates The list of bid rates.
     * @param askRates The list of ask rates.
     * @return The list of calculated mid rates.
     */
    private List<Double> calculateMidRates(final List<Double> bidRates, final List<Double> askRates) {
        List<Double> midRates = new ArrayList<>();
        for (int i = 0; i < bidRates.size(); i++) {
            double midRate = (bidRates.get(i) + askRates.get(i)) / 2;
            midRates.add(midRate);
        }
        return midRates;
    }

    /**
     * Retrieves the rate for a given date and rate type.
     *
     * @param date     The date for which the rate is requested.
     * @param rateType The type of rate requested (BID, ASK, or MID).
     * @return The interpolated rate for the given date and rate type.
     * @throws IllegalArgumentException If the date is before the first date in the
     *                                  yield curve.
     */
    public double getRate(final LocalDate date, final RateType rateType) {
        double days = (date.toEpochDay() - firstDate) - daysFromStart.get(0);
        if (days < 0) {
            throw new IllegalArgumentException("Date is before the first date in the yield curve.");
        }

        List<Double> rates = rateMap.get(rateType);
        if (days > daysFromStart.get(daysFromStart.size() - 1)) {
            return rates.get(rates.size() - 1);
        }

        int index = Collections.binarySearch(daysFromStart, days);
        index = (index >= 0) ? index : -(index + 1) - 1;

        return linearInterpolate(
                days,
                daysFromStart.get(index),
                daysFromStart.get(index + 1),
                rates.get(index),
                rates.get(index + 1));
    }

    /**
     * Performs linear interpolation between two points.
     *
     * @param days         The number of days from the start date.
     * @param previousDays The number of days from the start date for the previous
     *                     point.
     * @param nextDays     The number of days from the start date for the next
     *                     point.
     * @param previousRate The rate for the previous point.
     * @param nextRate     The rate for the next point.
     * @return The interpolated rate.
     */
    private double linearInterpolate(final double days, final double previousDays, final double nextDays,
            final double previousRate,
            final double nextRate) {
        return previousRate + (nextRate - previousRate) * (days - previousDays) / (nextDays - previousDays);
    }
}
