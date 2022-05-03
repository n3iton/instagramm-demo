package com.example.demo.web;

import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.SignUpRequest;
import com.example.demo.payload.response.JwtTokenSuccessResponse;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.security.SecurityConstants;
import com.example.demo.service.UserService;
import com.example.demo.validation.ResponseErrorValidation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api/auth")
@PreAuthorize("permitAll()")
public class AuthController {

  @Autowired
  private ResponseErrorValidation responseErrorValidation;

  @Autowired
  private UserService userService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @PostMapping("/signin")
  public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
      BindingResult bindingResult) {
    ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
    if (!ObjectUtils.isEmpty(errors)) return errors;

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getUsername(), loginRequest.getPassword()
        ));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

    return ResponseEntity.ok(new JwtTokenSuccessResponse(true, jwt));
  }

  @PostMapping("/signup")
  public ResponseEntity<Object> registerUser(@Valid @RequestBody SignUpRequest signUpRequest,
      BindingResult bindingResult) {

    ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
    if (!ObjectUtils.isEmpty(errors)) return errors;

    userService.createUser(signUpRequest);
    return ResponseEntity.ok(new MessageResponse("User registered successfully"));
  }
}
