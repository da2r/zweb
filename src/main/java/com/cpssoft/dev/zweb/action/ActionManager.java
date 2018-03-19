package com.cpssoft.dev.zweb.action;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class ActionManager {

	public static class Entry {
		public String path;
		public Class<? extends BaseAction> clazz;
		public Method method;
	}

	public static Map<String, Entry> entryMap = null;

	public static Entry get(String path) {
		if (entryMap == null) {
			init();
		}

		Entry result = entryMap.get(path);

		return result;
	}

	public static synchronized void init() {
		if (entryMap == null) {
			entryMap = new HashMap<String, Entry>();

			Set<Class<? extends BaseAction>> classes = getActionClassSet();
			for (Class<? extends BaseAction> clazz : classes) {
				for (Method method : clazz.getMethods()) {
					if (method.isAnnotationPresent(Action.class)) {
						Action action = method.getAnnotation(Action.class);
						String path = action.path();
						
						Entry entry = new Entry();
						entry.path = path;
						entry.clazz = clazz;
						entry.method = method;
						entryMap.put(path, entry);
					}
				}
			}
		}
	}

	private static Set<Class<? extends BaseAction>> getActionClassSet() {
		String basePackage = ActionManager.class.getPackage().getName();

		ConfigurationBuilder config = new ConfigurationBuilder()
				.setScanners(new TypeAnnotationsScanner(), new SubTypesScanner())
				.setUrls(ClasspathHelper.forJavaClassPath())
				.filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(basePackage)));

		Reflections reflections = new Reflections(config);

		return reflections.getSubTypesOf(BaseAction.class);
	}
}
