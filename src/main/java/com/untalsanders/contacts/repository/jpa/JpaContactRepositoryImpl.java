package com.untalsanders.contacts.repository.jpa;

import com.untalsanders.contacts.entity.ContactEntity;
import com.untalsanders.contacts.exception.ContactNotFoundException;
import com.untalsanders.contacts.mapper.ContactMapper;
import com.untalsanders.contacts.model.Contact;
import com.untalsanders.contacts.repository.ContactRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class JpaContactRepositoryImpl implements ContactRepository {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOG = LoggerFactory.getLogger(JpaContactRepositoryImpl.class);
    private final ContactMapper contactMapper;

    @Override
    public Optional<Contact> findById(Long id) {
        Query query = this.em.createQuery("SELECT contact FROM ContactEntity contact WHERE contact.id = :id");
        query.setParameter("id", id);
        Contact contactFound = contactMapper.entityToDomain((ContactEntity) query.getSingleResult());
        LOG.info("Contact found: {}", contactFound);
        return Optional.ofNullable(contactFound);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Contact> findAll() {
        Query query = this.em.createQuery("SELECT contact FROM ContactEntity contact");
        Collection<ContactEntity> contactEntityCollection = query.getResultList();
        LOG.info("Total contacts found: {}", contactEntityCollection.size());
        return contactMapper.toContacts(contactEntityCollection.stream().toList());
    }

    @Override
    @Transactional
    public void save(Contact contact) {
        ContactEntity contactEntity = contactMapper.domainToEntity(contact);
        if (contactEntity.getId() == null) {
            this.em.persist(contactEntity);
            LOG.info("Contact saved");
        } else {
            this.em.merge(contactEntity);
            LOG.info("Contact updated");
        }
    }

    @Override
    @Transactional
    public Contact update(Long id, Contact contact) {
        Optional<Contact> existingContact = findById(id);
        if (existingContact.isEmpty()) {
            throw new ContactNotFoundException(String.format("Contact with id %s not found", id));
        }
        ContactEntity contactEntity = contactMapper.domainToEntity(contact);
        contactEntity.setId(id); // Ensure the ID is set
        ContactEntity updatedEntity = em.merge(contactEntity);
        LOG.info("Contact updated: {}", updatedEntity.getId());
        return contactMapper.entityToDomain(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(Contact contact) {
        ContactEntity contactEntity = contactMapper.domainToEntity(contact);
        if (contactEntity.getId() != null) {
            ContactEntity managedEntity = em.find(ContactEntity.class, contactEntity.getId());
            if (managedEntity != null) {
                em.remove(managedEntity);
                LOG.info("Contact deleted: {}", contact.getId());
            } else {
                LOG.warn("Contact not found for deletion: {}", contact.getId());
            }
        } else {
            LOG.warn("Contact ID is null, cannot delete");
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        ContactEntity contactEntity = em.find(ContactEntity.class, id);
        if (contactEntity != null) {
            em.remove(contactEntity);
            LOG.info("Deleted Contact with ID: {}", id);
        } else {
            LOG.warn("Contact not found for deletion with ID: {}", id);
        }
    }
}
