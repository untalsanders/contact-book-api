package com.untalsanders.contacts.usecase;

import com.untalsanders.contacts.shared.domain.Result;

public interface DeleteContactUseCase {
    Result<Void> deleteContact(Long id);
}
