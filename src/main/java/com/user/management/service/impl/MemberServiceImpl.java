package com.user.management.service.impl;

import com.user.management.domain.Member;
import com.user.management.domain.enums.EmploymentStatus;
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
        createInitialMember("João Silva", EmploymentStatus.FORMER_EMPLOYEE);
        createInitialMember("Maria Santos", EmploymentStatus.EMPLOYEE);
        createInitialMember("Ana Costa", EmploymentStatus.EMPLOYEE);
        createInitialMember("Carlos Pereira", EmploymentStatus.FORMER_EMPLOYEE);
        createInitialMember("Lucas Almeida", EmploymentStatus.EMPLOYEE);
        createInitialMember("Fernanda Rodrigues", EmploymentStatus.EMPLOYEE);
        createInitialMember("Ricardo Souza", EmploymentStatus.EMPLOYEE);
        createInitialMember("Juliana Lima", EmploymentStatus.EMPLOYEE);
        createInitialMember("Roberto Martins", EmploymentStatus.EMPLOYEE);
        createInitialMember("Camila Ferreira", EmploymentStatus.EMPLOYEE);
        createInitialMember("Bruno Araújo", EmploymentStatus.EMPLOYEE);
        createInitialMember("Patricia Gomes", EmploymentStatus.EMPLOYEE);
        createInitialMember("Marcos Barbosa", EmploymentStatus.EMPLOYEE);
        createInitialMember("Larissa Monteiro", EmploymentStatus.EMPLOYEE);
        createInitialMember("Thiago Cardoso", EmploymentStatus.EMPLOYEE);
        createInitialMember("Renata Dias", EmploymentStatus.EMPLOYEE);
        createInitialMember("Gabriel Ribeiro", EmploymentStatus.EMPLOYEE);
        createInitialMember("Bianca Castro", EmploymentStatus.EMPLOYEE);
        createInitialMember("André Mendes", EmploymentStatus.EMPLOYEE);
        createInitialMember("Mano Menezes", EmploymentStatus.EMPLOYEE);
        createInitialMember("Vinicius Junior", EmploymentStatus.EMPLOYEE);
        createInitialMember("Matheus Mendes", EmploymentStatus.EMPLOYEE);
        createInitialMember("Luan Mendonça", EmploymentStatus.EMPLOYEE);

    }

    private void createInitialMember(String name, EmploymentStatus employmentStatus) {
        Member member = Member.builder()
                .id(UUID.randomUUID())
                .name(name)
                .employmentStatus(employmentStatus)
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
