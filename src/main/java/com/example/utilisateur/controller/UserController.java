/**
 * Cette classe est le contrôleur REST pour les opérations liées aux utilisateurs.
 */
package com.example.utilisateur.controller;

import com.example.utilisateur.entity.User;
import com.example.utilisateur.restservice.UserService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RestController
@RequestMapping("/users")
public class UserController {

    /**
     * Appel au service qui permet de gèrer les actions à réaliser sur les
     * utilisateurs.
     */
    @Autowired
    private UserService userService;

    /**
     * Logger pour suivre les actions réalisées.
     */
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * Crée un nouvel utilisateur.
     *
     * @param user l'utilisateur à créer.
     * @return un objet ResponseEntity avec le statut HTTP approprié et un message.
     */
    @PostMapping("")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        logger.info("Enregistrement d'un utilisateur");
        List<String> validationErrors = userService.getValidationErrors(user);
        if (!validationErrors.isEmpty()) {
            String errorMessage = String.join("\n", validationErrors);
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        userService.saveUser(user);
        String message = "L'utilisateur avec l'id : " + user.getId() + ", est enregistré avec succès.";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    /**
     * Récupère les utilisateurs avec le nom spécifié.
     * Si le name contient un espace on, dans la requête on le remplace par : %20.
     * 
     * @param name le nom de l'utilisateur.
     * @return un objet ResponseEntity avec le statut HTTP approprié et une liste
     *         d'utilisateurs.
     */
    @GetMapping("/{name}")
    public ResponseEntity<List<User>> getUser(@PathVariable String name) {
        logger.info("Recherche de l'utilisateur avec le nom : {}", name);
        List<User> users = userService.getUsersByName(name);
        if (users == null || users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Récupère l'utilisateur avec l'id spécifié.
     * 
     * @param id l'id de l'utilisateur.
     * @return un objet ResponseEntity avec le statut HTTP approprié et
     *         l'utilisateur.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) {
        logger.info("Recherche de l'utilisateur avec l'id : {}", id);
        Optional<User> user = userService.getUserByID(id);
        return user.isPresent() ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
}