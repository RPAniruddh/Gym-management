package com.gym.management.membership.controller;

import com.gym.management.membership.model.Member;
import com.gym.management.membership.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
 
    @PostMapping("/add")
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        return ResponseEntity.ok(memberService.createMember(member));
    }
 
    @GetMapping("/get/{id}")
    public ResponseEntity<Member> getMember(@PathVariable int id) {
        return ResponseEntity.ok(memberService.getMember(id));
    }
 
    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }
 
    @PutMapping("/update/{id}")
    public ResponseEntity<Member> updateMember(
            @PathVariable int id,
            @RequestBody Member member) {
        return ResponseEntity.ok(memberService.updateMember(id, member));
    }
 
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable int id) {
        memberService.deleteMember(id);
        return ResponseEntity.ok().build();
    }
}