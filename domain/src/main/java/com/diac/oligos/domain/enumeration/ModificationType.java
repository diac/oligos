package com.diac.oligos.domain.enumeration;

/**
 * Перечисление "Тип модификатора"
 */
public enum ModificationType {

    MOD_5("5'"),
    INTERNAL("INTERNAL"),
    MOD_3("3'");

    private final String displayName;

    ModificationType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}