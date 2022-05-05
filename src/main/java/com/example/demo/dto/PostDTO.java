package com.example.demo.dto;

import java.util.Set;
import lombok.Data;

@Data
public class PostDTO {

  private Long id;

  private String title;

  private String caption;

  private String location;

  private String username;

  private Integer likes;

  private Set<String> usersLiked;
}
