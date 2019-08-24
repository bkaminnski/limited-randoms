package com.hclc.limitedrandoms;

import java.math.BigDecimal;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

public class LimitedRandomsInput {
    private final BigDecimal totalSum;
    private final int numberOfValues;
    private final List<BigDecimal> limits;
    private final int precision;
    private final LimitType limitType;

    public LimitedRandomsInput(String[] args) {
        this(
                new BigDecimal(args[0]),
                parseInt(args[1]),
                parseInt(args[2]),
                of(args[3].split(";")).map(BigDecimal::new).collect(toList()),
                LimitType.valueOf(args[4])
        );
    }

    public LimitedRandomsInput(BigDecimal totalSum, int numberOfValues, int precision, List<BigDecimal> limits, LimitType limitType) {
        this.limitType = limitType;
        if (totalSum == null || totalSum.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("totalSum needs to be positive");
        }
        if (numberOfValues <= 0) {
            throw new IllegalArgumentException("numberOfValues needs to be positive");
        }
        if (precision < 0) {
            throw new IllegalArgumentException("precision cannot be negative");
        }
        if (limits == null || limits.isEmpty()) {
            throw new IllegalArgumentException("at least one limit needs to be provided");
        }
        this.totalSum = totalSum;
        this.numberOfValues = numberOfValues;
        this.precision = precision;
        this.limits = limits;
    }

    RawRandomsWithinLimitGenerator getRawRandomsWithinLimitGenerator() {
        return limitType.generatorInstance(this);
    }

    BigDecimal getTotalSum() {
        return totalSum;
    }

    int getNumberOfValues() {
        return numberOfValues;
    }

    int getPrecision() {
        return precision;
    }

    List<BigDecimal> getLimits() {
        return limits;
    }
}
