package com.ikservices.oficinamecanica.customers.application;

public enum SearchCriteria {
    NONE,
    DOCUMENT,
    NAME,
    PHONE,
    PLATE;

    public static SearchCriteria findByOrdinal(int ordinal) {
        for (SearchCriteria criteria : SearchCriteria.values()) {
            if (criteria.ordinal() == ordinal) {
                return criteria;
            }
        }
        return SearchCriteria.NONE;
    }
}
