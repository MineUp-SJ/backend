package com.mineup.orchestrator.enums;

public enum ProductTypeEnum {
   SERVICE("SERVICE"),
    PRODUCT("PRODUCT");

    private final String type;

    ProductTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
