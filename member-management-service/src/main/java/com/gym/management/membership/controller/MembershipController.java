package com.gym.management.membership.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gym.management.membership.model.Membership;
import com.gym.management.membership.service.MembershipService;

import lombok.RequiredArgsConstructor;
 
@RestController
@RequestMapping("/api/memberships")
@RequiredArgsConstructor
public class MembershipController {
    private final MembershipService membershipService;
 
    @PostMapping("/members/{memberId}")
    public ResponseEntity<Membership> createMembership(
            @PathVariable UUID memberId,
            @RequestParam Membership.MembershipType type) {
        return ResponseEntity.ok(membershipService.createMembership(memberId, type));
    }
 
    @PutMapping("/{membershipId}/renew")
    public ResponseEntity<Membership> renewMembership(@PathVariable UUID membershipId) {
        return ResponseEntity.ok(membershipService.renewMembership(membershipId));
    }
 
    @PutMapping("/{membershipId}/upgrade")
    public ResponseEntity<Membership> upgradeMembership(
            @PathVariable UUID membershipId,
            @RequestParam Membership.MembershipType newType) {
        return ResponseEntity.ok(membershipService.upgradeMembership(membershipId, newType));
    }
 
    @PostMapping("/{membershipId}/deactivate")
    public ResponseEntity<Void> deactivateMembership(@PathVariable UUID membershipId) {
        membershipService.deactivateMembership(membershipId);
        return ResponseEntity.ok().build();
    }
    
    // New method to get all memberships
    @GetMapping
    public ResponseEntity<List<Membership>> getAllMemberships() {
        return ResponseEntity.ok(membershipService.getAllMemberships());
    }
}