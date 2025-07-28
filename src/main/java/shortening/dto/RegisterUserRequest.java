package shortening.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class RegisterUserRequest {
	
	

	@NotBlank(message = "email cannot be blank")
	@Size(max = 100)
    private String email;

	@NotBlank(message = "password cannot be blank")
	@Size(min=7, max = 100)
    private String password;

	@NotBlank(message = "name cannot be blank")
	@Size(max = 100)
    private String name;

	/**
	 * 
	 * @param email
	 * @param password
	 * @param name
	 */
	public RegisterUserRequest(String email,String password, String name) {
		this.email = email;
		this.password = password;
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
