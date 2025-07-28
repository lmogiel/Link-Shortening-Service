package shortening.dto;

import java.util.List;

public record ApiErrorResponse(
		    String code,
		    String message,
		    List<String> details
		) 
		{
		    public ApiErrorResponse(String code, String message) {
		        this(code, message, null);
		    }
		}

