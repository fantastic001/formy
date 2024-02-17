package org.psw_isa.psw_isa_backend.controller;

import javax.servlet.http.HttpServletRequest;

import org.psw_isa.psw_isa_backend.plugins.PluginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/plugins")
public class PluginController {

    @Autowired
    private PluginService pluginService;

    @GetMapping("/{pluginName}/api/**")
    public ResponseEntity<?> handlePluginRequest(@PathVariable String pluginName, HttpServletRequest request) {
        return pluginService.dispatchRequest(pluginName, request);
    }

    @PostMapping("/{pluginName}/api/**")
    public ResponseEntity<?> handlePluginPostRequest(@PathVariable String pluginName, HttpServletRequest request) {
        return pluginService.dispatchRequest(pluginName, request);
    }

    @GetMapping("/{pluginName}/widgets/{widgetName}")
    public ResponseEntity<?> handlePluginWidgetRequest(@PathVariable String pluginName, @PathVariable String widgetName, HttpServletRequest request) {
        String code =  pluginService.getVueComponentCode(pluginName, widgetName);
        return ResponseEntity.ok(code);
    }
}
