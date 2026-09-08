package com.sav.budgetbee.repository;

import com.sav.budgetbee.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByAuthorityDefaultTrue();
}
