package com.user.management.dto.request;

import com.user.management.domain.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MemberRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Role is required")
        Role role
) {
}
