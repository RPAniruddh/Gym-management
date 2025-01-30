package com.gym.management.membership.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gym.management.membership.exception.MemberAlreadyExistsException;
import com.gym.management.membership.exception.MemberNotFoundException;
import com.gym.management.membership.model.Member;
import com.gym.management.membership.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	public Member createMember(Member member) {
		return memberRepository.save(member);
	}

	public Member getMember(int id) {
		return memberRepository.findById(id)
				.orElseThrow(() -> new MemberNotFoundException("Member not found with ID " + id));
	}

	public List<Member> getAllMembers() {
		return memberRepository.findAll();
	}

	@Transactional
	public Member updateMember(int id, Member memberDetails) {
		Member member = getMember(id);
		member.setFirstName(memberDetails.getFirstName());
		member.setLastName(memberDetails.getLastName());
		member.setEmail(memberDetails.getEmail());
		member.setPhoneNumber(memberDetails.getPhoneNumber());
		member.setDateOfBirth(memberDetails.getDateOfBirth());
		return memberRepository.save(member);
	}

	public void deleteMember(int id) {
		if (!memberRepository.existsById(id)) {
			throw new MemberNotFoundException("Member not found with ID " + id);
		}
		memberRepository.deleteById(id);
	}
}