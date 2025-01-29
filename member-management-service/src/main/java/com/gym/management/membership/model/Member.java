package com.gym.management.membership.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "members")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String phoneNumber;

	@Column(nullable = false)
	private LocalDate dateOfBirth;

	private LocalDateTime createdAt = LocalDateTime.now();

	@OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
	@JsonIgnore
	private Membership membership;
}