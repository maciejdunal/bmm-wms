package com.wsei.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wsei.controller.model.NewUserRequest;
import com.wsei.controller.model.RoleUpdateRequest;
import com.wsei.controller.model.UserResponse;
import com.wsei.model.Role;
import com.wsei.model.User;
import com.wsei.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @RequestMapping(value = "/healthCheck", method = RequestMethod.GET)
    @ResponseBody
    public String healthCheck() {
        return "OK";
    }

    private UserResponse mapToResponse(User user) {

        Role role = user.getRole();

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .roleName(Objects.nonNull(role) ? role.getName() : null)
                .build();
    }

    @PreAuthorize("hasRole('Manager') or hasRole('User')")
    @GetMapping("/users")
    public List<UserResponse> getUsers(){
        return userService.getUsers()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    @PreAuthorize("hasRole('Manager')")
    @GetMapping("/users/{id}")
    public UserResponse getUser(@PathVariable Long id)
    {
    return mapToResponse(userService.getUser(id));
    }

    @PreAuthorize("hasRole('Manager')")
    @PostMapping("/users")
    public UserResponse addUser(@Valid @RequestBody NewUserRequest request)
    {
        User user = userService.saveUser(request);
        return mapToResponse(user);
    }

    @PreAuthorize("hasRole('Manager')")
    @PutMapping("/users/{id}")
    public UserResponse updateUser(@RequestBody NewUserRequest newUser, @PathVariable Long id)
    {
        return mapToResponse(userService.updateUser(newUser, id));
    }

    @PreAuthorize("hasRole('Manager')")
    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id)
    {
        userService.deleteUser(id);
    }

    @PreAuthorize("hasRole('Manager')")
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles()
    {
        return ResponseEntity.ok().body(userService.getRoles());
    }

    @PreAuthorize("hasRole('Manager')")
    @PatchMapping("/roles/add-to-user")
    public UserResponse updateUserRole(@RequestBody RoleUpdateRequest request){
        User user = userService.assignRole(request);

        return mapToResponse(user);
    }



    @PreAuthorize("hasRole('Manager') or hasRole('User') or hasRole('Read-Only User')")
    @GetMapping("/tokens/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                    .withClaim("roles", List.of(user.getRole().getName()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }
            catch (Exception exception){
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }
        else{
            throw new RuntimeException("Refresh token is missing");
        }
    }

}