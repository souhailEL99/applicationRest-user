/**
 * Cette classe représente un objet UserDTO qui contient les informations de base de la class User.
 * Elle est utilisée pour transférer les données de l'utilisateur entre différentes couches de l'application.
 * Cette classe utilise le framework MapStruct comme demandé pour faciliter la conversion de l'entité User.
 */

package com.example.utilisateur.dto;

import java.util.Date;

import org.mapstruct.Mapper;
import lombok.Data;

@Mapper
@Data
public class UserDTO {
    private Long id;
    private String name;
    private Date birthDate;
    private String country;
    private String phoneNumber;
    private String gender;
}
