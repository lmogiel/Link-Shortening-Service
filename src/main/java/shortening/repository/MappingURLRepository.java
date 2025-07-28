package shortening.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import shortening.model.MappingURL;
import shortening.model.User;


public interface MappingURLRepository extends JpaRepository<MappingURL, Long> {

	
	boolean existsByShortenedCode(String shortCode);
	
	Optional<MappingURL> findByShortenedCode(String shortCode);
	
	List<MappingURL> findByCreatedBy(User user);
	
	void deleteByIdAndCreatedById(Long id, Long userId);
	
	Optional<MappingURL> findByIdAndCreatedById(Long id, Long userId);
	
	Optional<MappingURL> findByShortenedCodeAndCreatedById(String shortCode, Long userId);
	
	boolean existsByIdAndCreatedById(Long id, Long userId);
}
