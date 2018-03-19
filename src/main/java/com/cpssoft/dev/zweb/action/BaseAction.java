package com.cpssoft.dev.zweb.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;

import com.cpssoft.dev.zweb.util.VelocityUtil;
import com.google.gson.JsonObject;

public class BaseAction {

	public static final String DEFAULT_LAYOUT = "default";
	public static final String NO_LAYOUT = "";

	public HttpServletRequest request;
	public HttpServletResponse response;

	public VelocityContext context;

	public void prepare(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;

		this.context = new VelocityContext();
	}

	protected void writeResponsePage() {
		String path = request.getRequestURI().substring(1);
		if (path.isEmpty()) {
			path = "index";
		}

		writeHtmlResponse(DEFAULT_LAYOUT, path);
	}

	protected void writeHtmlResponse(String layout, String path) {
		String content = VelocityUtil.renderContent(path, context);
		
		if (layout == null || layout.equals(NO_LAYOUT)) {
			content = VelocityUtil.renderLayout(layout, content, context);
		}

		System.out.println("aa");
		System.out.println(content);
		
		writeResponse(content);
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

	protected void sendRedirect(String location) {
		try {
			response.sendRedirect(location);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
