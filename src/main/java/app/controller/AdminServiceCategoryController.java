package app.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.model.entity.ServiceCategory;
import app.service.CategoryService;

@Controller
@RequestMapping("/admin/category")
@RestController
public class AdminServiceCategoryController {
    @Autowired
    private CategoryService service;

    @PostMapping("/add")
    public ServiceCategory addCategory(@RequestParam String name, @RequestParam String desc) {
        ServiceCategory newCategory = ServiceCategory.builder().name(name).description(desc).build();

        return service.createServiceCategory(newCategory);
    }

    @GetMapping("/findById")
    public ServiceCategory findById(@RequestParam UUID id) {
        return service.getServiceCategoryById(id).orElse(null);
    }

    @GetMapping("/all")
    public List<ServiceCategory> getAllCategories() {
        return service.getAllServiceCategories();
    }

    @GetMapping("/search")
    public List<ServiceCategory> findAllByName(@RequestParam String keyword) {
        return service.findAllServiceCategoryByName(keyword);
    }

    @PatchMapping("/update")
    public ServiceCategory update(@RequestParam UUID id, @RequestParam String name, @RequestParam String desc) {
        ServiceCategory editedData = ServiceCategory.builder().name(name).description(desc).build();
        return service.updateServiceCategory(id, editedData);
    }

    @DeleteMapping("/delete")
    public void deleteCategory(UUID id) {
        service.deleteServiceCategory(id);
    }
}
