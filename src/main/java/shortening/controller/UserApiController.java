package shortening.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import shortening.dto.RegisterUserRequest;
import shortening.service.UserService;

@RestController
@Tag(name = "user")
public class UserApiController {
	
	private UserService userService;
	
	
	public UserApiController(UserService userService) {
		this.userService = userService;
	}


	@PostMapping("/app/registration")
	public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterUserRequest user){
			return new ResponseEntity<>(userService.register(user), HttpStatus.CREATED);
	}

}
