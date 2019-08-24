package com.hclc.limitedrandoms;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;
import static java.util.stream.Collectors.toList;

public class LimitedRandomsGenerator {
    private final LimitedRandomsInput input;

    public LimitedRandomsGenerator(LimitedRandomsInput input) {
        this.input = input;
    }

    public List<BigDecimal> generate() {
        List<BigDecimal> raw = input.getRawRandomsWithinLimitGenerator().generate();
        return rescaledToMatchTotalSum(raw);
    }

    private List<BigDecimal> rescaledToMatchTotalSum(List<BigDecimal> raw) {
        BigDecimal sum = raw.stream().reduce(BigDecimal::add).orElse(ZERO);
        BigDecimal factor = new BigDecimal(input.getTotalSum().doubleValue() / sum.doubleValue());
        return raw.stream().map(v -> v.multiply(factor).setScale(input.getPrecision(), HALF_UP)).collect(toList());
    }

    public static void main(String... args) {
        if (args.length == 0) {
            printHelp();
            return;
        }

        List<BigDecimal> randoms = new LimitedRandomsGenerator(new LimitedRandomsInput(args)).generate();
        printCsv(randoms);
    }

    private static void printHelp() {
        System.out.printf("<total sum> <number of values> <precision> <semicolon separated limits> <limit type: %s or %s>", LimitType.SHARP_LIMIT, LimitType.SMOOTH_LIMIT);
    }

    private static void printCsv(List<BigDecimal> randoms) {
        int i = 0;
        for (BigDecimal random : randoms) {
            System.out.println(i++ + "," + random);
        }
    }
}
