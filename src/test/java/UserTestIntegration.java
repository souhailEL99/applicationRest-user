import com.example.utilisateur.UtilisateurApplication;
import com.example.utilisateur.dto.UserDTO;
import com.example.utilisateur.entity.User;
import com.example.utilisateur.mapping.UserMapper;
import com.example.utilisateur.repository.UserRepository;
import com.example.utilisateur.restservice.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UtilisateurApplication.class)
@ActiveProfiles("test")
public class UserTestIntegration {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    private UserDTO userDTO;
    private User user;
    private Date birthDate;

    @BeforeEach
    void init() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            birthDate = dateFormat.parse("17/01/1999");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("souhail");
        userDTO.setCountry("France");
        userDTO.setBirthDate(birthDate);
        userDTO.setPhoneNumber("0123456789");
        user = new User();
        user.setId(1L);
        user.setName(userDTO.getName());
        user.setGender(userDTO.getGender());
        user.setBirthDate(birthDate);
        user.setCountry(userDTO.getCountry());
    }

    @Test
    public void saveUserTest() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDTO);
        userService.saveUser(user);
        Optional<UserDTO> userOptional = userService.getUserDTOByID(user.getId());
        assertThat(userOptional).isPresent();
        assertThat(userOptional.get().getName()).isEqualTo(user.getName());
        assertThat(userOptional.get().getGender()).isEqualTo(user.getGender());
        assertThat(userOptional.get().getBirthDate()).isEqualTo(user.getBirthDate());
        assertThat(userOptional.get().getCountry()).isEqualTo(user.getCountry());
    }

    @Test
    public void getUsersDTOByNameTest() {
        when(userRepository.findByName(userDTO.getName())).thenReturn(Arrays.asList(user));
        when(userMapper.toDto(user)).thenReturn(userDTO);
        List<UserDTO> userList = userService.getUsersDTOByName(userDTO.getName());
        assertThat(userList).isNotEmpty();
        assertThat(userList.get(0).getName()).isEqualTo(userDTO.getName());
    }

    @Test
    public void getUserDTOByIDTest() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDTO);
        Optional<UserDTO> userOptional = userService.getUserDTOByID(user.getId());
        assertThat(userOptional).isPresent();
        assertThat(userOptional.get().getName()).isEqualTo(userDTO.getName());
        assertThat(userOptional.get().getGender()).isEqualTo(userDTO.getGender());
        assertThat(userOptional.get().getBirthDate()).isEqualTo(userDTO.getBirthDate());
        assertThat(userOptional.get().getCountry()).isEqualTo(userDTO.getCountry());
    }

    @Test
    public void getValidationErrorsTestValid() {
        userDTO.setPhoneNumber("0123456789");
        List<String> errors = userService.getValidationErrors(userDTO);
        assertThat(errors).isEmpty();
    }

    @Test
    public void getValidationErrorsTestNOTValid() {
        userDTO.setPhoneNumber("1");
        List<String> errors = userService.getValidationErrors(userDTO);
        assertThat(errors).isNotEmpty();
    }
}
