package app.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.model.pojo.ProductEntity;
import app.service.ProductService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping(value = "/admin/products")
@RestController
public class AdminProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/all-products")
    public List<ProductEntity> getAllProducts() {
        List<ProductEntity> allProducts = productService.getAllProducts();

        return allProducts;
    }

    @GetMapping("/items/{id}")
    public ProductEntity getSpecificProduct(@PathVariable UUID id) {
        return productService.findById(id.toString());
    }

    @PostMapping("/add")
    public String addNewProduct(@RequestBody String entity) {
        return entity;
    }

    @PatchMapping("/edit/{id}")
    public String editProducts(@PathVariable String id) {
        return null;
    }

    @DeleteMapping("/remove/{id}")
    public String deleteProducts(@PathVariable String id) {
        return null;
    }

}
