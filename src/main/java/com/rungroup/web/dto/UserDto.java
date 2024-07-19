package com.rungroup.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    private String givenName;
    @NotEmpty
    private String familyName;
}
