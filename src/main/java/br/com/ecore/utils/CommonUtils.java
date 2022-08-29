package br.com.ecore.utils;

import br.com.ecore.exception.EcoreNotFoundException;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.function.Function;

public class CommonUtils {

    private CommonUtils() {}

    public static <O> O getOrThrowNotFoundException(Optional<O> value, String message) throws EcoreNotFoundException {
        return value.orElseThrow(() -> new EcoreNotFoundException(message));
    }

}
