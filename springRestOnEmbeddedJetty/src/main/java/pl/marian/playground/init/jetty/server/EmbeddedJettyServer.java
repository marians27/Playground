package pl.marian.playground.init.jetty.server;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.annotations.AnnotationConfiguration.ClassInheritanceMap;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.web.WebApplicationInitializer;
import pl.marian.playground.init.WebAppInitializer;

public class EmbeddedJettyServer {

    private static final int DEFAULT_JETTY_PORT = 8080;
    private final Server server;

    public EmbeddedJettyServer(Integer port, String name) {
        this.server = new Server(port == null ? DEFAULT_JETTY_PORT : port);
        Handler contextHandler = createServletContextHandler(name);
        server.setHandler(contextHandler);
    }

    public void runEmbeddedJettyAndJoin() throws Exception {
        runEmbeddedJetty();
        server.join();
    }

    public void runEmbeddedJetty() throws Exception {
        server.start();
    }

    public void stopEmbeddedJetty() throws Exception {
        server.stop();
    }

    private ServletContextHandler createServletContextHandler(String name) {
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/" + name);
        webAppContext.setAttribute(AnnotationConfiguration.CLASS_INHERITANCE_MAP, createClassMap());
        webAppContext.setConfigurations(new Configuration[]{new AnnotationConfiguration()});
        return webAppContext;
    }

    private ClassInheritanceMap createClassMap() {
        ClassInheritanceMap classMap = new ClassInheritanceMap();
        ConcurrentHashSet<String> impl = new ConcurrentHashSet<>();
        impl.add(WebAppInitializer.class.getName());
        classMap.put(WebApplicationInitializer.class.getName(), impl);
        return classMap;
    }
}
