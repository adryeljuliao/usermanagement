package com.user.management.service.impl;

import com.user.management.domain.Member;
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
        createInitialMember("João Silva", Role.MANAGER);
        createInitialMember("Maria Santos", Role.FUNCTIONARY);
        createInitialMember("Pedro Oliveira", Role.TRAINEE);
        createInitialMember("Ana Costa", Role.FUNCTIONARY);
        createInitialMember("Carlos Pereira", Role.MANAGER);
        createInitialMember("Lucas Almeida", Role.FUNCTIONARY);
        createInitialMember("Fernanda Rodrigues", Role.FUNCTIONARY);
        createInitialMember("Ricardo Souza", Role.FUNCTIONARY);
        createInitialMember("Juliana Lima", Role.FUNCTIONARY);
        createInitialMember("Roberto Martins", Role.FUNCTIONARY);
        createInitialMember("Camila Ferreira", Role.FUNCTIONARY);
        createInitialMember("Bruno Araújo", Role.FUNCTIONARY);
        createInitialMember("Patricia Gomes", Role.FUNCTIONARY);
        createInitialMember("Marcos Barbosa", Role.FUNCTIONARY);
        createInitialMember("Larissa Monteiro", Role.FUNCTIONARY);
        createInitialMember("Thiago Cardoso", Role.FUNCTIONARY);
        createInitialMember("Renata Dias", Role.FUNCTIONARY);
        createInitialMember("Gabriel Ribeiro", Role.FUNCTIONARY);
        createInitialMember("Bianca Castro", Role.FUNCTIONARY);
        createInitialMember("André Mendes", Role.FUNCTIONARY);
        createInitialMember("Mano Menezes", Role.FUNCTIONARY);
        createInitialMember("Vinicius Junior", Role.FUNCTIONARY);
        createInitialMember("Matheus Mendes", Role.FUNCTIONARY);
        createInitialMember("Luan Mendonça", Role.FUNCTIONARY);

    }

    private void createInitialMember(String name, Role role) {
        Member member = Member.builder()
                .id(UUID.randomUUID())
                .name(name)
                .role(role)
                .build();
        memberStorage.put(member.getId(), member);
    }

    @Override
    public MemberResponse createMember(MemberRequest request) {
        Member member = Member.builder()
                .id(UUID.randomUUID())
                .name(request.name())
                .role(request.role())
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
                .role(request.role())
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
                member.getRole()
        );
    }
}
