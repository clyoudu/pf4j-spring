package github.clyoudu.pf4jspring;

import github.clyoudu.pf4jspring.api.OperationButtonExtension;
import github.clyoudu.pf4jspring.core.SpringPlugin;
import org.pf4j.Extension;
import org.pf4j.PluginWrapper;

/**
 * @author leichen
 */
public class SystemButtonPlugin extends SpringPlugin {

    public SystemButtonPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class SystemButtonExtension implements OperationButtonExtension {

        @Override
        public String name() {
            return "System";
        }

        @Override
        public String onClick() {
            return System.getProperty("os.name") + "-" + System.getProperty("os.arch");
        }
    }
}
