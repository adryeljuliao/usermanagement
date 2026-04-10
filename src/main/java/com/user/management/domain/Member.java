package com.user.management.domain;

import com.user.management.domain.enums.EmploymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Member {

    private UUID id;
    private String name;
    private EmploymentStatus employmentStatus;

}
