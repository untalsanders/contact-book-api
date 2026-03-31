package com.untalsanders.contacts.shared.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Result<T> {

    private final T value;
    private final String error;
    private final boolean success;

    public static <T> Result<T> success(T value) {
        return new Result<>(value, null, true);
    }

    public static <T> Result<T> failure(String error) {
        return new Result<>(null, error, false);
    }

    public boolean isFailure() {
        return !success;
    }

    public <U> Result<U> map(Function<? super T, ? extends U> mapper) {
        if (isSuccess()) {
            return Result.success(mapper.apply(value));
        }
        return Result.failure(error);
    }

    public <U> Result<U> flatMap(Function<? super T, Result<U>> mapper) {
        if (isSuccess()) {
            return mapper.apply(value);
        }
        return Result.failure(error);
    }

    public void ifSuccess(Consumer<? super T> action) {
        if (isSuccess()) {
            action.accept(value);
        }
    }

    public void ifFailure(Consumer<String> action) {
        if (isFailure()) {
            action.accept(error);
        }
    }

    public T getOrElse(T defaultValue) {
        return isSuccess() ? value : defaultValue;
    }

    public <E extends Throwable> T orElseThrow(Function<String, E> exceptionSupplier) throws E {
        if (isSuccess()) {
            return value;
        }
        throw exceptionSupplier.apply(error);
    }

    public Optional<T> toOptional() {
        return Optional.ofNullable(value);
    }

    public boolean isSuccess() {
        return success;
    }
}
