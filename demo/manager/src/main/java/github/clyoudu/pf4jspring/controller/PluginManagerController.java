package github.clyoudu.pf4jspring.controller;

import com.alibaba.fastjson2.JSONObject;
import github.clyoudu.pf4jspring.config.Pf4jManagerProperties;
import org.pf4j.PluginManager;
import org.pf4j.PluginState;
import org.pf4j.PluginWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author leichen
 */
@RestController
@RequestMapping("plugin")
public class PluginManagerController {

    private Pf4jManagerProperties pf4jManagerProperties;

    private PluginManager pluginManager;

    @Autowired
    public void setPf4jManagerProperties(Pf4jManagerProperties pf4jManagerProperties) {
        this.pf4jManagerProperties = pf4jManagerProperties;
    }

    @Autowired
    public void setPluginManager(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    @PostMapping("upload")
    public boolean uploadPlugin(@RequestParam("file") MultipartFile file) throws IOException {
        String path = pf4jManagerProperties.getPath();
        Files.copy(file.getInputStream(), Paths.get(path).resolve(file.getOriginalFilename()));
        return true;
    }

    @GetMapping("reload")
    public void reloadPlugins() {
        pluginManager.unloadPlugins();
        pluginManager.loadPlugins();
        pluginManager.startPlugins();
    }

    @GetMapping("load")
    public String loadPlugin(@RequestParam() String pluginId) {
        PluginWrapper plugin = pluginManager.getPlugin(pluginId);
        if (plugin != null) {
            return pluginManager.loadPlugin(plugin.getPluginPath());
        } else {
            return "Plugin not found";
        }
    }

    @GetMapping("unload")
    public boolean unloadPlugin(@RequestParam() String pluginId) {
        return pluginManager.unloadPlugin(pluginId);
    }

    @GetMapping("start")
    public PluginState startPlugin(@RequestParam() String pluginId) {
        return pluginManager.startPlugin(pluginId);
    }

    @GetMapping("stop")
    public PluginState stopPlugin(@RequestParam() String pluginId) {
        return pluginManager.stopPlugin(pluginId);
    }

    @GetMapping("disable")
    public boolean disablePlugin(@RequestParam() String pluginId) {
        return pluginManager.disablePlugin(pluginId);
    }

    @GetMapping("enable")
    public boolean enablePlugin(@RequestParam() String pluginId) {
        return pluginManager.enablePlugin(pluginId);
    }

    @GetMapping("delete")
    public boolean deletePlugin(@RequestParam() String pluginId) {
        return pluginManager.deletePlugin(pluginId);
    }

    @GetMapping("list")
    public List<JSONObject> listPlugin() {
        return getPlugins();
    }

    private List<JSONObject> getPlugins() {
        ArrayList<JSONObject> list = new ArrayList<>();
        List<PluginWrapper> plugins = pluginManager.getPlugins();
        for (PluginWrapper plugin : plugins) {
            list.add(new JSONObject()
                    .fluentPut("id", plugin.getPluginId())
                    .fluentPut("version", plugin.getDescriptor().getVersion())
                    .fluentPut("path", plugin.getPluginPath().toString())
                    .fluentPut("state", plugin.getPluginState())
                    .fluentPut("class", plugin.getDescriptor().getPluginClass())
            );
        }
        return list;
    }

}
