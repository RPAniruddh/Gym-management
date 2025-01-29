package com.gym.management.membership.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "memberships")
public class Membership {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@Enumerated(EnumType.STRING)
	private MembershipType membershipType;

	@Enumerated(EnumType.STRING)
	private MembershipStatus status;

	private LocalDate startDate;
	private LocalDate endDate;
	private LocalDateTime createdAt = LocalDateTime.now();

	public enum MembershipType {
		BASIC, PREMIUM
	}

	public enum MembershipStatus {
		ACTIVE, INACTIVE
	}
}