package com.pranav.user;


import com.pranav.api.ApiKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class User {

    @NotNull
    private String userId;

    private String name;
    private String email;

    @NotEmpty
    private ApiKey apiKey;

    @NotNull
    private LocalDateTime createdAt;
}
