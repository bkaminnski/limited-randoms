package com.hclc.limitedrandoms;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

abstract class RawRandomsWithinLimitGenerator {
    private final static BigDecimal MAX_DISTANCE_MULTIPLIER = new BigDecimal(0.2);
    private static final BigDecimal TWO = new BigDecimal(2);

    abstract List<BigDecimal> generate();

    BigDecimal randomValueAround(BigDecimal value) {
        BigDecimal distance = value.multiply(MAX_DISTANCE_MULTIPLIER).abs();
        BigDecimal low = value.subtract(distance);
        return low.add(distance.multiply(TWO).multiply(new BigDecimal(ThreadLocalRandom.current().nextDouble())));
    }
}
