package github.clyoudu.pf4jspring;

import github.clyoudu.pf4jspring.api.OperationButtonExtension;
import github.clyoudu.pf4jspring.core.SpringPlugin;
import org.pf4j.Extension;
import org.pf4j.PluginWrapper;

/**
 * @author leichen
 */
public class GreetingButtonPlugin extends SpringPlugin {

    public GreetingButtonPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class GreetingButtonExtension implements OperationButtonExtension {
        @Override
        public String name() {
            return "Greeting";
        }

        @Override
        public String onClick() {
            return "Hello Pf4j-spring!";
        }
    }

    @Extension
    public static class NewYearGreetingButtonExtension implements OperationButtonExtension {
        @Override
        public String name() {
            return "Year of Rabbit";
        }

        @Override
        public String onClick() {
            return "Happy Chinese New Year!";
        }
    }
}
