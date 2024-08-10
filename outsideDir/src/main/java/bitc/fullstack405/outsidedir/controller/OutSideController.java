package bitc.fullstack405.outsidedir.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/outside")
public class OutSideController {

    @GetMapping({"/", ""})
    public String index() {
        return "index";
    }

    @GetMapping("/imgLoad")
    public String imgLoad() {
        return "outside/imgLoad";
    }

}