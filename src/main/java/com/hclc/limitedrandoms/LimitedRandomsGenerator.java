package com.hclc.limitedrandoms;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;
import static java.util.stream.Collectors.toList;

public class LimitedRandomsGenerator {

    private final Input input;

    public LimitedRandomsGenerator(Input input) {
        this.input = input;
    }

    public List<BigDecimal> generate() {
        List<BigDecimal> raw = input.getRawRandomsWithinLimitGenerator().generate();
        List<BigDecimal> rescaled = rescaleToMatchTotalSum(raw);
        return rescaled;
    }

    private List<BigDecimal> rescaleToMatchTotalSum(List<BigDecimal> raw) {
        BigDecimal sum = raw.stream().reduce(BigDecimal::add).orElse(ZERO);
        BigDecimal factor = new BigDecimal(input.getTotalSum().doubleValue() / sum.doubleValue());
        return raw.stream().map(v -> v.multiply(factor).setScale(input.getPrecision(), HALF_UP)).collect(toList());
    }

    public static void main(String... args) {
        if (args.length == 0) {
            printHelp();
            return;
        }

        Input input = prepareInput(args);
        List<BigDecimal> randoms = generateRandomValues(input);
        printCsv(randoms);
    }

    private static void printHelp() {
        System.out.printf("<total sum> <number of values> <precision> <semicolon separated limits> <limit type: %s or %s>", LimitType.SHARP_LIMIT, LimitType.SMOOTH_LIMIT);
    }

    private static Input prepareInput(String[] args) {
        BigDecimal totalSum = new BigDecimal(args[0]);
        int numberOfValues = Integer.valueOf(args[1]);
        int precision = Integer.valueOf(args[2]);
        List<BigDecimal> limits = Stream.of(args[3].split(";")).map(BigDecimal::new).collect(toList());
        LimitType limitType = LimitType.valueOf(args[4]);
        return new Input(totalSum, numberOfValues, precision, limits, limitType);
    }

    private static List<BigDecimal> generateRandomValues(Input input) {
        return new LimitedRandomsGenerator(input).generate();
    }

    private static void printCsv(List<BigDecimal> randoms) {
        int i = 0;
        for (BigDecimal random : randoms) {
            System.out.println(i++ + "," + random);
        }
    }
}
