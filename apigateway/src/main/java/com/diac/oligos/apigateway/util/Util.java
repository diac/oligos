package com.diac.oligos.apigateway.util;

import org.springframework.beans.factory.annotation.Value;

public class Util {

    @Value("${securityPrefix}")
    private static String securityPrefix;

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

    public static boolean isTokenValid(String barerToken) {
        return validString(barerToken) && barerToken.startsWith(securityPrefix);
    }
}