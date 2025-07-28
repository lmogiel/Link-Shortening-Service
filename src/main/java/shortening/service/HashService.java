package shortening.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jakarta.xml.bind.DatatypeConverter;

public class HashService {
	
	
	/**
	 * create hash by MD5 algorithm
	 * @param textToHash
	 * @return
	 */
	public String createHash(String textToHash) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(textToHash.getBytes());
		    byte[] digest = md.digest();
		    
		    return DatatypeConverter
		      .printHexBinary(digest).toUpperCase();
		
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
  
	}
	
}
