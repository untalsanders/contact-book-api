package com.untalsanders.contacts.contact.domain.port.in;

import com.untalsanders.contacts.shared.domain.Result;

import java.util.UUID;

public interface DeleteContactUseCase {
    Result<Boolean> deleteContact(UUID id);
}
