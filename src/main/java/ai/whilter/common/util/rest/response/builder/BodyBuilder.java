package ai.whilter.common.util.rest.response.builder;

public interface BodyBuilder<T, R> extends StatusBuilder<T, R> {

  HeaderBuilder<T, R> body(T body);
}
