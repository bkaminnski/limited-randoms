package com.hclc.limitedrandoms;

public enum LimitType {

    SHARP_LIMIT {
        @Override
        RawRandomsWithinLimitGenerator generatorInstance(Input input) {
            return new RawRandomsWithinSharpLimitGenerator(input);
        }
    }, SMOOTH_LIMIT {
        @Override
        RawRandomsWithinLimitGenerator generatorInstance(Input input) {
            return new RawRandomsWithinSmoothLimitGenerator(input);
        }
    };

    abstract RawRandomsWithinLimitGenerator generatorInstance(Input input);
}
