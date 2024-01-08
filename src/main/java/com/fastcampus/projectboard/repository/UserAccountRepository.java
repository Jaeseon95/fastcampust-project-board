package com.fastcampus.projectboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastcampus.projectboard.domain.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
}