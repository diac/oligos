package com.diac.oligos.domain.enumeration;

/**
 * Перечисление для типа "Тип основания"
 */
public enum BaseType {

    DNA("Deoxyribonucleic acid"),
    RNA("Ribonucleic acid");

    private final String displayName;

    BaseType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}