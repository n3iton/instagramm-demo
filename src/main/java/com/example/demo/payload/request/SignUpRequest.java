package com.example.demo.payload.request;

import com.example.demo.annotation.PasswordMatches;
import com.example.demo.annotation.ValidEmail;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@PasswordMatches
public class SignUpRequest {

  @Email(message = "Should have email format")
  @NotBlank(message = "User email is required")
  @ValidEmail
  private String email;

  @NotEmpty(message = "Please entry your name")
  private String firstname;

  @NotEmpty(message = "Please entry your lastname")
  private String lastname;

  @NotEmpty(message = "Please entry your nickname")
  private String username;

  @NotEmpty(message = "Password is required")
  @Size(min = 6)
  private String password;

  private String confirmPassword;
}
