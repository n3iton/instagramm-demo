package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.enums.ERole;
import com.example.demo.exception.UserExistsException;
import com.example.demo.payload.request.SignUpRequest;
import com.example.demo.repository.UserRepository;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  public static final Logger
      LOG = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;

  private final BCryptPasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = bCryptPasswordEncoder;
  }

  public User createUser(SignUpRequest userIn) {
    User user = new User();
    user.setEmail(userIn.getEmail());
    user.setName(userIn.getFirstname());
    user.setLastName(userIn.getLastname());
    user.setUsername(userIn.getUsername());
    user.setPassword(passwordEncoder.encode(userIn.getPassword()));
    user.getRoles().add(ERole.ROLE_USER);

    try {
      LOG.info("Saving User {}", userIn.getEmail());
      return userRepository.save(user);
    } catch (Exception e) {
      LOG.error("Error during registration. {}", e.getMessage());
      throw new UserExistsException("The user " + user.getUsername() + "already exists "
          + "Check credentials");
    }
  }

  public User updateUser(UserDTO userDTO, Principal principal) {
    User user = getUserByPrincipal(principal);
    user.setName(userDTO.getFirstname());
    user.setLastName(userDTO.getLastname());
    user.setBio(userDTO.getBio());

    return userRepository.save(user);
  }

  public User getCurrentUser(Principal principal) {
    return getUserByPrincipal(principal);
  }

  public User getUserById(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  private User getUserByPrincipal(Principal principal) {
    String username = principal.getName();
    return userRepository.findUserByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
  }


}
