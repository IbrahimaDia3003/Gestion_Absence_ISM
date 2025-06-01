package sn.ism.gestion.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {
    @GetMapping({ "/", "/{path:[^\\.]*}" })
    public String forward() {
        return "forward:/browser/index.html";
    }
}
