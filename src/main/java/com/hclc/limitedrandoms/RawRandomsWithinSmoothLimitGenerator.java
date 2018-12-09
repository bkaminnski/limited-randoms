package com.hclc.limitedrandoms;

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

class RawRandomsWithinSmoothLimitGenerator implements RawRandomsWithinLimitGenerator {

    private final Input input;

    RawRandomsWithinSmoothLimitGenerator(Input input) {
        this.input = input;
    }

    @Override
    public List<BigDecimal> generate() {
        PolynomialSplineFunction splineFunction = prepareSplineFunction();
        return IntStream
                .range(0, input.getNumberOfValues())
                .asDoubleStream()
                .map(splineFunction::value)
                .mapToObj(v -> new BigDecimal(v).multiply(new BigDecimal(ThreadLocalRandom.current().nextDouble())))
                .collect(toList());
    }

    private PolynomialSplineFunction prepareSplineFunction() {
        double[] xAxis = valuesForWhichNextLimitStarts();
        double[] yAxis = limits();
        return new SplineInterpolator().interpolate(xAxis, yAxis);
    }

    private double[] valuesForWhichNextLimitStarts() {
        return IntStream
                .concat(
                        IntStream.of(0),
                        IntStream.range(1, input.getLimits().size())).map(i -> (int) (1.0 * i / (input.getLimits().size() - 1) * input.getNumberOfValues()))
                .asDoubleStream()
                .toArray();
    }

    private double[] limits() {
        return input.getLimits().stream().mapToDouble(BigDecimal::doubleValue).toArray();
    }
}
