package br.com.ecore.utils;

import br.com.ecore.exception.EcoreNotFoundException;

import java.util.Optional;

public class CommonUtils {

    private CommonUtils() {}

    public static <O> O getOrThrowNotFoundException(Optional<O> value, String message, Object... args) throws EcoreNotFoundException {
        return value.orElseThrow(() -> new EcoreNotFoundException(message, args));
    }

}
