package com.pranav.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PublicKey;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiKey {
    private String key;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private Long requestCount;

    public ApiKey(String apiKey, String userId) {
        this.key = apiKey;
        this.userId = userId;
    }

}
