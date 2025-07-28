package shortening.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AliasUrlRequestDTO  extends LongUrlRequestDTO{
	
	@NotBlank(message = "Alias cannot be blank")
	@Size(max = 10)
	private String alias;

	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}

}
