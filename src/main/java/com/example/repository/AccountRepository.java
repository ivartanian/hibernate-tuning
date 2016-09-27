package com.example.repository;

import com.example.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by viv on 27.09.2016.
 */
public interface AccountRepository extends JpaRepository<Account, Long>{
}
