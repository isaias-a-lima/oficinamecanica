package com.ikservices.oficinamecanica.commons.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumberUtilTest {

    @Test
    public void testParseBigDecimal() {
        BigDecimal expected, actual;

        expected = BigDecimal.ZERO;
        actual = NumberUtil.parseBigDecimal(null);
        Assertions.assertEquals(expected, actual);

        expected = new BigDecimal(NumberUtil.parseStringNumber(getValues().get(0)));
        actual = NumberUtil.parseBigDecimal(getValues().get(0));
        Assertions.assertEquals(expected, actual);

        expected = new BigDecimal(NumberUtil.parseStringNumber(getValues().get(2)));
        actual = NumberUtil.parseBigDecimal(getValues().get(2));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testParseStringMoney() {

        for (String value : getValues()) {
            String expected = NumberUtil.parseStringNumber(value);

            String actual = NumberUtil.parseStringNumber(NumberUtil.parseStringMoney(new BigDecimal(expected)));

            Assertions.assertEquals(expected, actual);
        }
    }

    @Test
    public void testParseStringMoneyWithNullParam() {

        String expected = NumberUtil.parseStringNumber(null);

        String actual = NumberUtil.parseStringNumber(NumberUtil.parseStringMoney(null));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testParseStringPercent() {

        Map<String, BigDecimal> values = getPercentValues();

        for (String key : values.keySet()) {

            String actual = NumberUtil.parseStringPercent(values.get(key));

            Assertions.assertEquals(key, actual);
        }
    }

    @Test
    public void testCalcPrice() {
        BigDecimal expected = BigDecimal.valueOf(350).setScale(2, RoundingMode.HALF_UP);
        BigDecimal tested = BigDecimal.ZERO;
        tested = NumberUtil.calcPrice(4, BigDecimal.valueOf(50), BigDecimal.ZERO);
        tested = tested.add(NumberUtil.calcPrice(1, BigDecimal.valueOf(50), BigDecimal.ZERO));
        tested = tested.add(NumberUtil.calcPrice(1, BigDecimal.valueOf(200), BigDecimal.valueOf(50)));

        Assertions.assertEquals(expected, tested);
    }

    @Test
    public void testCalcPriceWithPartAndServiceCost() {
        BigDecimal expected = BigDecimal.valueOf(190).setScale(2, RoundingMode.HALF_UP);
        BigDecimal actual = NumberUtil.calcPrice(5,BigDecimal.valueOf(20), BigDecimal.valueOf(20), BigDecimal.valueOf(5));
        Assertions.assertEquals(expected, actual);
    }

    private List<String> getValues() {
        return Arrays.asList(new String[]{
                "R$ 1.299,50",
                "R$ 1,299.50",
                "R$ 1299,50",
                null
        });
    }

    private Map<String, BigDecimal> getPercentValues() {
        Map<String, BigDecimal> map = new HashMap<>();
        map.put("0 %", null);
        map.put("10 %", BigDecimal.valueOf(10));
        map.put("50 %", BigDecimal.valueOf(50.00));
        map.put("10,50 %", BigDecimal.valueOf(10.5));
        map.put("10,59 %", BigDecimal.valueOf(10.59));
        map.put("10,60 %", BigDecimal.valueOf(10.5999));
        return map;
    }
}
