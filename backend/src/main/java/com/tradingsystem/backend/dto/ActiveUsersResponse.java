package com.tradingsystem.backend.dto;

import java.util.List;

public record ActiveUsersResponse(
    List<ActiveUser> activeUsers
) {
    public static ActiveUsersResponse createActiveUsersResponse(List<ActiveUser> activeUsers){
        return new ActiveUsersResponse(activeUsers);
    }
}
