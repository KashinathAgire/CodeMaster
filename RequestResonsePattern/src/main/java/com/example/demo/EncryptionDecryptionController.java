package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class EncryptionDecryptionController {
	private static int AES_128 = 128;
	private static final Logger logger = LoggerFactory.getLogger(EncryptionDecryptionController.class);

	@Autowired
	private Service service;

	@GetMapping("/getKey")
	public String getKey() throws Exception {
		Constants.setKey_ED(Encryption.generateKey(AES_128));
		return Constants.getKey_ED();
	}

	@PostMapping("/sendRequest")
	public String getResponse(@RequestBody String encryptedMessage) {
		logger.info("The Encrypted Response is:" + service.getDecodeEncode(encryptedMessage));
		return service.getDecodeEncode(encryptedMessage);
	}

	@PostMapping("/encryption/secreteKey")
	public String getEncryptedResponse(@RequestBody User user, @RequestParam String secreteKey) {
		ObjectMapper Obj = new ObjectMapper();
		String jsonStr;
		String encryptedJson = null;
		try {
			jsonStr = Obj.writeValueAsString(user);
			encryptedJson = service.getEncryption(secreteKey, jsonStr);
			logger.info("The encryped json is:" + encryptedJson);

		} catch (JsonProcessingException e) {
			logger.info("The Exception is:" + e.getMessage());

		}
		return encryptedJson;

	}

	@PostMapping("/decryption/secreteKey")
	public String getEncryptedResponse(@RequestBody String request, @RequestParam String secreteKey) {
		return Encryption.decrypt(secreteKey, request);

	}

}
