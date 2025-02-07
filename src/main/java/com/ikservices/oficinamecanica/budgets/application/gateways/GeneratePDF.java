package com.ikservices.oficinamecanica.budgets.application.gateways;

import java.util.Map;

public interface GeneratePDF<K, V> {
    byte[] createPDF(Map<K, V> data);
}
