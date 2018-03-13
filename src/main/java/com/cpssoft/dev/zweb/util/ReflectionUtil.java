package com.cpssoft.dev.zweb.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class ReflectionUtil {


	public static Class<?> getGenericClass(Class<?> genericClass, int index) {
		Type genericType = genericClass.getGenericSuperclass();

		if (!(genericType instanceof ParameterizedType))
			throw new RuntimeException("The class does not have generic type: " + genericClass
					+ ", Type: " + genericType);
		ParameterizedType parameterizedType = (ParameterizedType) genericType;

		return (Class<?>) parameterizedType.getActualTypeArguments()[index];
	}

	public static boolean isGetter(Method method) {
		if (!method.getName().startsWith("get") && !method.getName().startsWith("is"))
			return false;
		if (method.getParameterTypes().length != 0)
			return false;
		if (void.class.equals(method.getReturnType()))
			return false;
		return true;
	}

	public static boolean isSetter(Method method) {
		if (!method.getName().startsWith("set"))
			return false;
		if (method.getParameterTypes().length != 1)
			return false;
		return true;
	}

	public static String getFieldNameFromGetter(Method getter) {
		String result = "";
		if (getter.getName().startsWith("get"))
			result = getter.getName().substring(3, 4).toLowerCase() + getter.getName().substring(4);
		else
			result = getter.getName().substring(2, 3).toLowerCase() + getter.getName().substring(3);
		return result;
	}

	public static String getFieldNameFromSetter(Method setter) {
		String result = "";
		if (setter.getName().startsWith("set"))
			result = setter.getName().substring(3, 4).toLowerCase() + setter.getName().substring(4);
		return result;
	}

	private static List<Field> popuplateFieldList(List<Field> fields, Class<?> type) {
		if (type.getSuperclass() != null) {
			fields = popuplateFieldList(fields, type.getSuperclass());
		}

		for (Field field : type.getDeclaredFields()) {
			fields.add(field);
		}

		return fields;
	}

	public static List<Field> getAllFields(Class<?> type) {
		List<Field> fieldList = new LinkedList<Field>();
		fieldList = popuplateFieldList(fieldList, type);

		return fieldList;
	}

	public static Field getField(Class<?> type, String name) {
		return getField(type, name, true);
	}

	public static List<Field> getFieldsAnnotatedWith(Class<?> type,
			Class<? extends Annotation> annotation, boolean recursive) {
		final List<Field> fields = new ArrayList<Field>();

		for (Field field : type.getDeclaredFields()) {
			if (annotation == null || field.isAnnotationPresent(annotation)) {
				fields.add(field);
			}
		}

		if (recursive) {
			Class<?> superClass = type.getSuperclass();
			if (superClass != null) {
				// System.out.println("TYPE: " + type.getName() + "SUPER: " +
				// superClass.getName());
				if (!superClass.equals(Object.class)) {
					fields.addAll(getFieldsAnnotatedWith(superClass, annotation, true));
				}
			}
		}

		return fields;

	}

	public static List<Class<?>> getClassSubTypeOf(String[] basePackage, Class<?> parentClass) {
		List<Class<?>> result = new ArrayList<Class<?>>();
		Set<URL> urlList = new HashSet<URL>();

		for (String packageStr : basePackage) {
			urlList.addAll(ClasspathHelper.forPackage(packageStr));
		}

		Reflections reflections = new Reflections(new ConfigurationBuilder().addUrls(urlList)
				.setScanners(new ResourcesScanner(), new TypeAnnotationsScanner(),
						new SubTypesScanner()));
		Set<?> classes = reflections.getSubTypesOf(parentClass);

		for (Object controller : classes) {
			result.add((Class<?>) controller);
		}

		return result;
	}

	public static List<Class<?>> getClassAnnotatedWith(String[] basePackage,
			Class<? extends Annotation> annotation) {
		List<Class<?>> result = new ArrayList<Class<?>>();
		FilterBuilder filter = new FilterBuilder();

		for (String packageStr : basePackage) {
			filter.include(FilterBuilder.prefix(packageStr));
		}

		ConfigurationBuilder config = new ConfigurationBuilder()
				.setScanners(new ResourcesScanner(), new TypeAnnotationsScanner(),
						new SubTypesScanner()).setUrls(ClasspathHelper.forPackage(""))
				.filterInputsBy(filter);
		Reflections reflections = new Reflections(config);
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(annotation);

		for (Class<?> controller : annotated) {
			result.add(controller);
		}

		return result;
	}

	@SuppressWarnings("rawtypes")
	public static Method getMethod(Class<?> type, boolean recursive, String methodName,
			Class... args) {
		Method result = null;

		try {
			result = type.getDeclaredMethod(methodName, args);
		} catch (Throwable t) {
			if (recursive) {
				Class<?> superClass = type.getSuperclass();
				// System.out.println("TYPE: " + type.getName() + "SUPER: " +
				// superClass.getName());
				if (!superClass.equals(Object.class)) {
					result = getMethod(superClass, true, methodName, args);
				}
			}
		}

		return result;
	}

	public static List<Method> getMethodList(Class<?> type, boolean recursive) {
		final List<Method> methods = new ArrayList<Method>();
		final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(type
				.getDeclaredMethods()));
		for (final Method method : allMethods) {
			methods.add(method);
		}

		if (recursive) {
			Class<?> superClass = type.getSuperclass();
			if (!superClass.equals(Object.class)) {
				methods.addAll(getMethodList(superClass, true));
			}
		}

		return methods;
	}

	public static List<Method> getMethodsAnnotatedWith(Class<?> type,
			Class<? extends Annotation> annotation, boolean recursive) {
		final List<Method> methods = new ArrayList<Method>();

		if (recursive) {
			Class<?> superClass = type.getSuperclass();
			if (superClass != null) {
				// System.out.println("TYPE: " + type.getName() + "SUPER: " +
				// superClass.getName());
				if (!superClass.equals(Object.class)) {
					methods.addAll(getMethodsAnnotatedWith(superClass, annotation, true));
				}
			}
		}

		final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(type
				.getDeclaredMethods()));
		for (final Method method : allMethods) {
			if (annotation == null || method.isAnnotationPresent(annotation)) {
				// Annotation annotInstance = method.getAnnotation(annotation);
				// TODO process annotInstance
				methods.add(method);
			}
		}

		return methods;
	}

	public static Field getField(Class<?> type, String fieldName, boolean recursive) {
		if (type == null || fieldName == null || fieldName.isEmpty()) {
			return null;
		}

		Field result = null;
		try {
			result = type.getDeclaredField(fieldName);
		} catch (Exception e) {
		}

		if (result == null && recursive) {
			Class<?> superClass = type.getSuperclass();
			if (superClass != null && (superClass.equals(Object.class) == false)) {
				result = getField(superClass, fieldName, true);
			}
		}

		return result;
	}
}
