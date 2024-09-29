package com.ikservices.oficinamecanica.commons.utils;

import com.ikservices.oficinamecanica.commons.constants.Constants;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;

public class NumberUtil {

    public static BigDecimal parseBigDecimal(String valor) {
        if (Objects.isNull(valor) || valor.isEmpty()) {
            return BigDecimal.ZERO;
        }
        valor = valor.replaceAll("[^0-9.,]","");

        if (valor.contains(",") && valor.contains(".")) {
            valor = valor.replace(",", "_").replace(".", "").replace("_", ".");
        } else if (valor.contains(",")) {
            valor = valor.replace(",", ".");
        }

        return new BigDecimal(valor);
    }

    public static String parseStringMoney(BigDecimal valor) {
        NumberFormat numberFormat = DecimalFormat.getCurrencyInstance(Constants.LOCALE);

        if (Objects.isNull(valor)) {
            return numberFormat.format(0.0);
        }

        return numberFormat.format(valor);
    }
}
