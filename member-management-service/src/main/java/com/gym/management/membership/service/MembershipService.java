package com.gym.management.membership.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gym.management.membership.exception.MembershipAlreadyExistsException;
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

        if (member.getMembership() != null) {
            throw new MembershipAlreadyExistsException("Member already has an active membership");
        }

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

        if (membership == null) {
            throw new MembershipNotFoundException("Membership not found for member with ID " + membershipId);
        }

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

        if (membership == null) {
            throw new MembershipNotFoundException("Membership not found for member with ID " + membershipId);
        }

        membership.setMembershipType(newType);
        membership.setEndDate(calculateEndDate(newType));

        memberService.createMember(member);
        return membership;
    }

    @Transactional
    public void deactivateMembership(int membershipId) {
        Member member = memberService.getMember(membershipId);
        Membership membership = member.getMembership();

        if (membership == null) {
            throw new MembershipNotFoundException("Membership not found for member with ID " + membershipId);
        }

        membership.setStatus(Membership.MembershipStatus.INACTIVE);
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
        Optional<Membership> membershipOptional = membershipRepository.findById(memberId);

        if (membershipOptional.isPresent()) {
            return membershipOptional.get();
        } else {
            throw new MembershipNotFoundException("Membership not found with id: " + memberId);
        }
    }
}