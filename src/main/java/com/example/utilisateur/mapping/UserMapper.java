/**
 * Cette interface définit les méthodes de mapping entre l'entité `User` et l'objet `UserDTO`.
 * Elle utilise le framework MapStruct pour faciliter la conversion.
 * Les annotations de l'interface sont :
 * - @Mapper : indique que l'interface est un mapper MapStruct, et utilise le modèle de composant Spring pour la gestion de ses instances.
 * - @Mapping : indique la correspondance entre les champs de l'entité et de l'objet DTO et qui doit être les mêmes que dans User.
 * - @InheritInverseConfiguration : indique que la méthode inverse de la méthode de mapping doit être générée automatiquement par MapStruct.
 */

package com.example.utilisateur.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.example.utilisateur.dto.UserDTO;
import com.example.utilisateur.entity.User;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "country", target = "country")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "gender", target = "gender")
    UserDTO toDto(User user);

    @InheritInverseConfiguration
    User toEntity(UserDTO userDTO);
}