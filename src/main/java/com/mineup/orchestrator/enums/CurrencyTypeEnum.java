package com.mineup.orchestrator.enums;

public enum CurrencyTypeEnum {
    USD("U$S"),
    ARS("$");
    public final String symbol;

    private CurrencyTypeEnum(String symbol) {
        this.symbol = symbol;
    }
}
