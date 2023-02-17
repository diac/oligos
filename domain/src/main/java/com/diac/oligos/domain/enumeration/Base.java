package com.diac.oligos.domain.enumeration;

import java.util.Set;

/**
 * Перечисление для типа "Основание"
 */
public enum Base {

    A("Adenine", Set.of(BaseType.DNA, BaseType.RNA)),
    C("Cytosine", Set.of(BaseType.DNA, BaseType.RNA)),
    G("Guanine", Set.of(BaseType.DNA, BaseType.RNA)),
    T("Thymine", Set.of(BaseType.DNA)),
    U("Uracil", Set.of(BaseType.RNA));

    private final String displayName;
    private final Set<BaseType> baseTypes;

    Base(String displayName, Set<BaseType> baseTypes) {
        this.displayName = displayName;
        this.baseTypes = baseTypes;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Set<BaseType> getBaseTypes() {
        return baseTypes;
    }
}