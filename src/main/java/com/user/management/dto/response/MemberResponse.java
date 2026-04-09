package com.user.management.dto.response;

import com.user.management.domain.enums.Role;

import java.util.UUID;

public record MemberResponse(
        UUID id,
        String name,
        Role role
) {
}
