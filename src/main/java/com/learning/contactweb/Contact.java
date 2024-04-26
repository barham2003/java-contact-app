package com.learning.contactweb;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
}
