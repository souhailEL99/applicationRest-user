/**
 * Représente un utilisateur avec son nom, sa date de naissance, son pays, son numéro de téléphone et son genre.
 */
package com.example.utilisateur.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import lombok.Data;

@Data
@Entity
@EntityScan
@Table(name = "users")
public class User {
    /**
     * L'identifiant unique de l'utilisateur.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Le nom de l'utilisateur.
     */
    @Size(max = 50)
    @NotNull
    @NotBlank
    private String name;

    /**
     * La date de naissance de l'utilisateur.
     */
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    /**
     * Le pays de l'utilisateur.
     */
    private String country;

    /**
     * Le numéro de téléphone de l'utilisateur.
     * Doit être une chaîne de 10 chiffres max.
     */
    @Pattern(regexp = "\\d{10}")
    @NotNull
    private String phoneNumber;

    /**
     * Le genre de l'utilisateur.
     * Doit être M pour Male ou F pour Female.
     */
    @NotNull
    private String gender;

}