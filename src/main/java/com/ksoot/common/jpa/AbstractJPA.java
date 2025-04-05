package com.ksoot.common.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.Metamodel;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.LongSupplier;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

public abstract class AbstractJPA implements JPA {

  @PersistenceContext protected EntityManager entityManager;

  protected CriteriaBuilder criteriaBuilder;

  protected Metamodel metamodel;

  <T, P> T getReference(final Class<T> clazz, final P primaryKey) {
    return this.entityManager.getReference(clazz, primaryKey);
  }

  @Override
  public final <T> CriteriaQuery<T> criteriaQuery(final Class<T> clazz) {
    return this.criteriaBuilder.createQuery(clazz);
  }

  @Override
  public final <T> TypedQuery<T> typedQuery(CriteriaQuery<T> criteriaQuery) {
    return this.entityManager.createQuery(criteriaQuery);
  }

  @Override
  public final <T> Optional<T> getSingleResultSafely(final CriteriaQuery<T> criteriaQuery) {
    return getSingleResultSafely(this.entityManager.createQuery(criteriaQuery));
  }

  @Override
  public final <T> Optional<T> getSingleResultSafely(final TypedQuery<T> typedQuery) {
    T result = null;
    try {
      result = typedQuery.getSingleResult();
    } catch (final NoResultException exception) {
      // Ignored on purpose
    }
    return Optional.ofNullable(result);
  }

  @SuppressWarnings("unchecked")
  public final <T> Optional<T> getSingleResultSafely(final Query query) {
    T result = null;
    try {
      result = (T) query.getSingleResult();
    } catch (final NoResultException exception) {
      // Ignored on purpose
    }
    return Optional.ofNullable(result);
  }

  @Override
  public final <T> List<T> getUnmodifiableResultList(final CriteriaQuery<T> query) {
    return getUnmodifiableResultList(this.entityManager.createQuery(query));
  }

  @Override
  public final <T> List<T> getUnmodifiableResultList(final TypedQuery<T> query) {
    List<T> results = query.getResultList();
    return results != null && !results.isEmpty()
        ? Collections.unmodifiableList(results)
        : Collections.emptyList();
  }

  @Override
  public final <T> T persist(final T entity) {
    this.entityManager.persist(entity);
    return entity;
  }

  @Override
  public final <T, S> Optional<T> findByIdSafely(final Class<T> clazz, final S primaryKey) {
    return Optional.ofNullable(this.entityManager.find(clazz, primaryKey));
  }

  @Override
  public <T> Long findCount(
      final CriteriaQuery<Long> query, final Root<T> root, final Predicate[] predicates) {
    if (ArrayUtils.isNotEmpty(predicates)) {
      query.where(predicates);
    }
    query.select(this.criteriaBuilder.countDistinct(root));
    return this.entityManager.createQuery(query).getSingleResult().longValue();
  }

  @Override
  public <T> Page<T> findPage(
      final CriteriaQuery<T> query,
      final Root<T> root,
      final Pageable pageRequest,
      final long totalRecords,
      final Predicate[] predicates) {
    if (totalRecords == 0) {
      return Page.empty();
    }
    query.select(root);

    if (ArrayUtils.isNotEmpty(predicates)) {
      query.where(predicates);
    }
    TypedQuery<T> typedQuery = typedQuery(query);
    typedQuery.setFirstResult(pageRequest.getPageNumber());
    typedQuery.setMaxResults(pageRequest.getPageSize());

    final List<T> results = getUnmodifiableResultList(typedQuery);
    return new PageImpl<>(results, pageRequest, totalRecords);
  }

  @Override
  public <T> Page<T> findPage(
      final CriteriaQuery<T> query,
      final Root<T> root,
      final Pageable pageRequest,
      final LongSupplier totalRecords,
      final Predicate[] predicates) {
    return this.findPage(query, root, pageRequest, totalRecords.getAsLong(), predicates);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Assert.state(Objects.nonNull(this.entityManager), "'entityManager' is required.");
    this.criteriaBuilder = this.entityManager.getCriteriaBuilder();
    this.metamodel = this.entityManager.getMetamodel();
  }
}
