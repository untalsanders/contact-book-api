package com.untalsanders.contacts.user.application;

import com.untalsanders.contacts.shared.domain.Result;
import com.untalsanders.contacts.user.domain.port.in.RetrieveUserUseCase;
import com.untalsanders.contacts.user.domain.port.out.UserRepository;
import com.untalsanders.contacts.user.web.dto.UserResponse;
import com.untalsanders.contacts.user.web.mapper.UserWebMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RetrieveUserService implements RetrieveUserUseCase {

    private final UserRepository userRepository;
    private final UserWebMapper userMapper;

    @Override
    public Result<UserResponse> findUserById(String id) {
        try {
            return userRepository.findById(id)
                .map(userMapper::toUserResponse)
                .map(Result::success)
                .orElseGet(() -> Result.failure(String.format("User with id %s not found", id)));
        } catch (Exception e) {
            return Result.failure("Failed to retrieve user: " + e.getMessage());
        }
    }

    @Override
    public Result<UserResponse> getUserByEmail(String email) {
        try {
            return userRepository.findByEmail(email)
                .map(userMapper::toUserResponse)
                .map(Result::success)
                .orElseGet(() -> Result.failure(String.format("User with email %s not found", email)));
        } catch (Exception e) {
            return Result.failure("Failed to retrieve user: " + e.getMessage());
        }
    }

    @Override
    public Result<UserResponse> getUserByUsername(String username) {
        try {
            return userRepository.findByUsername(username)
                .map(userMapper::toUserResponse)
                .map(Result::success)
                .orElseGet(() -> Result.failure(String.format("User with username %s not found", username)));
        } catch (Exception e) {
            return Result.failure("Failed to retrieve user: " + e.getMessage());
        }
    }

    @Override
    public Result<List<UserResponse>> retrieveAllUsers() {
        try {
            return Result.success(userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList());
        } catch (Exception e) {
            return Result.failure("Failed to retrieve users: " + e.getMessage());
        }
    }
}
