package com.rl.netty.client.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String date;

    public UserDTO() {
        // used by ObjectMapper
    }

    public UserDTO(final Long id, final String name, final String email, final String date) {
        this.id = id;
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
