package shortening.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import shortening.dto.RegisterUserRequest;
import shortening.model.User;
import shortening.repository.UserRepository;

@Service
public class UserService {

	private final PasswordEncoder passwordEncoder;
	private UserRepository userRepository;

	public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}


	public String register(RegisterUserRequest request) {
		boolean userExists = userRepository.findByEmail((request.getEmail())).isPresent();
        if(userExists){
            throw new IllegalStateException("User already exists");
        }
        
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

		return user.getName();
	}
	
	
	   
	
	
}
