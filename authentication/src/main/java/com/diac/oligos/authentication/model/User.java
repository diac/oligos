package com.diac.oligos.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;

    @NotNull
    @Field(name = "username")
    private String username;

    @Field(name = "password")
    @JsonIgnore
    private String password;

    @NotNull
    @Field(name = "isAdminUser")
    private boolean isAdmin;

    @Field(name = "isAccountExpired")
    private boolean isAccountExpired;

    @Field(name = "isAccountLocked")
    private boolean isAccountLocked;

    @Field(name = "isEnabled")
    private boolean isEnabled;

    @Field(name = "credentialsExpiryDate")
    private LocalDateTime credentialsExpiryDate;
}