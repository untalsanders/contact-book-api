package com.untalsanders.contacts.contact.application.port.in;

import com.untalsanders.contacts.shared.domain.vo.Result;

import java.util.UUID;

public interface DeleteContactUseCase {
    Result<Boolean, String> deleteContact(UUID id);
}
