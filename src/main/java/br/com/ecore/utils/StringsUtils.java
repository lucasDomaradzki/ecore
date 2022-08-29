package br.com.ecore.utils;

import static org.apache.commons.lang3.StringUtils.*;

public class StringsUtils {

    private StringsUtils(){}

    public static String prepareRoleName(String role) {
        return trim(upperCase(replace(role, " ", "_")));
    }

}
