package com.mentorshipwise.balanceservicestudy.dtos;

import com.mentorshipwise.balanceservicestudy.utils.RegexUtil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

// User input form validation
@Data
public class UserRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Pattern(
            regexp = RegexUtil.EMAIL,
            message = "Email contains invalid characters"
    )
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = RegexUtil.STRONG_PASSWORD,
            message = "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one number and one special character"
    )
    private String password;
}
