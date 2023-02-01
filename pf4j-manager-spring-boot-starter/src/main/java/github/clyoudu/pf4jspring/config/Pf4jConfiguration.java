package github.clyoudu.pf4jspring.config;

import github.clyoudu.pf4jspring.core.SpringPluginManager;
import org.pf4j.PluginManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Paths;

/**
 * @author leichen
 */
@Configuration
@EnableConfigurationProperties(Pf4jManagerProperties.class)
@ConditionalOnProperty(value = "spring.pf4j.enabled", havingValue = "true", matchIfMissing = true)
public class Pf4jConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PluginManager pluginManager(Pf4jManagerProperties pf4jManagerProperties) {
        return new SpringPluginManager(Paths.get(pf4jManagerProperties.getPath()));
    }

}
