package com.ityun.web.controller.site;

import com.ityun.web.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController extends BaseController {
    @RequestMapping(value = {"/", "/index"})
    public String home() {
        return "string";
    }
}
