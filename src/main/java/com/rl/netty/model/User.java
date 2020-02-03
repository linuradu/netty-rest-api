package com.rl.netty.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import static com.rl.netty.repository.UserRepository.USER_LOAD_USERS_BY_CRITERIA_ASC;
import static com.rl.netty.repository.UserRepository.USER_LOAD_USERS_BY_CRITERIA_DESC;

@Entity
@NamedQueries({
        @NamedQuery(name = USER_LOAD_USERS_BY_CRITERIA_ASC, query = "SELECT u FROM User u where " +
                "(:name is null or u.name = :name) AND " +
                "(:email is null or u.email = :email) AND " +
                "(:date is null or u.date = :date) " +
                "ORDER BY u.name ASC"),
        @NamedQuery(name = USER_LOAD_USERS_BY_CRITERIA_DESC, query = "SELECT u FROM User u where " +
                "(:name is null or u.name = :name) AND " +
                "(:email is null or u.email = :email) AND " +
                "(:date is null or u.date = :date) " +
                "ORDER BY u.name DESC")
})
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String date; // TODO - change this as Date

    public User() {
        // used by Hibernate
    }

    public User(final Long id, final String name, final String email, final String date) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.date = date;
    }

    public User(final String name, final String email, final String date) {
        this.name = name;
        this.email = email;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }
}
