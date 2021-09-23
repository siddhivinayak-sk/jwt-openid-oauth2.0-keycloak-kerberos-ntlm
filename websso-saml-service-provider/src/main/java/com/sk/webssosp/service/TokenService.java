package com.sk.webssosp.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sk.webssosp.config.AppConfig;
import com.sk.webssosp.core.DataEncryption;
import com.sk.webssosp.dto.HttpCall;
import com.sk.webssosp.dto.TokenDTO;
import com.sk.webssosp.entity.Token;
import com.sk.webssosp.repository.TokenRepository;

/**
 * @author Sandeep Kumar
 */
@Service 
@Transactional
public class TokenService {
    
    @Autowired
    private TokenRepository tokenRepository;
    
	@Autowired
	private AppConfig appUrlConfig;
	
	@Autowired
	private HttpCallService httpCallService;
    
    @Transactional
    public Token saveToken(String username, String domain, String inputToken) throws NoSuchAlgorithmException {
        String[] tokenList = inputToken.split("&");
        Token token = new Token();
        token.setIdToken(generateUuidUsingSecureRandom());
        token.setUsername(DataEncryption.encrypt(username));
        if (tokenList.length == 2) {
            token.setRefreshToken(DataEncryption.encrypt(tokenList[1]));
        }
        token.setAccessToken(DataEncryption.encrypt(tokenList[0]));
        token.setDomain(DataEncryption.encrypt(domain));
        token.setCreatedDate(new Date());
        return tokenRepository.save(token);
    }
    
    public String generateUuidUsingSecureRandom() throws NoSuchAlgorithmException {
        String uuid = null;
        
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        String randomNumber = Integer.toString(secureRandom.nextInt());
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] uuidBytes = messageDigest.digest(randomNumber.getBytes());
        uuid = convertingHEXValue(uuidBytes).toString();
        
        return uuid;
    }
    
    static private StringBuilder convertingHEXValue(byte[] uuidBytes) {
        StringBuilder uuid = new StringBuilder();
        char[] uuidKeys = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        for (int index = 0; index < uuidBytes.length; ++ index) {
            byte uuidByte = uuidBytes[index];
            uuid.append(uuidKeys[(uuidByte & 0xf0) >> 4]);
            uuid.append(uuidKeys[uuidByte & 0x0f]);
        }
        return uuid;
    }
    
    @Transactional
    public TokenDTO getToken(String idToken) {
        Token token = tokenRepository.findByIdToken(idToken);
        TokenDTO dto = new TokenDTO();
        if (Objects.nonNull(token)) {
            dto.setIdToken(token.getIdToken());
            if (token.getRefreshToken() != null)
                dto.setRefreshToken(DataEncryption.decrypt(token.getRefreshToken()));
            dto.setAccessToken(DataEncryption.decrypt(token.getAccessToken()));
            dto.setUsername(DataEncryption.decrypt(token.getUsername()));
            dto.setDomain(DataEncryption.decrypt(token.getDomain()));
        }
        return dto;
    }
    
    @Transactional
    public void deleteToken(String idToken) {
        Token token = tokenRepository.findByIdToken(idToken);
        tokenRepository.delete(token);
    }

    private HttpCall getCall(String relayState) {
    	for(HttpCall call:appUrlConfig.getRedirectauth()) {
    		if(call.getName().equalsIgnoreCase(relayState)) {
    			return call;
    		}
    	}
    	return null;
    }
    
    public String generateToken(String username, String domain, String relayState) {
    	HttpCall call = getCall(relayState);
    	return httpCallService.makeCall(call, username, domain, relayState);
    }
}
