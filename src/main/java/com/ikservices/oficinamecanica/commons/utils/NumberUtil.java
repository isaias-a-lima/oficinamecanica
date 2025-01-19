package com.ikservices.oficinamecanica.commons.utils;

import com.ikservices.oficinamecanica.commons.constants.Constants;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class NumberUtil {

    private static final String ZERO_PERCENT = "0 %";

    public static BigDecimal parseBigDecimal(String value) {
        if (Objects.isNull(value) || value.isEmpty()) {
            return BigDecimal.ZERO;
        }
        value = value.replaceAll("[^0-9.,]","");

        if (value.contains(",") && value.contains(".")) {
            value = value.replace(",", "_").replace(".", "").replace("_", ".");
        } else if (value.contains(",")) {
            value = value.replace(",", ".");
        }

        return new BigDecimal(value);
    }

    public static String parseStringMoney(BigDecimal value) {
        Locale locale = Objects.nonNull(Constants.getLOCALE()) ? Constants.getLOCALE() : new Locale("pt", "BR");
        NumberFormat numberFormat = DecimalFormat.getCurrencyInstance(locale);

        if (Objects.isNull(value)) {
            return numberFormat.format(0.0);
        }

        return numberFormat.format(value);
    }

    public static String parseStringNumber(String value) {
        if (Objects.isNull(value) || value.isEmpty()) {
            return "0.00";
        }
        value = value.replaceAll("[^0-9.,]","");

        Integer dot = value.indexOf(".");

        Integer comma = value.indexOf(",");

        if (value.contains(",") && value.contains(".") && comma.compareTo(dot) < 0 ) {
            value = value.replace(",", "");
        } else if (value.contains(",") && value.contains(".") && comma.compareTo(dot) > 0 ){
            value = value.replace(".", "_").replace(",", ".").replace("_", "");
        } else {
            value = value.replace(",", ".");
        }

        return value;
    }

    /**
     * Convert a BigDecimal value to String value to represent a percentage value.<br>
     * Example 1: If input "10" then output "10 %".<br>
     * Example 2: If input "10.5" then output "10,50 %".
     * @param value
     * @return
     */
    public static String parseStringPercent(BigDecimal value) {

        if (Objects.isNull(value)) {
            return ZERO_PERCENT;
        }

        String valueAux = value.toString();

        String fraction = valueAux.contains(".") ? valueAux.substring(valueAux.indexOf(".")+1) : "0";

        if (Integer.parseInt(fraction) > 0) {
            valueAux = value.setScale(2, RoundingMode.HALF_UP).toString().replace(".", ",").concat(" %");
        } else {
            valueAux = value.setScale(0, RoundingMode.HALF_UP).toString().concat(" %");
        }

        return valueAux;
    }

    public static BigDecimal calcPrice(Integer quantity, BigDecimal cost, BigDecimal discount) {
        //quantity * (cost - ((discount / 100) * cost));
        BigDecimal percentage = discount.divide(BigDecimal.valueOf(100.0));
        BigDecimal discountValue = cost.multiply(percentage);
        BigDecimal costValue = cost.subtract(discountValue);

        return costValue.multiply(BigDecimal.valueOf(quantity.doubleValue())).setScale(2, RoundingMode.HALF_UP);
    }
}
