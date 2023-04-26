/**
 * Représente un utilisateur avec son nom, sa date de naissance, son pays, son numéro de téléphone et son genre.
 */
package com.example.utilisateur.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    /**
     * Le pays de l'utilisateur.
     */
    @NotNull
    private String country;

    /**
     * Le numéro de téléphone de l'utilisateur.
     * Doit être une chaîne de 10 chiffres max.
     */
    @Pattern(regexp = "\\d{0,10}")
    private String phoneNumber;

    /**
     * Le genre de l'utilisateur.
     * Doit être M pour Male ou F pour Female.
     */
    @Pattern(regexp = "[MF]{0,1}")
    private String gender;

    /**
     * Constructeur par défaut.
     */
    public User() {
    }

    /**
     * Constructeur avec paramètres.
     * 
     * @param name      Le nom de l'utilisateur.
     * @param birthDate La date de naissance de l'utilisateur.
     * @param country   Le pays de résidence de l'utilisateur.
     */
    public User(String name, Date birthDate, String country) {
        this.name = name;
        this.birthDate = birthDate;
        this.country = country;
    }
}