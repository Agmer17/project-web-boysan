package app.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.model.pojo.ImageProduct;
import app.service.ImageProductService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RestController
@RequestMapping("/admin/service-image")
public class ImageProductController {

    @Autowired
    private ImageProductService service;

    @GetMapping("/get/{serviceId}")
    public ResponseEntity<List<ImageProduct>> getByServiceId(@RequestParam UUID serviceId) {

        return ResponseEntity.ok().body(service.findByServiceId(serviceId));
    }

    @PatchMapping("/add")
    public ResponseEntity<List<ImageProduct>> postImageForProduct() {
        return null;
    }

    @PatchMapping("/edit/{imageId}")
    public ImageProduct editImage(@RequestParam ImageProduct editedImage) {
        return null;
    }

    @DeleteMapping("/delete")
    public void deleteImage(@RequestParam UUID imageID) {
        service.removeImage(imageID);
    }

}
