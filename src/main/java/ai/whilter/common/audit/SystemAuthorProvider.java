package ai.whilter.common.audit;

import lombok.RequiredArgsConstructor;

/**
 * Returns your system user name taken from System property <code>user.name</code> from your machine
 */
@RequiredArgsConstructor
public class SystemAuthorProvider implements AuthorProvider {

  //    @Value("#{systemProperties['user.name']}")
  private final String author;

  @Override
  public String getAuthor() {
    return this.author;
  }
}
