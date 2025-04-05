package com.ksoot.common.util.rest.response.builder;

import org.apache.commons.lang3.builder.Builder;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

public interface HeaderBuilder<T, R> extends Builder<R> {

  public HeaderBuilder<T, R> header(final String name, String... value);

  public Builder<R> headers(final HttpHeaders headers);

  public Builder<R> headers(final MultiValueMap<String, String> headers);
}
