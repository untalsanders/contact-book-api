package com.untalsanders.contacts.shared.domain;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Implementación del patrón Result para manejar operaciones que pueden
 * tener éxito o fallar, sin recurrir a excepciones para el flujo de control.
 * @param <S> tipo del valor en caso de éxito (Success)
 * @param <F> tipo del error en caso de fallo (Failure)
 */
public sealed interface Result<T> permits Result.Success, Result.Failure {

    record Success<T>(T value) implements Result<T> {}
    record Failure<T>(String error) implements Result<T> {}

    /**
     * Crea un resultado exitoso.
     */
    static <T> Result<T> success(T value) {
        Objects.requireNonNull(value, "The value of success cannot be null");
        return new Success<>(value);
    }

    /**
     * Crea un resultado fallido.
     */
    static <T> Result<T> failure(String error) {
        Objects.requireNonNull(error, "The value of failure cannot be null");
        return new Failure<>(error);
    }

    /**
     * Ejecuta una operación y envuelve el resultado.
     * Si la operación lanza una excepción, retorna un {@link Failure} con el mensaje.
     */
    static <T> Result<T> of(Supplier<T> supplier) {
        try {
            return success(supplier.get());
        } catch (Exception e) {
            return failure(e.getMessage());
        }
    }

    /**
     * Indica si el resultado es exitoso.
     */
    default boolean isSuccess() {
        return this instanceof Success<T>;
    }

    /**
     * Obtiene el valor de éxito.
     *
     * @throws IllegalStateException si el resultado es fallido
     */
    default T getValue() {
        if (this instanceof Success<T>(T value)) return value;
        throw new IllegalStateException("The result is a Failure - check isSuccess() before calling getValue()");
    }

    /**
     * Obtiene el valor de error.
     *
     * @throws IllegalStateException si el resultado es exitoso
     */
    default String getError() {
        if (this instanceof Failure<T>(String error)) return error;
        throw new IllegalStateException("The result is a Success - check isSuccess() before calling getError()");
    }
}
