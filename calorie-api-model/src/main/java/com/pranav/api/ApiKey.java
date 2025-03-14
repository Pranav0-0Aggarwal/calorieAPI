package com.pranav.api;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiKey {
    private String key;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private Long requestCount;

}
