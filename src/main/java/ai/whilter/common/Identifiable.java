package ai.whilter.common;

import java.io.Serializable;

/**
 * @param <I> Id type
 * @author Rajveer Singh
 */
public interface Identifiable<I extends Comparable<I> & Serializable> {

  I getId();
}
