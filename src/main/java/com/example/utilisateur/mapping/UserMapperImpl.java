/**
Cette classe implémente l'interface UserMapper et permet de convertir un objet User en UserDTO
et vice versa.
*/
package com.example.utilisateur.mapping;

import org.springframework.stereotype.Component;

import com.example.utilisateur.dto.UserDTO;
import com.example.utilisateur.entity.User;

@Component
public class UserMapperImpl implements UserMapper {

    /**
     * Convertit un objet User en un objet UserDTO.
     * 
     * @param user l'utilisateur à convertir.
     * @return l'objet UserDTO correspondant.
     */
    @Override
    public UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setBirthDate(user.getBirthDate());
        dto.setCountry(user.getCountry());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setGender(user.getGender());
        return dto;
    }

    /**
     * Convertit un objet UserDTO en un objet User.
     * 
     * @param userDTO l'utilisateur à convertir.
     * @return l'objet User correspondant.
     */
    @Override
    public User toEntity(UserDTO userDTO) {
        User entity = new User();
        entity.setId(userDTO.getId());
        entity.setName(userDTO.getName());
        entity.setBirthDate(userDTO.getBirthDate());
        entity.setCountry(userDTO.getCountry());
        entity.setPhoneNumber(userDTO.getPhoneNumber());
        entity.setGender(userDTO.getGender());
        return entity;
    }

}
