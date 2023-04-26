import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.utilisateur.dto.UserDTO;
import com.example.utilisateur.restservice.UserService;

public class UserServiceTest {

    @Test
    public void testGetValidationErrors() {
        UserService userService = new UserService(null, null);

        UserDTO userDTO = new UserDTO();
        userDTO.setName("Souhail");
        userDTO.setBirthDate(null);
        userDTO.setCountry("");
        userDTO.setPhoneNumber("");
        userDTO.setGender("");

        List<String> errors = userService.getValidationErrors(userDTO);
        assertEquals(3, errors.size());
        assertEquals(
                "La date de naissance de l'utilisateur est obligatoire et l'utilisateur doit être majeur. Votre age : 0",
                errors.get(0));
        assertEquals("Le pays de l'utilisateur est obligatoire.", errors.get(1));
        assertEquals("Seuls les adultes résidents en France sont autorisés.", errors.get(2));

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date birthDate = null;
        try {
            birthDate = dateFormat.parse("17/01/1999");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        userDTO.setBirthDate(birthDate);
        userDTO.setCountry("France");
        userDTO.setPhoneNumber("0751175521");
        userDTO.setGender("M");

        errors = userService.getValidationErrors(userDTO);
        assertEquals(0, errors.size());

        String nom = "a".repeat(51);
        userDTO.setName(nom);

        errors = userService.getValidationErrors(userDTO);
        assertEquals(1, errors.size());
        assertEquals("Le nom de l'utilisateur est obligatoire et ne doit pas dépasser 50 caractères.", errors.get(0));

        userDTO.setCountry("test");
        userDTO.setName("souhail");

        errors = userService.getValidationErrors(userDTO);
        assertEquals(1, errors.size());
        assertEquals("Seuls les adultes résidents en France sont autorisés.", errors.get(0));
    }
}
