package app.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import app.model.common.CommonProductService;
import app.model.dto.NewServiceProduct;
import app.model.pojo.BaseServiceProduct;
import app.model.pojo.ProductEntity;
import app.service.ProductService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping(value = "/admin/products")
public class AdminProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String getProductPage(@SessionAttribute(required = true) Claims claims, Model model) {
        String adminUsername = claims.get("username", String.class);

        model.addAttribute("currentAdmin", adminUsername);

        return "NewProduct";
    }

    @GetMapping("/all-products")
    @ResponseBody
    public List<ProductEntity> getAllProducts() {
        List<ProductEntity> allProducts = productService.getAllProducts();

        return allProducts;
    }

    @GetMapping("/items/{id}")
    @ResponseBody
    public ProductEntity getSpecificProduct(@PathVariable UUID id) {
        return productService.findById(id.toString());
    }

    @PostMapping("/add")
    @ResponseBody
    public BaseServiceProduct addNewProduct(@Valid @ModelAttribute NewServiceProduct newProduct) throws IOException {
        return productService.addNewProduct(newProduct);
    }

    @PatchMapping("/edit/{id}")
    @ResponseBody
    // *ini buat ngedit productnya aja, untuk edit gambar ataupun categorynya pake
    // di controller masing masing*/
    public String editProducts(@ModelAttribute CommonProductService request) {
        return null;
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Object> deleteProducts(@RequestParam UUID serviceId) {
        productService.deleteProducts(serviceId);

        return ResponseEntity.status(204).body(null);
    }

}
