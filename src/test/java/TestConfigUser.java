import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.example.utilisateur.repository.UserRepository;
import com.example.utilisateur.restservice.UserService;

@TestConfiguration
public class TestConfigUser {

    @MockBean
    private UserRepository userRepository;

    @Bean
    public UserService userService() {
        return new UserService(userRepository);
    }

    @Bean
    public LocalValidatorFactoryBean validatorFactory() {
        return new LocalValidatorFactoryBean();
    }

}