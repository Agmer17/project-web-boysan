package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.service.ImageProductService;

@Controller
@RestController
@RequestMapping("/admin/service-image")
public class ImageProductController {

    @Autowired
    private ImageProductService service;

}
