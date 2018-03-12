package com.cpssoft.dev.zweb;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class HibernateUtil {

	private HibernateUtil() {

	}

	private static SessionFactory sessionFactory = null;

	public synchronized static void initSessionFactory(String dbpath, String user, String pass)
			throws ExceptionInInitializerError {
		if (sessionFactory != null) {
			throw new RuntimeException("sessionFactory already initialized");
		}

		try {
			StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
			registryBuilder.applySettings(createSetting(dbpath, user, pass));

			ServiceRegistry registry = registryBuilder.build();

			MetadataSources metadataSources = new MetadataSources(registry);
			for (Class<?> annotatedClass : getAnnotatedEntityClass()) {
				metadataSources.addAnnotatedClass(annotatedClass);
			}

			sessionFactory = metadataSources.buildMetadata().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed: " + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static Session openSession() {
		if (sessionFactory == null) {
			throw new RuntimeException("sessionFactory not initialized");
		}

		return sessionFactory.openSession();
	}

	private static Map<String, Object> createSetting(String dbpath, String user, String pass) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Environment.DRIVER, "org.h2.Driver");
		result.put(Environment.URL, "jdbc:h2:" + dbpath);
		result.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
		// result.put(Environment.DIALECT, "com.cpssoft.dev.zweb.CustomH2Dialect");
		result.put(Environment.SHOW_SQL, true);
		result.put(Environment.FORMAT_SQL, true);
		result.put(Environment.HBM2DDL_AUTO, "update");
		result.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

		if (user != null && pass != null) {
			result.put(Environment.USER, user);
			result.put(Environment.PASS, pass);
		}

		result.put("hibernate.hikari.connectionTimeout", "20000");
		result.put("hibernate.hikari.minimumIdle", "10");
		result.put("hibernate.hikari.maximumPoolSize", "20");
		result.put("hibernate.hikari.idleTimeout", "300000");

		return result;
	}

	private static Collection<Class<?>> getAnnotatedEntityClass() {
		String basePackage = HibernateUtil.class.getPackage().getName();

		ConfigurationBuilder config = new ConfigurationBuilder()
				.setScanners(new TypeAnnotationsScanner(), new SubTypesScanner())
				.setUrls(ClasspathHelper.forJavaClassPath())
				.filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(basePackage)));

		Reflections reflections = new Reflections(config);

		return reflections.getTypesAnnotatedWith(Entity.class);
	}

	public static void shutdown() {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}
}
