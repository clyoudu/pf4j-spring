package github.clyoudu.pf4jspring.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import github.clyoudu.pf4jspring.api.OperationButtonExtension;
import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author leichen
 */
@RestController
@RequestMapping("ext")
public class ExtensionManagerController {

    private PluginManager pluginManager;

    @Autowired
    public void setPluginManager(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    @GetMapping("list")
    public JSONArray extensions(@RequestParam String extensionClass) throws ClassNotFoundException {
        List extensions = pluginManager.getExtensions(Class.forName(extensionClass));
        JSONArray result = new JSONArray();
        for (Object extension : extensions) {
            if (extension instanceof OperationButtonExtension) {
                PluginWrapper plugin = pluginManager.whichPlugin(extension.getClass());
                result.add(new JSONObject().fluentPut("class", extension.getClass().getName())
                        .fluentPut("name", ((OperationButtonExtension) extension).name()).fluentPut("plugin", new JSONObject()
                                .fluentPut("id", plugin.getPluginId())
                                .fluentPut("version", plugin.getDescriptor().getVersion())
                                .fluentPut("path", plugin.getPluginPath().toString())
                                .fluentPut("state", plugin.getPluginState())
                                .fluentPut("class", plugin.getDescriptor().getPluginClass())));
            }
        }
        return result;
    }

    @GetMapping("click")
    public String click(@RequestParam String extensionClass, @RequestParam String name) throws ClassNotFoundException {
        List extensions = pluginManager.getExtensions(Class.forName(extensionClass));
        for (Object extension : extensions) {
            if (extension instanceof OperationButtonExtension && ((OperationButtonExtension) extension).name().equals(name)) {
                return ((OperationButtonExtension) extension).onClick();
            }
        }
        return "Extension not found or unavailable";
    }
}
