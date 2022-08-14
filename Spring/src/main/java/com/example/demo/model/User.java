package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;


@Entity
@Getter
@Setter
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    @NotBlank(message = "First name is mandatory")
    private String firstName;


    @Column(nullable = false)
    @NotBlank(message = "Last name is mandatory")
    private String lastName;


    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email is mandatory")
    private String email;


    @Column(nullable = false)
    @NotBlank(message = "Password is mandatory")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(length = 1000)
    private HashSet<Permissions> permissions = new HashSet<>();


   /* @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Machine> machines;*/

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashSet<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(HashSet<Permissions> permissions) {
        this.permissions = permissions;
    }
}
