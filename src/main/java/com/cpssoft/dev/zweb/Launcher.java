package com.cpssoft.dev.zweb;

import java.io.File;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.reflections.util.ClasspathHelper;

import com.cpssoft.dev.zweb.orm.BrandEntity;
import com.cpssoft.dev.zweb.orm.CarEntity;
import com.cpssoft.dev.zweb.type.CarType;

public class Launcher {

	private Server server;

	public Launcher() {

	}

	public void testDatabase() {
		String dbpath = "/Users/herman/Documents/AppData/zweb/zwebdb";
		HibernateUtil.initSessionFactory(dbpath, null, null);
		Session session = HibernateUtil.openSession();
		try {
			Transaction tx = session.getTransaction();
			tx.begin();

			BrandEntity testing = new BrandEntity();
			testing.setName("testing");
			session.save(testing);

			CarEntity car = new CarEntity();
			car.setName("abc");
			car.setPrice(20L);
			car.setBrand(testing);
			car.setType(CarType.SEDAN);
			session.save(car);

			Query<CarEntity> query = session.createQuery("from CarEntity", CarEntity.class);
			List<CarEntity> list = query.getResultList();

			System.out.println(list.size());
			System.out.println(list);

			Query<Long> sum = session.createQuery("select sum(price) from CarEntity", Long.class);
			System.out.println("SUM : " + sum.uniqueResult());

			tx.commit();
		} finally {
			session.close();
			HibernateUtil.shutdown();
		}
	}

	public void startWebServer() throws Exception {
		server = new Server(8888);

		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setResourceBase(getClassPath() + "/static");

		ContextHandler resourceContext = new ContextHandler();
		resourceContext.setContextPath("/static/*");
		resourceContext.setHandler(resourceHandler);

		ServletContextHandler servletContext = new ServletContextHandler();
		servletContext.setContextPath("/");
		servletContext.addServlet(new ServletHolder(new ServletManager()), "/*");

		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers(new Handler[] { resourceContext, servletContext });

		server.setHandler(contexts);
		server.start();

		// The use of server.join() the will make the current thread join and
		// wait until the server is done executing.
		// See
		// http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#join()
		server.join();

		// server.stop();

		// if (Desktop.isDesktopSupported() &&
		// Desktop.getDesktop().isSupported(Action.BROWSE)) {
		// Desktop.getDesktop().browse(new URI("http://127.0.0.1:8888"));
		// }
	}

	private String getClassPath() {
		URL url = ClasspathHelper.forJavaClassPath().iterator().next();
		return url.toString();
	}

	public void printNetAddress() throws UnknownHostException, SocketException {
		InetAddress localhost = InetAddress.getLocalHost();
		System.out.println(localhost.getHostName());
		System.out.println(localhost.getHostAddress());
		System.out.println("===========");

		Enumeration<NetworkInterface> net = NetworkInterface.getNetworkInterfaces();
		while (net.hasMoreElements()) {
			NetworkInterface n = net.nextElement();
			System.out.println(n.getName());
			System.out.println(n.getDisplayName());
			System.out.println(n.getIndex());
			System.out.println(n.isUp());
			System.out.println(n.isVirtual());
			System.out.println(n.isLoopback());
			System.out.println(n.isPointToPoint());
			if (n.isUp()) {
				Enumeration<InetAddress> addresses = n.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress a = addresses.nextElement();
					System.out.println(a.getHostAddress());
					System.out.println(a.getHostName());
				}
			}

			System.out.println("------------");
		}
	}

	public void testVelocity() {
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.setProperty("resource.loader", "classpath");
		velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

		velocityEngine.init();

		File f = new File(".");
		System.out.println("working path: " + f.getAbsolutePath());

		System.out.println("velocity prop: ");
		System.out.println(velocityEngine.getProperty("resource.loader"));
		System.out.println(velocityEngine.getProperty("file.resource.loader.path"));
		System.out.println(velocityEngine.getProperty("class.resource.loader.class"));

		Template t = velocityEngine.getTemplate("templates/index.vm");

		VelocityContext context = new VelocityContext();
		context.put("name", "World");
		// context.put("StringUtil", new StringUtil());

		StringWriter writer = new StringWriter();
		t.merge(context, writer);

		String rendered = writer.toString();
		System.out.println(rendered);
	}

	public static void main(String[] args) {
		System.out.println(Arrays.asList(args));

		System.out.println("Start Launcher");

		try {
			System.out.println("Java Class Path: " + ClasspathHelper.forJavaClassPath());

			Launcher launcher = new Launcher();
			// launcher.testDatabase();
			// launcher.printNetAddress();
			// launcher.testVelocity();
			launcher.startWebServer();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		System.out.println("End Launcher");
	}

}
