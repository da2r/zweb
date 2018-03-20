package com.cpssoft.dev.zweb.action;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

public class LoginAction extends BaseAction {

	@Action(path = "login")
	public void login() {
		writeResponsePage();
	}

	@Action(path = "login-submit")
	public void loginSubmit() {
		// System.out.println(request.getMethod());
		// System.out.println(request.getRequestURI());
		// System.out.println(request.getParameter("id"));
		
		HttpSession session = request.getSession();

		Long counter = (Long) session.getAttribute("counter");
		if (counter == null)
			counter = 0L;
		counter++;

		session.setAttribute("counter", counter);

		JsonObject resp = new JsonObject();
		resp.addProperty("s", true);

		JsonObject payload = new JsonObject();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		payload.addProperty("current", now.format(formatter));

		resp.add("d", payload);

		writeResponse(resp);
	}

	@Action(path = "login-redirect")
	public void loginRedirect() {
		sendRedirect("/static/favicon.ico");
	}

	@Override
	protected boolean isRequireLoggedIn() {
		return false;
	}

}
