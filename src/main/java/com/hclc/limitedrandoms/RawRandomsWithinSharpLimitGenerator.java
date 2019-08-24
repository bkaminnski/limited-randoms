package com.hclc.limitedrandoms;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

class RawRandomsWithinSharpLimitGenerator extends RawRandomsWithinLimitGenerator {
    private final LimitedRandomsInput input;

    RawRandomsWithinSharpLimitGenerator(LimitedRandomsInput input) {
        this.input = input;
    }

    @Override
    public List<BigDecimal> generate() {
        return IntStream
                .range(0, input.getNumberOfValues())
                .map(this::toLimitIndex)
                .mapToObj(this::toLimitValue)
                .map(this::randomValueAround)
                .collect(toList());
    }

    private int toLimitIndex(int i) {
        return (int) (1.0 * i / input.getNumberOfValues() * input.getLimits().size());
    }

    private BigDecimal toLimitValue(int limitIndex) {
        return input.getLimits().get(limitIndex);
    }
}
