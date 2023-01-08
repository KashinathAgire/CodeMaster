package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@org.springframework.stereotype.Service
public class Service {
	private static final Logger logger = LoggerFactory.getLogger(Service.class);

	public String getDecodeEncode(String encode) {

		String serviceResponse = null;

		logger.info(
				"###################################Inside getDecodeEncode {} ##########################################:");

		logger.info("Request Body is:" + encode);

		logger.info("Encryption/Decryption key is:" + Constants.Key_ED);

		try {
			String decrypedString = Encryption.decrypt(Constants.Key_ED, encode);
			logger.info("Decrypted Message : " + new String(decrypedString));
			serviceResponse = getResponse(decrypedString);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return serviceResponse;
	}

	public String getEncryption(String secreteKey, String JsonData) {
		return Encryption.encrypt(secreteKey, JsonData);
	}

	public String getResponse(String decrypedString) {
		logger.info(
				"###################################Inside getResponse {} ##########################################:");

		ObjectMapper Obj = new ObjectMapper();
		ResponseBody responseBody = new ResponseBody();
		logger.info("Request Body is:" + decrypedString);

		String jsonStr = null;
		try {
			User user = Obj.readValue(decrypedString, User.class);
			responseBody.setMessage("Hi" + user.getMessage());
			responseBody.setStatusCode("200");
			responseBody.setDescription("Message Encrypted SuccessFully!!!!");

			jsonStr = Obj.writeValueAsString(responseBody);
			logger.info("Request Body is:" + jsonStr);

		} catch (JsonMappingException e) {
			logger.info("Exception occured while json Mapping is:" + e.getMessage());
		} catch (JsonProcessingException e) {
			logger.info("Exception occured while json proccesing is:" + e.getMessage());
		}
		return Encryption.encrypt(Constants.Key_ED, jsonStr);

	}

}
