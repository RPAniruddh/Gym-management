package com.gym.management.membership.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gym.management.membership.exception.MembershipNotFoundException;
import com.gym.management.membership.model.Member;
import com.gym.management.membership.model.Membership;
import com.gym.management.membership.repository.MembershipRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MembershipService {
	private final MemberService memberService;
	private final MembershipRepository membershipRepository;

	@Transactional
	public Membership createMembership(int memberId, Membership.MembershipType type) {
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
	public Membership renewMembership(int membershipId) {
		Member member = memberService.getMember(membershipId);
		Membership membership = member.getMembership();

		membership.setStartDate(LocalDate.now());
		membership.setEndDate(calculateEndDate(membership.getMembershipType()));
		membership.setStatus(Membership.MembershipStatus.ACTIVE);

		memberService.createMember(member);
		return membership;
	}

	@Transactional
	public Membership upgradeMembership(int membershipId, Membership.MembershipType newType) {
		Member member = memberService.getMember(membershipId);
		Membership membership = member.getMembership();

		membership.setMembershipType(newType);
		membership.setEndDate(calculateEndDate(newType));

		memberService.createMember(member);
		return membership;
	}

	@Transactional
	public void deactivateMembership(int membershipId) {
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
	
    public List<Membership> getAllMemberships() {
        return membershipRepository.findAll();
    }

	public Membership getMembership(int memberId) {
	    // Retrieve the membership from the repository
	    Optional<Membership> membershipOptional = membershipRepository.findById(memberId);
	    
	    // Check if the membership is present
	    if (membershipOptional.isPresent()) {
	        return membershipOptional.get();
	    } else {
	        // Throw an exception if the membership is not found
	        throw new MembershipNotFoundException("Membership not found with id: " + memberId);
	    }
	}
}
