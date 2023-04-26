
/**

Cette class est utilisée pour tester les méthodes de la classe UserController.
Elle utilise les bibliothèques Mockito et JUnit5 pour effectuer les tests unitaires.
*/
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.example.utilisateur.restservice.UserService;
import com.example.utilisateur.controller.UserController;
import com.example.utilisateur.dto.UserDTO;
import com.example.utilisateur.entity.User;
import com.example.utilisateur.mapping.UserMapper;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    private UserDTO userDTO;
    private Date birthDate;

    @BeforeEach
    public void init() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        birthDate = null;
        try {
            birthDate = dateFormat.parse("17/01/1999");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("souhail");
        userDTO.setCountry("france");
        userDTO.setBirthDate(birthDate);
    }

    @Test
    public void testCreateUser() {
        when(userService.getValidationErrors(userDTO)).thenReturn(new ArrayList<>());
        User user = new User("souhail", birthDate ,"France");
        user.setId(1L);
        when(userMapper.toEntity(userDTO)).thenReturn(user);

        ResponseEntity<String> responseEntity = userController.createUser(userDTO);
    
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()); // code 201
       Assertions.assertEquals( "{\"Ok\": \"" + "L'utilisateur avec l'id : " + userDTO.getId() + " et le nom : " + userDTO.getName()
       + ", est enregistré avec succès."+"\" }", responseEntity.getBody());
    
        userDTO.setName("");
    
        List<String> validationErrors = new ArrayList<>();
        validationErrors.add("Le nom de l'utilisateur est obligatoire et ne doit pas dépasser 50 caractères.");
        when(userService.getValidationErrors(userDTO)).thenReturn(validationErrors);
        
        responseEntity = userController.createUser(userDTO);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals("Le nom de l'utilisateur est obligatoire et ne doit pas dépasser 50 caractères.",
                responseEntity.getBody());
    }

    @Test
    public void testGetUser() {
        List<UserDTO> users = new ArrayList<>();
        users.add(userDTO);
        when(userService.getUsersDTOByName("souhail")).thenReturn(users);

        ResponseEntity<List<UserDTO>> responseEntity = userController.getUser("souhail");

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        when(userService.getUsersDTOByName("souhailTest")).thenReturn(new ArrayList<>());

        responseEntity = userController.getUser("souhailTest");

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetUserById() {
        when(userService.getUserDTOByID(1L)).thenReturn(Optional.of(userDTO));

        ResponseEntity<UserDTO> responseEntity = userController.getUserById(1L);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(userDTO, responseEntity.getBody());

        when(userService.getUserDTOByID(2L)).thenReturn(Optional.empty());

        responseEntity = userController.getUserById(2L);
        
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
