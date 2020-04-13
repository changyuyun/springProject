package com.ityun.web.controller.site;

import com.ityun.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController {
    @RequestMapping(value = {"/", "/index"})
    public String home() {
        return "templates/classic/index";
    }
}
