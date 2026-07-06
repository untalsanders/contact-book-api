package com.untalsanders.contacts.shared.domain.vo;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Implementación del patrón Result para manejar operaciones que pueden
 * tener éxito o fallar, sin recurrir a excepciones para el flujo de control.
 * <p>
 * Ejemplo de uso:
 * <pre>{@code
 * Result<User, String> result = userService.findById(id);
 *
 * result
 *     .onSuccess(user -> System.out.println("Usuario: " + user))
 *     .onFailure(error -> System.out.println("Error: " + error));
 *
 * // Transformar el valor
 * Result<String, String> nameResult = result.map(User::username);
 *
 * // Encadenar operaciones
 * Result<User, String> saved = result.flatMap(user -> userService.save(user));
 *
 * // Obtener valor con fallback
 * User user = result.getOrElse(User.defaultUser());
 * }</pre>
 *
 * @param <S> tipo del valor en caso de éxito (Success)
 * @param <F> tipo del error en caso de fallo (Failure)
 */
public sealed interface Result<S, F> permits Result.Success, Result.Failure {

    /**
     * Crea un resultado exitoso.
     */
    static <S, F> Result<S, F> success(S value) {
        Objects.requireNonNull(value, "El valor de éxito no puede ser nulo");
        return new Success<>(value);
    }

    /**
     * Crea un resultado fallido.
     */
    static <S, F> Result<S, F> failure(F error) {
        Objects.requireNonNull(error, "El valor de error no puede ser nulo");
        return new Failure<>(error);
    }

    /**
     * Ejecuta una operación y envuelve el resultado.
     * Si la operación lanza una excepción, retorna un {@link Failure} con el mensaje.
     */
    static <S> Result<S, String> of(Supplier<S> supplier) {
        try {
            return success(supplier.get());
        } catch (Exception e) {
            return failure(e.getMessage());
        }
    }

    /**
     * Indica si el resultado es exitoso.
     */
    boolean isSuccess();

    /**
     * Indica si el resultado es fallido.
     */
    boolean isFailure();

    /**
     * Obtiene el valor de éxito.
     *
     * @throws IllegalStateException si el resultado es fallido
     */
    S getValue();

    /**
     * Obtiene el valor de error.
     *
     * @throws IllegalStateException si el resultado es exitoso
     */
    F getError();

    /**
     * Obtiene el valor de éxito o un valor por defecto si es fallido.
     */
    S getOrElse(S defaultValue);

    /**
     * Obtiene el valor de éxito o lo produce con un supplier si es fallido.
     */
    S getOrElseGet(Supplier<S> supplier);

    /**
     * Transforma el valor de éxito con la función dada.
     * Si el resultado es fallido, propaga el error sin modificar.
     */
    <U> Result<U, F> map(Function<S, U> mapper);

    /**
     * Transforma el valor de error con la función dada.
     * Si el resultado es exitoso, propaga el valor sin modificar.
     */
    <U> Result<S, U> mapFailure(Function<F, U> mapper);

    /**
     * Transforma el valor de éxito con una función que retorna otro Result.
     * Permite encadenar operaciones que pueden fallar.
     */
    <U> Result<U, F> flatMap(Function<S, Result<U, F>> mapper);

    /**
     * Ejecuta la acción si el resultado es exitoso. Retorna el mismo Result para encadenar.
     */
    Result<S, F> onSuccess(Consumer<S> action);

    /**
     * Ejecuta la acción si el resultado es fallido. Retorna el mismo Result para encadenar.
     */
    Result<S, F> onFailure(Consumer<F> action);

    /**
     * Aplica una de las dos funciones según el resultado.
     */
    <U> U fold(Function<S, U> onSuccess, Function<F, U> onFailure);

    record Success<S, F>(S value) implements Result<S, F> {

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public boolean isFailure() {
            return false;
        }

        @Override
        public S getValue() {
            return value;
        }

        @Override
        public F getError() {
            throw new IllegalStateException("No se puede obtener el error de un resultado exitoso");
        }

        @Override
        public S getOrElse(S defaultValue) {
            return value;
        }

        @Override
        public S getOrElseGet(Supplier<S> supplier) {
            return value;
        }

        @Override
        public <U> Result<U, F> map(Function<S, U> mapper) {
            return success(mapper.apply(value));
        }

        @Override
        public <U> Result<S, U> mapFailure(Function<F, U> mapper) {
            return success(value);
        }

        @Override
        public <U> Result<U, F> flatMap(Function<S, Result<U, F>> mapper) {
            return mapper.apply(value);
        }

        @Override
        public Result<S, F> onSuccess(Consumer<S> action) {
            action.accept(value);
            return this;
        }

        @Override
        public Result<S, F> onFailure(Consumer<F> action) {
            return this;
        }

        @Override
        public <U> U fold(Function<S, U> onSuccess, Function<F, U> onFailure) {
            return onSuccess.apply(value);
        }
    }

    record Failure<S, F>(F error) implements Result<S, F> {

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public boolean isFailure() {
            return true;
        }

        @Override
        public S getValue() {
            throw new IllegalStateException("No se puede obtener el valor de un resultado fallido");
        }

        @Override
        public F getError() {
            return error;
        }

        @Override
        public S getOrElse(S defaultValue) {
            return defaultValue;
        }

        @Override
        public S getOrElseGet(Supplier<S> supplier) {
            return supplier.get();
        }

        @Override
        @SuppressWarnings("unchecked")
        public <U> Result<U, F> map(Function<S, U> mapper) {
            return (Result<U, F>) this;
        }

        @Override
        public <U> Result<S, U> mapFailure(Function<F, U> mapper) {
            return failure(mapper.apply(error));
        }

        @Override
        @SuppressWarnings("unchecked")
        public <U> Result<U, F> flatMap(Function<S, Result<U, F>> mapper) {
            return (Result<U, F>) this;
        }

        @Override
        public Result<S, F> onSuccess(Consumer<S> action) {
            return this;
        }

        @Override
        public Result<S, F> onFailure(Consumer<F> action) {
            action.accept(error);
            return this;
        }

        @Override
        public <U> U fold(Function<S, U> onSuccess, Function<F, U> onFailure) {
            return onFailure.apply(error);
        }
    }
}
