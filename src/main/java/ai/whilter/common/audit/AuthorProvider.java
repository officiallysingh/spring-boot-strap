package ai.whilter.common.audit;

public interface AuthorProvider {

  /**
   * Should provide a commit author, typically a user name taken from a current user session or
   * Spring Security's <code>SecurityContextHolder</code>. <br>
   * <br>
   * Implementation has to be thread-safe. <br>
   * <br>
   * See {@link SpringSecurityAuthorProvider} - implementation for Spring Security<br>
   * See {@link SystemAuthorProvider} See {@link MockAuthorProvider}
   */
  String getAuthor();
}
