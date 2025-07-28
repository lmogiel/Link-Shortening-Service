package shortening.exception;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shortening.dto.ApiErrorResponse;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler
{

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
    	List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            errors.add(((FieldError) error ).getField() + " : " + error.getDefaultMessage() );
        });

        ApiErrorResponse response = new ApiErrorResponse(
                "VALIDATION_ERROR",
                "Validation failed",
                errors
            );
            
       return ResponseEntity.badRequest().body(response);
    }
    
    
	@ExceptionHandler(MappingNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleMappingNotFound(MappingNotFoundException ex) {
		ApiErrorResponse response = new ApiErrorResponse(
            "MAPPING_NOT_FOUND",
            ex.getMessage()
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
	@ExceptionHandler(HttpMessageNotReadableException .class)
    public ResponseEntity<ApiErrorResponse> handleMissingBody(HttpMessageNotReadableException ex) {
		ApiErrorResponse response = new ApiErrorResponse(
            "WRONG_MESSAGE_BODY",
            ex.getMessage()
        );
        
		return ResponseEntity.badRequest().body(response);
    }
	
	
	@ExceptionHandler(Exception.class)
	    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex) {
		 ApiErrorResponse response = new ApiErrorResponse(
	            "INTERNAL_ERROR",
	            "An unexpected error occurred"
	        );
	        
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
}