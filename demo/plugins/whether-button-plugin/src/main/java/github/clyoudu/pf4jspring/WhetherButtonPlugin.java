package github.clyoudu.pf4jspring;

import github.clyoudu.pf4jspring.api.OperationButtonExtension;
import github.clyoudu.pf4jspring.core.SpringPlugin;
import org.pf4j.Extension;
import org.pf4j.PluginWrapper;

import java.util.Random;

/**
 * @author leichen
 */
public class WhetherButtonPlugin extends SpringPlugin {

    private static final String[] WHETHER = new String[]{"Sunny", "Cloudy", "Raining", "Windy", "Snow"};

    public WhetherButtonPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class WhetherButtonExtension implements OperationButtonExtension {
        @Override
        public String name() {
            return "Whether";
        }

        @Override
        public String onClick() {
            return WHETHER[new Random().nextInt(WHETHER.length)];
        }
    }
}
