package com.untalsanders.contacts.contactbook.application.usecase;

import com.untalsanders.contacts.shared.domain.Result;

public interface DeleteContactUseCase {
    Result<Void> deleteContact(Long id);
}
