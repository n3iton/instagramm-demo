package com.example.demo.validation;

import com.example.demo.annotation.PasswordMatches;
import com.example.demo.payload.request.SignUpRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

  @Override
  public void initialize(PasswordMatches constraintAnnotation) {

  }

  @Override
  public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
    SignUpRequest userSignUpRequest = (SignUpRequest) obj;
    return  userSignUpRequest.getPassword().equals(userSignUpRequest.getConfirmPassword());
  }
}
