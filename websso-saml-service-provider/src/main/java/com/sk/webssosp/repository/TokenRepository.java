package com.sk.webssosp.repository;

import com.sk.webssosp.entity.Token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
	Token findByIdToken(String token);
}