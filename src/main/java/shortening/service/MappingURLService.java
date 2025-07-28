package shortening.service;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shortening.dto.ShortUrlResponseDTO;
import shortening.exception.MappingNotFoundException;
import shortening.model.MappingURL;
import shortening.model.User;
import shortening.repository.MappingURLRepository;

@Service
public class MappingURLService {

	@Value("${app.base-url}")
	private String baseUrl;

	private MappingURLRepository mappingURLRepository;
	private SecurityService securityService;

	public MappingURLService(MappingURLRepository mappingURLRepository, SecurityService securityService) {
		this.mappingURLRepository = mappingURLRepository;
		this.securityService = securityService;
	}

	public ShortUrlResponseDTO shortenUrl(String longUrl) {
		return this.shorten(this.getUniqueShortenedCode(longUrl), longUrl);
	}

	public ShortUrlResponseDTO shortenAlias(String longUrl, String alias) {

		if (!mappingURLRepository.existsByShortenedCode(alias)) {
			return this.shorten(alias, longUrl);
		}
		throw new IllegalArgumentException("Alias already exists");
	}

	public String resolveUrl(String shortenedCode) {
		MappingURL mappingURL = mappingURLRepository.findByShortenedCode(shortenedCode)
				.orElseThrow(() -> new MappingNotFoundException("Url not found with code : " + shortenedCode));
		
		mappingURL.setClickCount(mappingURL.getClickCount() + 1);
		mappingURLRepository.save(mappingURL);
		return mappingURL.getLongUrl();
	}

	public List<MappingURL> showUserMappings() {
		return mappingURLRepository.findByCreatedBy(securityService.getCurrentUser());
	}

	@Transactional
	public void deleteUserMapping(Long id) {
		if(!mappingURLRepository.existsByIdAndCreatedById(id,securityService.getCurrentUserId())) {
			throw new MappingNotFoundException("Mapping to delete not found");
		}
		mappingURLRepository.deleteByIdAndCreatedById(id, securityService.getCurrentUserId());
	}

	public MappingURL getMappingById(Long id) {
		return mappingURLRepository.findByIdAndCreatedById(id, securityService.getCurrentUserId())
				.orElseThrow(() -> new MappingNotFoundException("Mapping not found with id : " + id));
	}

	public MappingURL getMappingByShorCode(String shortenedCode) {
		return mappingURLRepository.findByShortenedCodeAndCreatedById(shortenedCode, securityService.getCurrentUserId())
				.orElseThrow(() -> new MappingNotFoundException("Mapping not found with code : " + shortenedCode));
	}

	public ShortUrlResponseDTO shorten(String shortenedCode, String longUrl) {

		this.createAndStoreMapping(longUrl, shortenedCode);

		ShortUrlResponseDTO shortUrl = new ShortUrlResponseDTO();
		shortUrl.setShortenedUrl(baseUrl + shortenedCode);
		return shortUrl;
	}

	private String getUniqueShortenedCode(String longUrl) {
		String shortenedCode;
		int counter = 0;
		do {
			counter++;
			shortenedCode = this.createNewShortenedCode(longUrl + Integer.toString(counter));
		} while (mappingURLRepository.existsByShortenedCode(shortenedCode));

		return shortenedCode;
	}

	private String createNewShortenedCode(String textToShortening) {
		HashService hash = new HashService();
		return hash.createHash(textToShortening).substring(0, 7);

	}

	private void createAndStoreMapping(String longUrl, String shortenedCode) {
		MappingURL newMappingUrl = new MappingURL();
		newMappingUrl.setLongUrl(longUrl);
		newMappingUrl.setShortenedCode(shortenedCode);

		User user = securityService.getCurrentUser();
		if (user != null) {
			newMappingUrl.setCreatedBy(user);
		}
		this.saveMapping(newMappingUrl);
	}

	public MappingURL saveMapping(MappingURL shortening) {
		return mappingURLRepository.save(shortening);
	}

}
