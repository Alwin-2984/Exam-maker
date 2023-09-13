/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovaturelabs.training.contacts.repository;

import com.innovaturelabs.training.contacts.entity.Contact;
import com.innovaturelabs.training.contacts.view.ContactListView;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.repository.Repository;

/**
 *
 * @author nirmal
 */
public interface ContactRepository extends Repository<Contact, Integer> {

    Collection<ContactListView> findAllByUserUserId(Integer userId);

    Optional<Contact> findByContactIdAndUserUserId(Integer contactId, Integer userId);

    Contact save(Contact contact);

    void delete(Contact contact);
}
