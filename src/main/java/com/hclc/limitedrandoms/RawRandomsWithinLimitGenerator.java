package com.hclc.limitedrandoms;

import java.math.BigDecimal;
import java.util.List;

interface RawRandomsWithinLimitGenerator {

    List<BigDecimal> generate();
}
