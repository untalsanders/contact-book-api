package com.untalsanders.contacts.contactbook.application.usecase;

import com.untalsanders.contacts.shared.domain.Result;

import java.util.UUID;

public interface DeleteContactUseCase {
    Result<Void> deleteContact(UUID id);
}
