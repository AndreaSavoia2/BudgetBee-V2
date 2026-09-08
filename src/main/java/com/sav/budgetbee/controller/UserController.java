package com.sav.budgetbee.controller;

import com.sav.budgetbee.payload.request.LoginRequest;
import com.sav.budgetbee.payload.request.RegistrationRequest;
import com.sav.budgetbee.payload.response.AuthenticationResponse;
import com.sav.budgetbee.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Validated
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("v0/users/registration")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        String result = userService.registerUser(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("v0/users/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        AuthenticationResponse result = userService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("v1/users/delete")
    public ResponseEntity<?> deletedUser(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(userService.deleteUser(userDetails));
    }

    @PatchMapping("/v0/users/reset")
    public ResponseEntity<?> resetPassword(@RequestParam @NotBlank @Email String email){
        return new ResponseEntity<>(userService.resetPassword(email), HttpStatus.OK);
    }

    @PatchMapping("/v1/users/change-password")
    public ResponseEntity<?> changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam @NotBlank String newPassword
    ){
        return ResponseEntity.ok(userService.changePassword(userDetails, newPassword));
    }
}
