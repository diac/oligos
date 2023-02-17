package com.diac.oligos.domain.enumeration;

/**
 * Перечисление "Категория модификатора"
 */
public enum ModificationCategory {

    ATTACHMENT_CHEMISTRY_LINKER("Attachment Chemistry/Linker"),
    FLUOROPHORE("Fluorophore"),
    MODIFIED_BASE("Modified Base"),
    QUENCHER("Quencher"),
    SPACER("Spacer");

    private final String displayName;

    public String getDisplayName() {
        return displayName;
    }

    ModificationCategory(String displayName) {
        this.displayName = displayName;
    }
}