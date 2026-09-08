package com.sav.budgetbee.service;

import com.sav.budgetbee.entity.User;
import com.sav.budgetbee.exception.GenericException;
import com.sav.budgetbee.mail.Mail;
import com.sav.budgetbee.mail.MailService;
import com.sav.budgetbee.payload.request.LoginRequest;
import com.sav.budgetbee.payload.request.RegistrationRequest;
import com.sav.budgetbee.payload.response.AuthenticationResponse;
import com.sav.budgetbee.repository.AuthorityRepository;
import com.sav.budgetbee.repository.UserRepository;
import com.sav.budgetbee.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final JwtService jwtService;
    private final MailService mailService;

    public String registerUser(RegistrationRequest registrationRequest){
        String username = registrationRequest.getUsername().trim();
        String email = registrationRequest.getEmail().trim().toLowerCase();
        if(userRepository.existsByUsername(username))
            throw new GenericException("Username is already in use", HttpStatus.CONFLICT);

        if(userRepository.existsByEmail(email))
            throw new GenericException("Email is already in use", HttpStatus.CONFLICT);

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .authority(authorityRepository.findByAuthorityDefaultTrue())
                .email(email)
                .enabled(true)
                .build();

        userRepository.save(user);
        return "User created";
    }

    public AuthenticationResponse login(LoginRequest loginRequest){
        String username = loginRequest.getUsername().trim();
        User user = findUserUsername(username);
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            throw new BadCredentialsException("Bad credentials");
        String jwt = jwtService.generateToken(user, user.getId());
        return AuthenticationResponse.builder()
                .id(user.getId())
                .username(username)
                .token(jwt)
                .build();
    }

    public String deleteUser(UserDetails userDetails){
        User user = (User) userDetails;
        String uid = UUID.randomUUID().toString();
        user.setEnabled(false);
        user.setUsername("*********");
        user.setPassword(passwordEncoder.encode(uid));
        user.setEmail("*********");
        userRepository.save(user);
        return "User deleted";
    }

    @Transactional
    public String resetPassword(String email){
        User user = findUserByEmail(email.trim().toLowerCase());
        String pwd = RandomStringUtils.randomAlphanumeric(10);
        Mail mail = mailService.createMail(
                user,
                "BudgetBe - Reset password",
                "Hi " + user.getUsername() + ",\n use this password " + pwd + " to login");
        mailService.sendMail(mail);

        user.setPassword(passwordEncoder.encode(pwd));
        return "You password reset. check email";
    }

    public String changePassword(UserDetails userDetails, String newPassword){
        User user = (User) userDetails;
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "Password changed";
    }

    protected User findUserUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Bad credentials"));
    }

    protected User findUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Bad credentials"));
    }
}
