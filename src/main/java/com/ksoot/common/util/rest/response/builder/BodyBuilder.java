package com.ksoot.common.util.rest.response.builder;

public interface BodyBuilder<T, R> extends StatusBuilder<T, R> {

  public HeaderBuilder<T, R> body(T body);
}
