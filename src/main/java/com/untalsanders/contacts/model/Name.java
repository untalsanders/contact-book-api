package com.untalsanders.contacts.model;

import lombok.Value;

/**
 * Value Object representing a person's name.
 *
 * @author Sanders Gutiérrez
 */
@Value
public class Name {
    String first;
    String last;
}
