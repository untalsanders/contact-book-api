package com.untalsanders.contacts.contact.infrastructure.persistence.jpa;

import com.untalsanders.contacts.contact.application.exception.ContactNotFoundException;
import com.untalsanders.contacts.contact.domain.Contact;
import com.untalsanders.contacts.contact.application.port.out.ContactRepository;
import com.untalsanders.contacts.contact.infrastructure.persistence.entity.ContactEntity;
import com.untalsanders.contacts.contact.infrastructure.persistence.mapper.ContactMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class JpaContactRepositoryAdapter implements ContactRepository {

    @PersistenceContext
    private EntityManager entityManager;
    private final ContactMapper mapper;

    @Override
    public Optional<Contact> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(ContactEntity.class, id))
            .map(mapper::entityToDomain)
            .map(contact -> {
                log.info("Contact found: {}", contact);
                return contact;
            });
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Contact> findAll() {
        Query query = this.entityManager.createQuery("SELECT contact FROM ContactEntity contact");
        Collection<ContactEntity> contactEntityCollection = query.getResultList();
        log.info("Total contacts found: {}", contactEntityCollection.size());
        return mapper.toContacts(contactEntityCollection.stream().toList());
    }

    @Override
    public Contact save(Contact contact) {
        ContactEntity entity = mapper.domainToEntity(contact);
        if (entity.getId() == null) {
             entityManager.persist(entity);
             log.info("Contact saved");
        } else {
            entity = entityManager.merge(entity);
            log.info("Contact updated");
        }
        return mapper.entityToDomain(entity);
    }

    @Override
    @Transactional
    public Contact update(UUID id, Contact contact) {
        Optional<Contact> existingContact = findById(id);
        if (existingContact.isEmpty()) {
            throw new ContactNotFoundException(String.valueOf(id));
        }
        ContactEntity contactEntity = mapper.domainToEntity(contact);
        contactEntity.setId(id); // Ensure the ID is set
        ContactEntity updatedEntity = entityManager.merge(contactEntity);
        log.info("Contact updated: {}", updatedEntity.getId());
        return mapper.entityToDomain(updatedEntity);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        ContactEntity contactEntity = entityManager.find(ContactEntity.class, id);
        if (contactEntity != null) {
            entityManager.remove(contactEntity);
            log.info("Deleted Contact with ID: {}", id);
        } else {
            log.warn("Contact isn't found for deletion with ID: {}", id);
        }
    }
}
