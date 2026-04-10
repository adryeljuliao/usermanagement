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

    @PostConstruct
    public void initializeData() {
        createInitialMember("João Silva", EmploymentStatus.FORMER_EMPLOYEE, Role.DEV);
        createInitialMember("Maria Santos", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember("Ana Costa", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember("Carlos Pereira", EmploymentStatus.FORMER_EMPLOYEE, Role.DEV);
        createInitialMember("Lucas Almeida", EmploymentStatus.EMPLOYEE, Role.MANAGER);
        createInitialMember("Fernanda Rodrigues", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember("Ricardo Souza", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember("Juliana Lima", EmploymentStatus.EMPLOYEE, Role.MANAGER);
        createInitialMember("Roberto Martins", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember("Camila Ferreira", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember("Bruno Araújo", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember("Patricia Gomes", EmploymentStatus.EMPLOYEE, Role.QA);
        createInitialMember("Marcos Barbosa", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember("Larissa Monteiro", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember("Thiago Cardoso", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember("Renata Dias", EmploymentStatus.EMPLOYEE, Role.TRAINEE);
        createInitialMember("Gabriel Ribeiro", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember("Bianca Castro", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember("André Mendes", EmploymentStatus.EMPLOYEE, Role.QA);
        createInitialMember("Mano Menezes", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember("Vinicius Junior", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember("Matheus Mendes", EmploymentStatus.EMPLOYEE, Role.DEV);
        createInitialMember("Luan Mendonça", EmploymentStatus.EMPLOYEE, Role.MANAGER);

    }

    private void createInitialMember(String name, EmploymentStatus employmentStatus, Role role) {
        Member member = Member.builder()
                .id(UUID.randomUUID())
                .name(name)
                .employmentStatus(employmentStatus)
                .role(role)
                .build();
        memberStorage.put(member.getId(), member);
    }

    @Override
    public MemberResponse createMember(MemberRequest request) {
        Member member = Member.builder()
                .id(UUID.randomUUID())
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
                member.getEmploymentStatus()
        );
    }
}
