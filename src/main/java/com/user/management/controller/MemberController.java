package com.user.management.controller;

import com.user.management.dto.request.MemberRequest;
import com.user.management.dto.response.MemberResponse;
import com.user.management.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Tag(name = "Member Management", description = "APIs para gerenciamento de membros")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @Operation(summary = "Criar novo membro", description = "Cria um novo membro no sistema")
    public ResponseEntity<MemberResponse> createMember(@Valid @RequestBody MemberRequest request) {
        MemberResponse response = memberService.createMember(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar membro por ID", description = "Retorna um membro específico pelo ID")
    public ResponseEntity<MemberResponse> getMemberById(@PathVariable UUID id) {
        MemberResponse response = memberService.getMemberById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar todos os membros", description = "Retorna lista de todos os membros ou filtra por IDs específicos (máximo 10 IDs)")
    public ResponseEntity<List<MemberResponse>> getAllMembers(
            @RequestParam(value = "ids", required = false)
            @Size(max = 10, message = "Máximo de 10 IDs permitidos por requisição")
            List<UUID> ids) {

        List<MemberResponse> members = (ids != null && !ids.isEmpty())
                ? memberService.getMembersByIds(ids)
                : memberService.getAllMembers();

        return ResponseEntity.ok(members);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar membro", description = "Atualiza dados de um membro existente")
    public ResponseEntity<MemberResponse> updateMember(
            @PathVariable UUID id,
            @Valid @RequestBody MemberRequest request) {
        MemberResponse response = memberService.updateMember(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar membro", description = "Remove um membro do sistema")
    public ResponseEntity<Void> deleteMember(@PathVariable UUID id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}
