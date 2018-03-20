package com.cpssoft.dev.zweb;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cpssoft.dev.zweb.action.ActionManager;
import com.cpssoft.dev.zweb.action.ActionManager.Entry;
import com.cpssoft.dev.zweb.action.BaseAction;
import com.cpssoft.dev.zweb.action.ValidateResult;
import com.cpssoft.dev.zweb.action.ValidateResultType;
import com.cpssoft.dev.zweb.util.SystemUtil;

public class ActionServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		execute(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		execute(request, response);
	}

	private void execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		System.out.println(request.getMethod() + " " + request.getRequestURI());

		String path = request.getRequestURI().substring(1);
		if (path.isEmpty()) {
			path = "index";
		}

		if ("favicon.ico".equals(path)) {
			writeFavicon(response);
		} else {

			Entry actionEntry = ActionManager.get(path);
			if (actionEntry == null) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			} else {
				try {
					BaseAction instance = actionEntry.clazz.newInstance();
					instance.prepare(request, response);

					ValidateResult validate = instance.validate();
					if (validate == null) {
						actionEntry.method.invoke(instance);
					} else {
						if (validate.type == ValidateResultType.ERROR) {
							throw new RuntimeException(validate.message);
						} else if (validate.type == ValidateResultType.REDIRECT) {
							String redirectTarget = (String) validate.data;
							response.sendRedirect(redirectTarget);
						} else {
							response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						}
					}

				} catch (InvocationTargetException e) {
					throw new RuntimeException(e);
				} catch (IllegalArgumentException e) {
					throw new RuntimeException(e);
				} catch (InstantiationException e) {
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}

	}

	private void writeFavicon(HttpServletResponse response) throws IOException {
		// TODO Cache bytes
		File file = new File(SystemUtil.getSourceFolder() + "/static/favicon.ico");
		byte[] bytes = Files.readAllBytes(file.toPath());

		response.setHeader("Content-length", "" + bytes.length);
		response.setHeader("Cache-Control", "public, max-age=31536000");
		response.setContentType("image/x-icon");
		response.getOutputStream().write(bytes);
	}

}
