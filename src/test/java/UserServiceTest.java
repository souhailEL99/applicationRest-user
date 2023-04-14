
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.utilisateur.entity.User;
import com.example.utilisateur.repository.UserRepository;
import com.example.utilisateur.restservice.UserService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfigUser.class })
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void saveUserTest() {
        /*
         * User user = new User();
         * user.setName("souhail");
         * user.setPhoneNumber("1234567890");
         * user.setGender("M");
         * userService.saveUser(user);
         * /*
         * Optional<User> savedUser = userService.getUserByID(user.getId());
         * assertTrue(savedUser.isPresent());
         * assertEquals(user.getName(), savedUser.get().getName());
         * assertEquals(user.getPhoneNumber(), savedUser.get().getPhoneNumber());
         * assertEquals(user.getGender(), savedUser.get().getGender());
         */
    }

    @Test
    public void getUsersByNameTest() {
        /*
         * User user1 = new User();
         * user1.setName("souhail");
         * user1.setPhoneNumber("1234567777890");
         * user1.setGender("M");
         * userService.saveUser(user1);
         * 
         * 
         * User user2 = new User();
         * user2.setName("elisa");
         * user2.setPhoneNumber("0987654321");
         * user2.setGender("F");
         * userService.saveUser(user2);
         * 
         * List<User> users = userService.getUsersByName("elisa");
         * assertEquals(2, users.size());
         * assertEquals(user1.getName(), users.get(0).getName());
         * assertEquals(user2.getName(), users.get(1).getName());
         * assertEquals(user1.getPhoneNumber(), users.get(0).getPhoneNumber());
         * assertEquals(user2.getPhoneNumber(), users.get(1).getPhoneNumber());
         * assertEquals(user1.getGender(), users.get(0).getGender());
         * assertEquals(user2.getGender(), users.get(1).getGender());
         */
    }

    @Test
    public void getUserByIDTest() {
        /*
         * User user = new User();
         * user.setName("souhail");
         * user.setPhoneNumber("1234567890");
         * user.setGender("M");
         * userService.saveUser(user);
         * 
         * 
         * Optional<User> savedUser = userService.getUserByID(user.getId());
         * assertTrue(savedUser.isPresent());
         * assertEquals(user.getName(), savedUser.get().getName());
         * assertEquals(user.getPhoneNumber(), savedUser.get().getPhoneNumber());
         * assertEquals(user.getGender(), savedUser.get().getGender());
         */
    }

    @Test
    public void getUserByIDUsingRepositoryTest() {
        User user = new User();
        user.setName("souhail");
        user.setPhoneNumber("1234567890");
        user.setGender("M");
        user.setId(1L);
        /*
         * userService.saveUser(user);
         * 
         * Optional<User> savedUser = userService.getUserByID(user.getId());
         * assertTrue(savedUser.isPresent());
         * assertEquals(user.getName(), savedUser.get().getName());
         * assertEquals(user.getPhoneNumber(), savedUser.get().getPhoneNumber());
         * assertEquals(user.getGender(), savedUser.get().getGender());
         */
    }

    @Test
    public void getValidationErrorsTest() {
        User user = new User();
        user.setName("");
        user.setPhoneNumber("");
        user.setGender("A");

        /*
         * List<String> errors = userService.getValidationErrors(user);
         * assertNotNull(errors);
         * assertFalse(errors.isEmpty());
         * assertEquals(3, errors.size());
         * Assertions.assertTrue(
         * errors.
         * contains("Le nom de l'utilisateur est obligatoire et ne doit pas dépasser 50 caractères."
         * ));
         * Assertions.assertTrue(errors.contains(
         * "Le numéro de téléphone de l'utilisateur ne doit pas dépasser 10 chiffres et ne doit pas être null."
         * ));
         * Assertions.assertTrue(errors.
         * contains("Le genre de l'utilisateur doit être M ou F."));
         */
    }
}
