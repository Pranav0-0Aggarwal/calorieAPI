package com.pranav.user;


import com.pranav.api.ApiKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private String userId;

    private String name;
    private String email;
    private ApiKey apiKey;
    private LocalDateTime createdAt;
}
