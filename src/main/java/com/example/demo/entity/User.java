package com.example.demo.entity;

import com.example.demo.entity.enums.ERole;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@Table(name = "\"user\"")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(unique = true, updatable = false)
  private String username;

  @Column(nullable = false)
  private String lastName;

  @Column(unique = true)
  private String email;

  @Column(columnDefinition = "text")
  private String bio;

  @Column(length = 3000)
  private String password;

  @ElementCollection(targetClass = ERole.class)
  @CollectionTable(name = "user_role",
      joinColumns = @JoinColumn(name = "user_id"))
  private Set<ERole> roles = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
      mappedBy = "user", orphanRemoval = true)
  private List<Post> posts = new ArrayList<>();

  @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
  @Column(updatable = false)
  private LocalDateTime createDate;


  @Transient
  private Collection<? extends GrantedAuthority> authorities;

  public User() {

  }

  @PrePersist
  protected void onCreate() {
    this.createDate = LocalDateTime.now();
  }
}
