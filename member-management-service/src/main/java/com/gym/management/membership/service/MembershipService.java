package com.gym.management.membership.service;

import java.time.LocalDate;
import java.util.List;

import com.gym.management.membership.model.Membership;

public interface MembershipService {
    Membership createMembership(int memberId, Membership.MembershipType type);
    Membership renewMembership(int membershipId);
    Membership upgradeMembership(int membershipId, Membership.MembershipType newType);
    void deactivateMembership(int membershipId);
    List<Membership> getAllMemberships();
    Membership getMembership(int memberId);
    LocalDate calculateEndDate(Membership.MembershipType type);
}
