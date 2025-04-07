package ai.whilter.common.util.rest.response.builder;

import org.springframework.http.HttpStatus;

public interface StatusBuilder<T, R> {

  HeaderBuilder<T, R> status(final HttpStatus status);

  HeaderBuilder<T, R> status(final int status);
}
