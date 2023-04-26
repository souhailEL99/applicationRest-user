/**
 * Cette classe est le contrôleur REST pour les opérations liées aux utilisateurs.
 */
package com.example.utilisateur.controller;

import com.example.utilisateur.dto.UserDTO;
import com.example.utilisateur.entity.User;
import com.example.utilisateur.mapping.UserMapper;
import com.example.utilisateur.restservice.UserService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/users")
public class UserController {

    /**
     * Appel au service qui permet de gèrer les actions à réaliser sur les
     * utilisateurs.
     */
    private final UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

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
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        logger.info("Enregistrement d'un utilisateur");
        User user = userMapper.toEntity(userDTO);
        List<String> validationErrors = userService.getValidationErrors(userDTO);
        if (!validationErrors.isEmpty()) {
            String errorMessage = String.join("\n", validationErrors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        userService.saveUser(user);
        String message = "L'utilisateur avec l'id : " + user.getId() + " et le nom : " + user.getName()
                + ", est enregistré avec succès.";
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"Ok\": \"" + message + "\" }");
    }

    /**
     * Récupère les utilisateurs avec le nom spécifié.
     * Si le name contient un espace on, dans la requête on le remplace par : %20.
     * 
     * @param name le nom de l'utilisateur.
     * @return un objet ResponseEntity avec le statut HTTP approprié et une liste
     *         d'utilisateurs.
     */
    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getUser(@RequestParam(value = "name") String name) {
        logger.info("Recherche de l'utilisateur avec le nom : {}", name);
        if (StringUtils.isEmpty(name)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<UserDTO> users = userService.getUsersDTOByName(name);
        if (users == null || users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    /**
     * Récupère l'utilisateur avec l'id spécifié.
     * 
     * @param id l'id de l'utilisateur.
     * @return un objet ResponseEntity avec le statut HTTP approprié et
     *         l'utilisateur.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        logger.info("Recherche de l'utilisateur avec l'id : {}", id);
        Optional<UserDTO> userDTO = userService.getUserDTOByID(id);
        return userDTO.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}