package shortening.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import shortening.dto.AliasUrlRequestDTO;
import shortening.dto.ApiErrorResponse;
import shortening.dto.LongUrlRequestDTO;
import shortening.dto.ShortUrlResponseDTO;
import shortening.service.MappingURLService;



@RestController
@Tag(name = "shortening")
public class ShorteningApiController {
	
	private MappingURLService mappingURLService;

	public ShorteningApiController(MappingURLService mappingURLService) {
		this.mappingURLService = mappingURLService;
	}

	@Operation(
		        summary = "Generate shortened URL",
		        description = "Retrive long url and return shortened value. If you send credentials also save result in your mappings."
		    )
		    @ApiResponses(value = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Url shortened",
		            content = @Content(schema = @Schema(implementation = ShortUrlResponseDTO.class))
		        ),
		        @ApiResponse(
		            responseCode = "400",
		            description = "Bad request / url in wrong format",
		            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
		        )
		    })
	@PostMapping("/shorten")
	public ResponseEntity<ShortUrlResponseDTO> shorten(@Valid @RequestBody LongUrlRequestDTO longUrl) {
		return new ResponseEntity<>(mappingURLService.shortenUrl(longUrl.getUrl()), HttpStatus.OK);
	}
	
	
	
	@Operation(summary = "return shortened URL", description = "Retrive long url and alias return shortened value. You need to bo log.")
	@PostMapping("/shorten/alias")
	public ResponseEntity<ShortUrlResponseDTO> shortenAlias(@Valid @RequestBody AliasUrlRequestDTO request) {
		return new ResponseEntity<>(mappingURLService.shortenAlias(request.getUrl(), request.getAlias()), HttpStatus.OK);
	}
	
	
	
	@Operation(summary = "redirect to original link", description = "Redirect to original link base on shortened code.")
	
	@GetMapping("/{shortenedCode}")
	public RedirectView resolve(@PathVariable String shortenedCode) {
		
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(mappingURLService.resolveUrl(shortenedCode));
		return redirectView;
		
	}
}
