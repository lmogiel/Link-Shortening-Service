package shortening.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import shortening.model.MappingURL;
import shortening.service.MappingURLService;


@RequestMapping("/app")
@RestController
@Tag(name = "mappings")
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "basicAuth",
description = "authorization with basic", scheme = "basic")

public class MappingApiController {

	
	private MappingURLService mappingURLService;
	
	public MappingApiController(MappingURLService mappingURLService) {
		this.mappingURLService = mappingURLService;
	}

	
	@Operation(summary = "Get list of all mapping for shortening links", description = "Return list of all user shortening links")
	@GetMapping("/mappings")
	  public ResponseEntity<List<MappingURL>> getMappingsByUser() {
	      List<MappingURL> userMappings = mappingURLService.showUserMappings();
	      if (userMappings.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }
	      return new ResponseEntity<>(userMappings, HttpStatus.OK);
	  }
	
	
	
	@Operation(summary = "Delete mapping", description = "delete mapping by id ")
	@DeleteMapping("/mapping/{id}")
	public ResponseEntity<Void> deleteMapping(@PathVariable long id) {
		
			mappingURLService.deleteUserMapping(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);  
	}
	
	
	@Operation(summary = "Display mapping", description = "get specific mapping data by id")
	@GetMapping("/mapping/{id}")
	public ResponseEntity<MappingURL> getMappingById(@PathVariable long id){
		return new ResponseEntity<>(mappingURLService.getMappingById(id),HttpStatus.OK);
	}
	
	
	
	@Operation(summary = "Display mapping by Shortened Code", description = "get specific mapping data by shortened code")
	@GetMapping("/mapping/short/{shortenedCode}")
	public ResponseEntity<MappingURL> getMappingByShorCode(@PathVariable String shortenedCode){
		return new ResponseEntity<>(mappingURLService.getMappingByShorCode(shortenedCode),HttpStatus.OK);
	}
	
	
}
