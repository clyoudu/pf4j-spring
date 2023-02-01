package github.clyoudu.pf4jspring.core;

import org.pf4j.DefaultPluginManager;
import org.pf4j.ExtensionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.util.List;

/**
 * @author leichen
 */
public class SpringPluginManager extends DefaultPluginManager implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public SpringPluginManager() {
        super();
    }

    public SpringPluginManager(Path... pluginsRoots) {
        super(pluginsRoots);
    }

    public SpringPluginManager(List<Path> pluginsRoots) {
        super(pluginsRoots);
    }

    @Override
    protected ExtensionFactory createExtensionFactory() {
        return new SpringExtensionFactory(this);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * This method load, start plugins and inject extensions in Spring
     */
    @PostConstruct
    public void init() {
        loadPlugins();
        startPlugins();

        AbstractAutowireCapableBeanFactory beanFactory = (AbstractAutowireCapableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        ExtensionsInjector extensionsInjector = new ExtensionsInjector(this, beanFactory);
        extensionsInjector.injectExtensions();
    }
}