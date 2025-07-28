package shortening.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import shortening.model.User;
import shortening.repository.UserRepository;

@Component
public class SecurityService {
	
	private UserRepository userRepository;
	
	public SecurityService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	 public User getCurrentUser() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        if (authentication != null && authentication.isAuthenticated()) {
	            String email = authentication.getName();
	            return userRepository.findByEmail(email).orElse(null);
	        }
	        return null;
	    }

	    public Long getCurrentUserId() {
	        User user = getCurrentUser();
	        return user != null ? user.getId() : null;
	    }
}
