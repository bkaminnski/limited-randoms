package com.hclc.limitedrandoms;

public enum LimitType {
    SHARP_LIMIT {
        @Override
        RawRandomsWithinLimitGenerator generatorInstance(LimitedRandomsInput input) {
            return new RawRandomsWithinSharpLimitGenerator(input);
        }
    }, SMOOTH_LIMIT {
        @Override
        RawRandomsWithinLimitGenerator generatorInstance(LimitedRandomsInput input) {
            return new RawRandomsWithinSmoothLimitGenerator(input);
        }
    };

    abstract RawRandomsWithinLimitGenerator generatorInstance(LimitedRandomsInput input);
}
