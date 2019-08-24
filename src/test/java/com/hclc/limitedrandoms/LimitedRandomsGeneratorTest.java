package com.hclc.limitedrandoms;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.within;

public class LimitedRandomsGeneratorTest {

    @ParameterizedTest(name = "{index} => generator ''{0}''")
    @EnumSource(LimitType.class)
    void sumOfGeneratedValuesShouldBeCloseToTotalSum(LimitType limitType) {
        LimitedRandomsInput input = prepareInput(limitType);
        LimitedRandomsGenerator limitedRandomsGenerator = new LimitedRandomsGenerator(input);

        List<BigDecimal> output = limitedRandomsGenerator.generate();
        BigDecimal actualTotalSum = output.stream().reduce(BigDecimal::add).orElse(ZERO);

        Assertions.assertThat(actualTotalSum).isCloseTo(input.getTotalSum(), within(ONE));
    }

    @ParameterizedTest(name = "{index} => generator ''{0}''")
    @EnumSource(LimitType.class)
    void numberOfGeneratedValuesShouldBeTheSameAsInInput(LimitType limitType) {
        LimitedRandomsInput input = prepareInput(limitType);
        LimitedRandomsGenerator limitedRandomsGenerator = new LimitedRandomsGenerator(input);

        List<BigDecimal> output = limitedRandomsGenerator.generate();

        Assertions.assertThat(output.size()).isEqualTo(input.getNumberOfValues());
    }

    private LimitedRandomsInput prepareInput(LimitType limitType) {
        BigDecimal totalSum = new BigDecimal(100000);
        List<BigDecimal> limits = List.of(new BigDecimal(1), new BigDecimal(10), new BigDecimal(1), new BigDecimal(10));
        return new LimitedRandomsInput(totalSum, 1000, 2, limits, limitType);
    }
}
