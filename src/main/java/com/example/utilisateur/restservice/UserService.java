/**
 * Représente un service qui nous permet de gèrer les actions à réaliser sur les utilisateurs.
 */
package com.example.utilisateur.restservice;

import com.example.utilisateur.dto.UserDTO;
import com.example.utilisateur.entity.User;
import com.example.utilisateur.mapping.UserMapper;
import com.example.utilisateur.repository.UserRepository;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.apache.commons.lang3.StringUtils;

@Validated
@Service
public class UserService {

    private UserRepository userRepository;

    private UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Enregistre l'utilisateur dans la base de données.
     * 
     * @param user L'utilisateur à enregistrer.
     */
    @Transactional
    public void saveUser(User user) {
        if (user != null) {
            userRepository.save(user);
        }
    }

    /**
     * Récupère tous les utilisateurs ayant le même nom.
     * 
     * @param name Le nom des utilisateurs à récupérer.
     * @return Une liste d'utilisateurs qui ont le même name.
     */
    public List<UserDTO> getUsersDTOByName(String name) {
        return userRepository.findByName(name).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupère l'utilisateur ayant l'ID spécifié.
     * 
     * @param id L'ID de l'utilisateur à récupérer.
     * @return Un objet Optional contenant l'utilisateur en fonction de son
     *         existance.
     */
    public Optional<UserDTO> getUserDTOByID(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(userMapper::toDto);
    }

    /**
     * Vérifie si les attribtus de l'utilisateur sont conformes aux règles.
     * 
     * @param user L'utilisateur à valider.
     * @return Une liste d'erreurs s'il en existe.
     */
    public List<String> getValidationErrors(UserDTO userDTO) {
        List<String> errors = new ArrayList<>();
        if (!StringUtils.isNotBlank(userDTO.getName()) || userDTO.getName().length() > 50) {
            errors.add("Le nom de l'utilisateur est obligatoire et ne doit pas dépasser 50 caractères.");
        }
        if (userDTO.getBirthDate() == null || calculerAge(userDTO.getBirthDate()) < 18) {
            errors.add(
                    "La date de naissance de l'utilisateur est obligatoire et l'utilisateur doit être majeur. Votre age : "
                            + calculerAge(userDTO.getBirthDate()));
        }

        if (!StringUtils.isNotBlank(userDTO.getCountry())) {
            errors.add("Le pays de l'utilisateur est obligatoire.");
        }

        if (userDTO.getCountry() != null && !userDTO.getCountry().equalsIgnoreCase("france")) {
            errors.add("Seuls les adultes résidents en France sont autorisés.");
        }
        if (StringUtils.isNotBlank(userDTO.getPhoneNumber())
                && (userDTO.getPhoneNumber().length() != 10 || !userDTO.getPhoneNumber().matches("\\d+"))) {
            errors.add("Le numéro de téléphone doit contenir exactement 10 chiffres s'il est renseigné.");
        }
        if (StringUtils.isNotBlank(userDTO.getGender()) && !userDTO.getGender().equalsIgnoreCase("F")
                && !userDTO.getGender().equalsIgnoreCase("M")) {
            errors.add("Le genre de l'utilisateur doit être M ou F s'il est renseigné.");
        }
        return errors;
    }

    private static int calculerAge(Date birthDate) {
        if (birthDate == null) {
            return 0;
        }
        LocalDate localDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        Period period = Period.between(localDate, now);
        return period.getYears();
    }

}