package com.ksoot.common.jpa;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.function.LongSupplier;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JPA extends InitializingBean {

  <T> Optional<T> getSingleResultSafely(final CriteriaQuery<T> criteriaQuery);

  <T> Optional<T> getSingleResultSafely(final TypedQuery<T> typedQuery);

  <T> Optional<T> getSingleResultSafely(final Query query);

  <T> List<T> getUnmodifiableResultList(final CriteriaQuery<T> query);

  <T> List<T> getUnmodifiableResultList(final TypedQuery<T> query);

  <T> T persist(final T entity);

  <T, P> Optional<T> findByIdSafely(final Class<T> clazz, final P primaryKey);

  <T> CriteriaQuery<T> criteriaQuery(final Class<T> clazz);

  <T> TypedQuery<T> typedQuery(CriteriaQuery<T> criteriaQuery);

  <T> Long findCount(
      final CriteriaQuery<Long> query, final Root<T> root, final Predicate[] predicates);

  <T> Page<T> findPage(
      final CriteriaQuery<T> query,
      final Root<T> root,
      final Pageable pageRequest,
      final long totalRecords,
      final Predicate[] predicates);

  <T> Page<T> findPage(
      final CriteriaQuery<T> query,
      final Root<T> root,
      final Pageable pageRequest,
      final LongSupplier totalRecords,
      final Predicate[] predicates);

  static Predicate[] toPredicatesArray(final List<Predicate> predicates) {
    return CollectionUtils.isNotEmpty(predicates)
        ? predicates.toArray(new Predicate[predicates.size()])
        : null;
  }
}
