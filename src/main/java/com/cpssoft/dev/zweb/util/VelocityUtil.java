package com.cpssoft.dev.zweb.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.ParseException;
import org.apache.velocity.runtime.parser.node.SimpleNode;

public class VelocityUtil {
	static public Template renderTemplate(String content) throws ParseException {
		RuntimeServices runtimeServices = RuntimeSingleton.getRuntimeServices();
		StringReader reader = new StringReader(content);
		SimpleNode node = runtimeServices.parse(reader, "Template name");
		Template template = new Template();
		template.setRuntimeServices(runtimeServices);
		template.setData(node);
		template.initDocument();

		return template;
	}

	static public String mergeTemplate(Template template,
			Map<String, Object> conf) throws ParseException,
			ResourceNotFoundException, ParseErrorException,
			MethodInvocationException, IOException {
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
