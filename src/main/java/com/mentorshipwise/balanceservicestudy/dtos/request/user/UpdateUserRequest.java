package com.mentorshipwise.balanceservicestudy.dtos.request.user;

import com.mentorshipwise.balanceservicestudy.utils.RegexUtil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @Email(message = "Email must be valid")
    @Pattern(
            regexp = RegexUtil.EMAIL,
            message = "Email contains invalid characters"
    )
    private String email;

    private String name;
}
