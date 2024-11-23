package com.example.demo.entity.enumModel;


public enum ERole {
    ADMIN,
    PRODUCT_MANAGER;
    @Override
    public String toString(){
        return switch (this.ordinal()) {
            case 0 -> "ADMIN";
            case 1 -> "PRODUCT_MANAGER";
            default -> null;
        };
    }
}

