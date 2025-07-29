package com.untalsanders.contacts.repository;

import com.untalsanders.contacts.exception.ContactNotFoundException;
import com.untalsanders.contacts.model.Contact;

import java.util.List;
import java.util.Optional;

/**
 * Repository class for <code>Contact</code> domain objets. All method names are compliant with Spring Data naming
 * conventions so this interface can easily be extended for Spring Data see here: <a href="http://static.springsource.org/spring-data/jpa/docs/current/reference/html/jpa.repositories.html#jpa.query-methods.query-creation">http://static.springsource.org/spring-data/jpa/docs/current/reference/html/jpa.repositories.html#jpa.query-methods.query-creation</a>
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
    Optional<Contact> findById(Long id);

    /**
     * Save a <code>Contact</code> to the data store.
     *
     * @param contact the <code>Contact</code> to save
     * @return the <code>Contact</code> saved
     */
    void save(Contact contact);

    /**
     * Update a <code>Contact</code> to the data store.
     *
     * @param id      the id for search and update
     * @param contact the <code>Contact</code> to update
     * @throws ContactNotFoundException in case the <code>Contact</code> with given id not exists.
     */
    void update(Long id, Contact contact);

    /**
     * Delete a <code>Contact</code> from the data store.
     *
     * @param id the id for search and removal
     */
    void deleteById(Long id);

    /**
     * Delete a <code>Contact</code> from the data store.
     *
     * @param contact the <code>Contact</code> for search and removal
     */
    void delete(Contact contact);
}
