package com.gym.management.membership.controller;

import java.util.List;

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
            @PathVariable int memberId,
            @RequestParam Membership.MembershipType type) {
        return ResponseEntity.ok(membershipService.createMembership(memberId, type));
    }
 
    @PutMapping("/{membershipId}/renew")
    public ResponseEntity<Membership> renewMembership(@PathVariable int membershipId) {
        return ResponseEntity.ok(membershipService.renewMembership(membershipId));
    }
 
    @PutMapping("/{membershipId}/upgrade")
    public ResponseEntity<Membership> upgradeMembership(
            @PathVariable int membershipId,
            @RequestParam Membership.MembershipType newType) {
        return ResponseEntity.ok(membershipService.upgradeMembership(membershipId, newType));
    }
 
    @PostMapping("/{membershipId}/deactivate")
    public ResponseEntity<Void> deactivateMembership(@PathVariable int membershipId) {
        membershipService.deactivateMembership(membershipId);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{membershipId}")
    public ResponseEntity<Membership> getMembershipById(@PathVariable int membershipId) {
        return ResponseEntity.ok(membershipService.getMembership(membershipId));
    }
    
    @GetMapping
    public ResponseEntity<List<Membership>> getAllMemberships() {
        return ResponseEntity.ok(membershipService.getAllMemberships());
    }
}