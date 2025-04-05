package com.ksoot.common.util.rest.response.builder;

import org.springframework.http.HttpStatus;

public interface StatusBuilder<T, R> {

  public HeaderBuilder<T, R> status(final HttpStatus status);

  public HeaderBuilder<T, R> status(final int status);
}
