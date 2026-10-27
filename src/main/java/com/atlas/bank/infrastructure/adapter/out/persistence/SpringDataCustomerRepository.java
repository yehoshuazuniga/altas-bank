package com.atlas.bank.infrastructure.adapter.out.persistence;

import com.atlas.bank.domain.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataCustomerRepository extends JpaRepository<CustomerJpaEntity, Long> {
}
