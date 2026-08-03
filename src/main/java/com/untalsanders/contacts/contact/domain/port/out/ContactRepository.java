package com.untalsanders.contacts.contact.domain.port.out;

import com.untalsanders.contacts.contact.domain.exception.ContactNotFoundException;
import com.untalsanders.contacts.contact.domain.model.Contact;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository class for <code>Contact</code> domain objets. All method names are compliant with Spring Data naming
 * conventions, so this interface can easily be extended for Spring Data see here: <a href="http://static.springsource.org/spring-data/jpa/docs/current/reference/html/jpa.repositories.html#jpa.query-methods.query-creation">http://static.springsource.org/spring-data/jpa/docs/current/reference/html/jpa.repositories.html#jpa.query-methods.query-creation</a>
 *
 * @author Sanders Gutiérrez
 */
public interface ContactRepository {
    /**
     * Retrieve all <code>Contact</code>s from the data store.
     *
     * @return <code>Collection</code> of <code>Contact</code>s
     */
    List<Contact> findAll();

    /**
     * Retrieve a <code>Contact</code> from the data store by id.
     *
     * @param id the id to search for
     * @return the <code>Contact</code> if found
     */
    Optional<Contact> findById(UUID id);

    /**
     * Save a <code>Contact</code> to the data store.
     *
     * @param contact the <code>Contact</code> to save
     * @return the saved <code>Contact</code>
     */
    Contact save(Contact contact);

    /**
     * Update a <code>Contact</code> to the data store.
     *
     * @param id      the id for search and update
     * @param contact the <code>Contact</code> to update
     * @return the updated <code>Contact</code>
     * @throws ContactNotFoundException in case the <code>Contact</code> with given id doesn't exist.
     */
    Contact update(UUID id, Contact contact);

    /**
     * Delete a <code>Contact</code> from the data store.
     *
     * @param id the id for search and removal
     */
    void deleteById(UUID id);
}
