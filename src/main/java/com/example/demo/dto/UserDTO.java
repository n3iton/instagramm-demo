package com.example.demo.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDTO {

  private Long id;

  @NotEmpty
  private String firstname;

  @NotEmpty
  private String lastname;

  private String username;

  private String bio;

}
