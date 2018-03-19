package com.cpssoft.dev.zweb.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.ParseException;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class VelocityUtil {

	public static VelocityEngine velocityEngine;

	static {
		velocityEngine = new VelocityEngine();
		velocityEngine.setProperty("resource.loader", "classpath");
		velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		velocityEngine.init();
	}

	public static String renderContent(String contentFile, VelocityContext context) {
		Template t = velocityEngine.getTemplate("templates/content/" + contentFile + ".vm");
		StringWriter writer = new StringWriter();
		t.merge(context, writer);

		return writer.toString();
	}
	
	public static String renderLayout(String layoutFile, String content, VelocityContext context) {
		Template t = velocityEngine.getTemplate("templates/layout/" + layoutFile + ".vm");
		StringWriter writer = new StringWriter();
		
		VelocityContext layoutContext = new VelocityContext(context);
		layoutContext.put("body", content);
		
		t.merge(layoutContext, writer);

		return writer.toString();
	}

	@Deprecated
	public static Template renderTemplate(String content) throws ParseException {
		RuntimeServices runtimeServices = RuntimeSingleton.getRuntimeServices();
		StringReader reader = new StringReader(content);
		SimpleNode node = runtimeServices.parse(reader, "Template name");
		Template template = new Template();
		template.setRuntimeServices(runtimeServices);
		template.setData(node);
		template.initDocument();

		return template;
	}

	@Deprecated
	public static String mergeTemplate(Template template, Map<String, Object> conf) throws ParseException,
			ResourceNotFoundException, ParseErrorException, MethodInvocationException, IOException {
		StringWriter writer = new StringWriter();
		VelocityContext context = new VelocityContext();
		context.put("str", new StringUtil());
		for (Object key : conf.keySet()) {
			context.put((String) key, conf.get((String) key));
		}

		template.merge(context, writer);
		return writer.toString();
	}
}
