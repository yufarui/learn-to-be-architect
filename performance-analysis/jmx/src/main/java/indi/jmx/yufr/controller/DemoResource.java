package indi.jmx.yufr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date: 2020/12/22 10:53
 * @author: farui.yu
 */
@RestController
public class DemoResource {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
