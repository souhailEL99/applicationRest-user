/**
 * Cette interface est un repository pour la gestion des entités User en base de données.
 */
package com.example.utilisateur.repository;

import com.example.utilisateur.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Récupère tous les utilisateurs ayant le même nom.
     * 
     * @param name Le nom des utilisateurs à récupérer.
     * @return Une liste d'utilisateurs qui ont le même name.
     */
    @Query
    List<User> findByName(String name);

    /**
     * Récupère l'utilisateur ayant l'ID spécifié.
     * 
     * @param id L'ID de l'utilisateur à récupérer.
     * @return Un objet Optional contenant l'utilisateur en fonction de son
     *         existance.
     */
    @Query
    Optional<User> findById(Long id);
}