package com.untalsanders.contacts.repository.jpa;

import com.untalsanders.contacts.entity.ContactEntity;
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
public class JpaContactRepository implements ContactRepository {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOG = LoggerFactory.getLogger(JpaContactRepository.class);
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
    public void update(Long id, Contact contact) {
        // TODO
    }

    @Override
    public void delete(Contact contact) {
        LOG.info("Deleting contact: {}", contact.getId());
    }

    @Override
    public void deleteById(Long id) {
        Optional<Contact> contact = findById(id);
        contact.ifPresent(value -> {
            /*contactCrudRepository.deleteById(value.getId());*/
            LOG.info("Deleted Contact with ID: {}", contact);
        });
    }
}
