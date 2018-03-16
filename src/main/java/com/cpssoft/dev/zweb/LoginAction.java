package com.cpssoft.dev.zweb;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

public class LoginAction {

	public HttpServletRequest request;
	public HttpServletResponse response;

	public void login() {
		writeResponse("Hello");
	}

	public void loginSubmit() {
		System.out.println(request.getMethod());
		System.out.println(request.getRequestURI());
		System.out.println(request.getParameter("id"));

		JsonObject resp = new JsonObject();
		resp.addProperty("s", true);

		JsonObject payload = new JsonObject();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		payload.addProperty("current", now.format(formatter));

		resp.add("d", payload);

		writeResponse(resp);
	}
	
	protected void writeResponse(String html) {
		try {
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println(html);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected void writeResponse(JsonObject json) {
		try {
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println(json.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
