package com.untalsanders.contacts.user.web.controller;

import com.untalsanders.contacts.shared.infrastructure.web.dto.ApiResponse;
import com.untalsanders.contacts.user.application.RetrieveUserService;
import com.untalsanders.contacts.user.web.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final RetrieveUserService retrieveUserService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        var usersResult = retrieveUserService.retrieveAllUsers();
        ApiResponse<List<UserResponse>> response = ApiResponse.success(
            200,
            usersResult.getValue(),
            "Successfully retrieved users"
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable String id) {
        var userResult = retrieveUserService.findUserById(id);
        ApiResponse<UserResponse> response = ApiResponse.success(
            200,
            userResult.getValue(),
            "Successfully retrieved user"
        );
        return ResponseEntity.ok(response);
    }
}
