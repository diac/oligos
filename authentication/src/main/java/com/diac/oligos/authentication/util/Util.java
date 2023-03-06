package com.diac.oligos.authentication.util;

import java.util.UUID;

public class Util {

    private Util() {

    }

    public static String generateId(String prefix, String format, int sequence) {
        return prefix + String.format(format, sequence);
    }

    public static boolean validString(String input) {
        return input != null && !input.isEmpty();
    }


    public static boolean validLong(long input) {
        return input > 0;
    }

    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }
}