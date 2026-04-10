package com.user.management.service.impl;

import com.user.management.domain.Member;
import com.user.management.domain.enums.EmploymentStatus;
import com.user.management.domain.enums.Role;
import com.user.management.dto.request.MemberRequest;
import com.user.management.dto.response.MemberResponse;
import com.user.management.exception.MemberNotFoundException;
import com.user.management.service.MemberService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MemberServiceImpl implements MemberService {

    private final Map<UUID, Member> memberStorage = new ConcurrentHashMap<>();
    private int memberCounter = 1000;

    @PostConstruct
    public void initializeData() {
        createInitialMember(UUID.fromString("00000000-0000-0000-0000-000000000001"), "João Silva", EmploymentStatus.FORMER_EMPLOYEE, Role.DEV);
        createInitialMember(UUID.fromString("00000000-0000-0000-0000-000000000002"), "Maria Santos", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember(UUID.fromString("00000000-0000-0000-0000-000000000003"), "Ana Costa", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember(UUID.fromString("00000000-0000-0000-0000-000000000004"), "Carlos Pereira", EmploymentStatus.FORMER_EMPLOYEE, Role.DEV);
        createInitialMember(UUID.fromString("00000000-0000-0000-0000-000000000005"), "Lucas Almeida", EmploymentStatus.EMPLOYEE, Role.MANAGER);
        createInitialMember(UUID.fromString("00000000-0000-0000-0000-000000000006"), "Fernanda Rodrigues", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember(UUID.fromString("00000000-0000-0000-0000-000000000007"), "Ricardo Souza", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember(UUID.fromString("00000000-0000-0000-0000-000000000008"), "Juliana Lima", EmploymentStatus.EMPLOYEE, Role.MANAGER);
        createInitialMember(UUID.fromString("00000000-0000-0000-0000-000000000009"), "Roberto Martins", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember(UUID.fromString("00000000-0000-0000-0000-000000000010"), "Camila Ferreira", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember(UUID.fromString("00000000-0000-0000-0000-000000000011"), "Bruno Araújo", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember(UUID.fromString("00000000-0000-0000-0000-000000000012"), "Patricia Gomes", EmploymentStatus.EMPLOYEE, Role.QA);
        createInitialMember(UUID.fromString("00000000-0000-0000-0000-000000000013"), "Marcos Barbosa", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember(UUID.fromString("00000000-0000-0000-0000-000000000014"), "Larissa Monteiro", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember(UUID.fromString("00000000-0000-0000-0000-000000000015"), "Thiago Cardoso", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember(UUID.fromString("00000000-0000-0000-0000-000000000016"), "Renata Dias", EmploymentStatus.EMPLOYEE, Role.TRAINEE);
        createInitialMember(UUID.fromString("00000000-0000-0000-0000-000000000017"), "Gabriel Ribeiro", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember(UUID.fromString("00000000-0000-0000-0000-000000000018"), "Bianca Castro", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember(UUID.fromString("00000000-0000-0000-0000-000000000019"), "André Mendes", EmploymentStatus.EMPLOYEE, Role.QA);
        createInitialMember(UUID.fromString("00000000-0000-0000-0000-000000000020"), "Mano Menezes", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember(UUID.fromString("00000000-0000-0000-0000-000000000021"), "Vinicius Junior", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember(UUID.fromString("00000000-0000-0000-0000-000000000022"), "Matheus Mendes", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember(UUID.fromString("00000000-0000-0000-0000-000000000023"), "Luan Mendonça", EmploymentStatus.EMPLOYEE, Role.MANAGER);
    }

    private void createInitialMember(UUID id, String name, EmploymentStatus employmentStatus, Role role) {
        Member member = Member.builder()
                .id(id)
                .name(name)
                .employmentStatus(employmentStatus)
                .role(role)
                .build();
        memberStorage.put(member.getId(), member);
    }

    @Override
    public MemberResponse createMember(MemberRequest request) {
        UUID newId = UUID.fromString(String.format("00000000-0000-0000-0000-%012d", ++memberCounter));
        Member member = Member.builder()
                .id(newId)
                .name(request.name())
                .employmentStatus(request.employmentStatus())
                .build();

        memberStorage.put(member.getId(), member);
        return mapToResponse(member);
    }

    @Override
    public MemberResponse getMemberById(UUID id) {
        Member member = memberStorage.get(id);
        if (member == null) {
            throw new MemberNotFoundException("Member not found with id: " + id);
        }
        return mapToResponse(member);
    }

    @Override
    public List<MemberResponse> getAllMembers() {
        return memberStorage.values().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<MemberResponse> getMembersByIds(List<UUID> ids) {
        return ids.stream()
                .map(memberStorage::get)
                .filter(Objects::nonNull)
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public MemberResponse updateMember(UUID id, MemberRequest request) {
        Member existingMember = memberStorage.get(id);
        if (existingMember == null) {
            throw new MemberNotFoundException("Member not found with id: " + id);
        }

        Member updatedMember = Member.builder()
                .id(id)
                .name(request.name())
                .employmentStatus(request.employmentStatus())
                .build();

        memberStorage.put(id, updatedMember);
        return mapToResponse(updatedMember);
    }

    @Override
    public void deleteMember(UUID id) {
        if (!memberStorage.containsKey(id)) {
            throw new MemberNotFoundException("Member not found with id: " + id);
        }
        memberStorage.remove(id);
    }

    private MemberResponse mapToResponse(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getEmploymentStatus(),
                member.getRole()
        );
    }
}
