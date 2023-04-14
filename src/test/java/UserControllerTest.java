import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.utilisateur.entity.User;
import com.example.utilisateur.restservice.UserService;
import com.example.utilisateur.controller.UserController;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("souhail");
        user.setPhoneNumber("0123456789");
        user.setGender("M");
    }

    @Test
    public void testCreateUser() {
        when(userService.getValidationErrors(user)).thenReturn(new ArrayList<>());
        ResponseEntity<String> responseEntity = userController.createUser(user);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals("L'utilisateur avec l'id : " + user.getId() + ", est enregistré avec succès.", responseEntity.getBody()); // responseEntity.getBody() est le message qu'on envoie avec :  return new ResponseEntity<>(message, HttpStatus);
        
        user.setName("");
            List<String> validationErrors = new ArrayList<>();
        validationErrors.add("Le nom de l'utilisateur est obligatoire et ne doit pas dépasser 50 caractères.");
        when(userService.getValidationErrors(user)).thenReturn(validationErrors);
       responseEntity = userController.createUser(user);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals("Le nom de l'utilisateur est obligatoire et ne doit pas dépasser 50 caractères.",
                responseEntity.getBody());
    }

    @Test
    public void testGetUser() {
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userService.getUsersByName("souhail")).thenReturn(users);
        ResponseEntity<List<User>> responseEntity = userController.getUser("souhail");
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(1, responseEntity.getBody().size());

        when(userService.getUsersByName("souhailTest")).thenReturn(new ArrayList<>());
        responseEntity = userController.getUser("souhailTest");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetUserById() {
        when(userService.getUserByID(1L)).thenReturn(Optional.of(user));
        ResponseEntity<Optional<User>> responseEntity = userController.getUserById(1L);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        if( responseEntity.getBody()!=null) {}
        Assertions.assertEquals(user, responseEntity.getBody().get());
        when(userService.getUserByID(2L)).thenReturn(Optional.empty());
        responseEntity = userController.getUserById(2L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
