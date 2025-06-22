// package ai.whilter.common.audit;
//
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
//
/// **
// * Returns a current user name from Spring Security context
// */
// public class SpringSecurityAuthorProvider implements AuthorProvider {
//
//  @Override
//  public String getAuthor() {
//    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//    if (auth == null) {
//      return "unknown";
//    }
//    return auth.getName();
//  }
// }
