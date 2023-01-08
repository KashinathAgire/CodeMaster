package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class ResponseBody {
private String description;
private String statusCode;
private String message;

public ResponseBody() {
	super();
	// TODO Auto-generated constructor stub
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getStatusCode() {
	return statusCode;
}
public void setStatusCode(String statusCode) {
	this.statusCode = statusCode;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
@Override
public String toString() {
	return "ResponseBody [description=" + description + ", statusCode=" + statusCode + ", message=" + message + "]";
}




}
