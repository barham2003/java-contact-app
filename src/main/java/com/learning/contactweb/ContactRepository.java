package com.learning.contactweb;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;


public interface ContactRepository  extends CrudRepository<Contact, Integer> {
    boolean existsByNumber(String number);
    @Query("SELECT c FROM Contact c WHERE c.number = :number")
    Contact findByNumber(@Param("number") String number);



    boolean existsByFirstNameAndLastName(String firstName, String lastName);
    @Query("SELECT c FROM Contact c WHERE c.firstName= :firstNumber AND c.lastName=:lastName")
    Contact findByFirstNameAndLastName(@Param("firstNumber") String firstNumber, @Param("lastName") String lastName);


    @Query("SELECT c FROM Contact c WHERE c.firstName = :term OR c.lastName = :term OR c.number = :term")
    ArrayList<Contact> searchContacts(@Param("term") String term);
}
