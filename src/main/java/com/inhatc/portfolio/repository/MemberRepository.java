package com.inhatc.portfolio.repository;

import com.inhatc.portfolio.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
   Optional<Member> findByEmail(String email);
}
