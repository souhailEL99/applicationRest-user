/**
 * Représente un service qui nous permet de gèrer les actions à réaliser sur les utilisateurs.
 */
package com.example.utilisateur.restservice;

import com.example.utilisateur.entity.User;
import com.example.utilisateur.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.apache.commons.lang3.StringUtils;

@Validated
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository2) {
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
    public List<User> getUsersByName(String name) {
        return userRepository.findByName(name);
    }

    /**
     * Récupère l'utilisateur ayant l'ID spécifié.
     * 
     * @param id L'ID de l'utilisateur à récupérer.
     * @return Un objet Optional contenant l'utilisateur en fonction de son
     *         existance.
     */
    public Optional<User> getUserByID(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Vérifie si les attribtus de l'utilisateur sont conformes aux règles.
     * 
     * @param user L'utilisateur à valider.
     * @return Une liste d'erreurs s'il en existe.
     */
    public List<String> getValidationErrors(User user) {
        List<String> errors = new ArrayList<>();
        if (!StringUtils.isNotBlank(user.getName()) || user.getName().length() > 50) {
            errors.add("Le nom de l'utilisateur est obligatoire et ne doit pas dépasser 50 caractères.");
        }
        if (!StringUtils.isNotBlank(user.getPhoneNumber()) || user.getPhoneNumber().length() != 10) {
            errors.add(
                    "Le numéro de téléphone de l'utilisateur doit être exactement 10 chiffres");
        }
        if (!user.getGender().equalsIgnoreCase("F") && !user.getGender().equalsIgnoreCase("M")) {
            errors.add("Le genre de l'utilisateur doit être M ou F.");
        }
        return errors;
    }

}