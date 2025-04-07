package ai.whilter.common.util.rest.response.builder;

import java.util.Arrays;
import org.apache.commons.lang3.builder.Builder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

public abstract class AbstractResponseBuilder<T, R>
    implements BodyBuilder<T, R>, HeaderBuilder<T, R> {

  protected HttpStatus status;

  protected HttpHeaders headers;

  protected T body;

  protected AbstractResponseBuilder(final HttpStatus status) {
    Assert.notNull(status, "status must not be null");
    this.status = status;
  }

  protected AbstractResponseBuilder(final T body) {
    Assert.notNull(body, "body must not be null");
    this.body = body;
  }

  protected AbstractResponseBuilder(final T body, final HttpStatus status) {
    Assert.notNull(body, "body must not be null");
    Assert.notNull(status, "status must not be null");
    this.body = body;
    this.status = status;
  }

  @Override
  public HeaderBuilder<T, R> body(final T body) {
    Assert.notNull(body, "body must not be null");
    this.body = body;
    return this;
  }

  @Override
  public HeaderBuilder<T, R> status(final HttpStatus status) {
    Assert.notNull(status, "status must not be null");
    this.status = status;
    return this;
  }

  @Override
  public HeaderBuilder<T, R> status(int status) {
    this.status = HttpStatus.valueOf(status);
    return this;
  }

  protected void addHeader(final String name, final String... values) {
    if (this.headers == null) {
      this.headers = new HttpHeaders();
    }
    this.headers.put(name, Arrays.asList(values));
  }

  protected void addHeaders(final HttpHeaders headers) {
    if (this.headers == null) {
      this.headers = headers;
    } else {
      this.headers.putAll(headers);
    }
  }

  @Override
  public HeaderBuilder<T, R> header(final String name, String... values) {
    Assert.hasLength(name, "Header name must not be null or empty!");
    Assert.isTrue(values != null && values.length > 0, "Header values must not be null or empty!");
    this.addHeader(name, values);
    return this;
  }

  @Override
  public Builder<R> headers(final HttpHeaders headers) {
    Assert.isTrue(headers != null && !headers.isEmpty(), "headers must not be null or empty");
    this.addHeaders(headers);
    return this;
  }

  @Override
  public Builder<R> headers(final MultiValueMap<String, String> headers) {
    Assert.isTrue(headers != null && !headers.isEmpty(), "headers must not be null or empty");
    this.addHeaders(HttpHeaders.readOnlyHttpHeaders(headers));
    return this;
  }
}
