package com.user.management.service;

import com.user.management.dto.request.MemberRequest;
import com.user.management.dto.response.MemberResponse;

import java.util.List;
import java.util.UUID;

public interface MemberService {

    MemberResponse createMember(MemberRequest request);

    MemberResponse getMemberById(UUID id);

    List<MemberResponse> getAllMembers();

    List<MemberResponse> getMembersByIds(List<UUID> ids);

    MemberResponse updateMember(UUID id, MemberRequest request);

    void deleteMember(UUID id);

}
