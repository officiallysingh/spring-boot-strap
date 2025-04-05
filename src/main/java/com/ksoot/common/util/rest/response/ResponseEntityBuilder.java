package com.ksoot.common.util.rest.response;

import com.ksoot.common.util.rest.response.builder.AbstractResponseBuilder;
import com.ksoot.common.util.rest.response.builder.BodyBuilder;
import com.ksoot.common.util.rest.response.builder.HeaderBuilder;
import com.ksoot.common.util.rest.response.builder.StatusBuilder;
import java.net.URI;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

/**
 * @author Rajveer Singh
 */
public class ResponseEntityBuilder {

  public static <T> StatusBuilder<T, ResponseEntity<T>> of(final T body) {
    Assert.notNull(body, "Body must not be null");
    return new Builder<>(body);
  }

  public static <T> HeaderBuilder<T, ResponseEntity<T>> of(final Optional<T> body) {
    Assert.notNull(body, "Body must not be null");
    return body.isPresent()
        ? new Builder<>(body.get(), HttpStatus.OK)
        : new Builder<>(HttpStatus.NOT_FOUND);
  }

  public static <T> BodyBuilder<T, ResponseEntity<T>> accepted() {
    return new Builder<>(HttpStatus.ACCEPTED);
  }

  public static <T> BodyBuilder<T, ResponseEntity<T>> badRequest() {
    return new Builder<>(HttpStatus.BAD_REQUEST);
  }

  public static <T> BodyBuilder<T, ResponseEntity<T>> noContent() {
    return new Builder<>(HttpStatus.NO_CONTENT);
  }

  public static <T> BodyBuilder<T, ResponseEntity<T>> notFound() {
    return new Builder<>(HttpStatus.NOT_FOUND);
  }

  public static <T> Builder<T> created(final URI location) {
    return new Builder<>(location);
  }

  public static <T> HeaderBuilder<T, ResponseEntity<T>> created(final URI location, final T body) {
    return new Builder<>(location, body);
  }

  public static <T> HeaderBuilder<T, ResponseEntity<T>> ok(final T body) {
    return new Builder<>(body, HttpStatus.OK);
  }

  public static <T> BodyBuilder<T, ResponseEntity<T>> ok() {
    return new Builder<>(HttpStatus.OK);
  }

  public static class Builder<T> extends AbstractResponseBuilder<T, ResponseEntity<T>> {

    private URI location;

    Builder(final HttpStatus status) {
      super(status);
    }

    Builder(final T body) {
      super(body);
    }

    Builder(final T body, final HttpStatus status) {
      super(body, status);
    }

    Builder(final URI location) {
      super(HttpStatus.CREATED);
      Assert.notNull(location, "location must not be null");
      this.location = location;
    }

    Builder(final URI location, final T body) {
      super(body, HttpStatus.CREATED);
      Assert.notNull(location, "location must not be null");
      Assert.notNull(body, "body must not be null");
      this.location = location;
    }

    @Override
    public ResponseEntity<T> build() {
      ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(this.status);
      if (this.location != null) {
        bodyBuilder.location(this.location);
      }
      if (this.headers != null) {
        bodyBuilder.headers(this.headers);
      }
      return this.body != null ? bodyBuilder.body(this.body) : bodyBuilder.build();
    }
  }

  private ResponseEntityBuilder() {
    throw new IllegalStateException("Just a utility class, not supposed to be instantiated");
  }
}
