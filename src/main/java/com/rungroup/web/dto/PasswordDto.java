package com.rungroup.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordDto {
    @NotEmpty(message = "Current password cannot be empty.")
    private String currentPassword;
    //    @NotEmpty(message = "New password cannot be empty")
    @Size(min = 5, message = "New password must be at least 5 characters in length.")
    private String newPassword;
    //    @NotEmpty(message = "New password (again) cannot be empty")
    @Size(min = 5, message = "New password (again) must be at least 5 characters in length.")
    private String newPasswordAgain;

    @Override
    public String toString() {
        return "PasswordDto{" +
                "currentPassword='" + currentPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", newPasswordAgain='" + newPasswordAgain + '\'' +
                '}';
    }
}
