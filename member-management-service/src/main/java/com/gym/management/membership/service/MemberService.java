package com.gym.management.membership.service;

import java.util.List;

import com.gym.management.membership.model.Member;

public interface MemberService {
    Member createMember(Member member);
    Member getMember(int id);
    List<Member> getAllMembers();
    Member updateMember(int id, Member memberDetails);
    void deleteMember(int id);
}
