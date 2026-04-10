package com.user.management.dto.response;

import com.user.management.domain.enums.EmploymentStatus;

import java.util.UUID;

public record MemberResponse(
        UUID id,
        String name,
        EmploymentStatus employmentStatus
) {
}
