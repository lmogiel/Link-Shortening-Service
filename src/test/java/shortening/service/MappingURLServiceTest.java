package shortening.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import shortening.dto.ShortUrlResponseDTO;
import shortening.repository.MappingURLRepository;



@ExtendWith(MockitoExtension.class)
public class MappingURLServiceTest {


	@Mock
	private MappingURLRepository mappingURLRepository;
	
	@Mock
	private SecurityService securityService;
	
	@InjectMocks
	private MappingURLService mappingURLService;
	
	
	@Test
	void testshortenUrl() {
		
		String longUrl = "https://google.pl";
		String shortenedUrl = "http://localhost/AD8B6A8";
		ReflectionTestUtils.setField(mappingURLService, "baseUrl", "http://localhost/");

		ShortUrlResponseDTO shortUrlResponseDTO  = mappingURLService.shortenUrl(longUrl);

		assertEquals(shortenedUrl, shortUrlResponseDTO.getShortenedUrl());
	}
	
	
	//TODO rest of the service test
	
	

}
