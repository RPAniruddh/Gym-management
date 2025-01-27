package com.gym.management.membership.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.management.membership.model.Member;

public interface MemberRepository extends JpaRepository<Member, UUID> {
 Optional<Member> findByEmail(String email);
}
