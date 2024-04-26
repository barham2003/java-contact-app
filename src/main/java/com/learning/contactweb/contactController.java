package com.learning.contactweb;


import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class contactController {

    @RequestMapping(value = "/contacts")
    @ResponseBody
    public String contacts(Model model) {
        return "contacts";
    }
}
