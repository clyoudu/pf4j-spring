package github.clyoudu.pf4jspring.core;

import org.pf4j.Plugin;
import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;

/**
 * @author leichen
 */
public abstract class SpringPlugin extends Plugin {

    private ApplicationContext applicationContext;

    public SpringPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    public final ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            applicationContext = createApplicationContext();
        }

        return applicationContext;
    }

    @Override
    public void stop() {
        String pluginClass = getWrapper().getDescriptor().getPluginClass();
        if (applicationContext.containsBean(pluginClass)) {
            ((DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory()).destroySingleton(pluginClass);
        } else {
            log.debug("No plugin bean found with class '{}'", pluginClass);
        }

    }

    @Override
    public void start() {
        getApplicationContext();
        String pluginClass = getWrapper().getDescriptor().getPluginClass();
        if (!applicationContext.containsBean(pluginClass)) {
            ((DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory()).destroySingleton(pluginClass);
        }
    }

    /**
     * spring ApplicationContext
     *
     * @return ApplicationContext
     */
    protected ApplicationContext createApplicationContext() {
        PluginManager pluginManager = getWrapper().getPluginManager();
        if (pluginManager instanceof SpringPluginManager) {
            return ((SpringPluginManager) pluginManager).getApplicationContext();
        }
        return null;
    }

}
