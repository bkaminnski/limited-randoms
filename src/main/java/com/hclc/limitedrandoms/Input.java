package com.hclc.limitedrandoms;

import java.math.BigDecimal;
import java.util.List;

public class Input {

    private final BigDecimal totalSum;
    private final int numberOfValues;
    private final List<BigDecimal> limits;
    private final int precision;
    private final LimitType limitType;

    public Input(BigDecimal totalSum, int numberOfValues, int precision, List<BigDecimal> limits, LimitType limitType) {
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

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public int getNumberOfValues() {
        return numberOfValues;
    }

    public int getPrecision() {
        return precision;
    }

    public List<BigDecimal> getLimits() {
        return limits;
    }

    public LimitType getLimitType() {
        return limitType;
    }
}
