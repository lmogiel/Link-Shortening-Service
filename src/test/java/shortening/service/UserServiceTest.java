package shortening.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import shortening.dto.RegisterUserRequest;
import shortening.model.User;
import shortening.repository.UserRepository;



@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private UserService userService;
	
	
	
	
	@Test
	public void testRegister() {
		User mockedUser = new User();
		mockedUser.setEmail("test@email.com");
		mockedUser.setName("test");
		mockedUser.setPassword("password");
        
        when(userRepository.save(Mockito.any(User.class))).thenReturn(mockedUser);
        
        String userName = userService.register(new RegisterUserRequest("test@email.com", "password", "test"));
        
        assertEquals("test", userName);
	}
	
	
	

}
