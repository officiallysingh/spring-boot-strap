package com.ksoot.adapter.repository;

import com.ksoot.domain.model.Employee;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface EmployeeRepository
    extends JpaRepository<Employee, Long>, RevisionRepository<Employee, Long, Integer> {

  Optional<Employee> findByCode(final String code);

  boolean existsByCode(final String code);
}
