package com.untalsanders.contacts.user.application;

import com.untalsanders.contacts.user.domain.port.in.DeleteUserUseCase;
import com.untalsanders.contacts.user.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DeleteUserService implements DeleteUserUseCase {
    private final UserRepository userRepository;

    @Override
    public boolean deleteById(String id) {
        return userRepository.deleteById(id);
    }

    @Override
    public boolean deleteByEmail(String email) {
        return userRepository.deleteByEmail(email);
    }

    @Override
    public boolean deleteByUsername(String username) {
        return userRepository.deleteByUsername(username);
    }
}
