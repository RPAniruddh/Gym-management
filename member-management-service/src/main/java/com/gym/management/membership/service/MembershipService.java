package com.gym.management.membership.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gym.management.membership.model.Member;
import com.gym.management.membership.model.Membership;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MembershipService {
	private final MemberService memberService;

	@Transactional
	public Membership createMembership(UUID memberId, Membership.MembershipType type) {
		Member member = memberService.getMember(memberId);

		Membership membership = new Membership();
		membership.setMember(member);
		membership.setMembershipType(type);
		membership.setStatus(Membership.MembershipStatus.ACTIVE);
		membership.setStartDate(LocalDate.now());
		membership.setEndDate(calculateEndDate(type));

		member.setMembership(membership);
		memberService.createMember(member);

		return membership;
	}

	@Transactional
	public Membership renewMembership(UUID membershipId) {
		Member member = memberService.getMember(membershipId);
		Membership membership = member.getMembership();

		membership.setStartDate(LocalDate.now());
		membership.setEndDate(calculateEndDate(membership.getMembershipType()));
		membership.setStatus(Membership.MembershipStatus.ACTIVE);

		memberService.createMember(member);
		return membership;
	}

	@Transactional
	public Membership upgradeMembership(UUID membershipId, Membership.MembershipType newType) {
		Member member = memberService.getMember(membershipId);
		Membership membership = member.getMembership();

		membership.setMembershipType(newType);
		membership.setEndDate(calculateEndDate(newType));

		memberService.createMember(member);
		return membership;
	}

	@Transactional
	public void deactivateMembership(UUID membershipId) {
		Member member = memberService.getMember(membershipId);
		member.getMembership().setStatus(Membership.MembershipStatus.INACTIVE);
		memberService.createMember(member);
	}

	private LocalDate calculateEndDate(Membership.MembershipType type) {
		return switch (type) {
		case BASIC -> LocalDate.now().plusMonths(1);
		case PREMIUM -> LocalDate.now().plusMonths(3);
		};
	}
}
