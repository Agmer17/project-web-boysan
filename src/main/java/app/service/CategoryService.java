package app.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.entity.ServiceCategory;
import app.repository.ServiceCategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private ServiceCategoryRepository repo;

    public ServiceCategory createServiceCategory(ServiceCategory serviceCategory) {
        if (repo.existsByName(serviceCategory.getName())) {
            throw new RuntimeException("Service category with name '" + serviceCategory.getName() + "' already exists");
        }
        return repo.save(serviceCategory);
    }

    public Optional<ServiceCategory> getServiceCategoryById(UUID id) {
        return repo.findById(id);
    }

    public List<ServiceCategory> getAllServiceCategories() {
        return repo.findAll();
    }

    public List<ServiceCategory> findAllServiceCategoryByName(String name) {
        List<ServiceCategory> result = repo.findAllByName(name);

        return result;
    }

    public ServiceCategory updateServiceCategory(UUID id, ServiceCategory serviceCategory) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Service category with id " + id + " not found");
        }

        Optional<ServiceCategory> existingByName = repo.findByName(serviceCategory.getName());
        if (existingByName.isPresent() && !existingByName.get().getId().equals(id)) {
            throw new RuntimeException("Service category with name '" + serviceCategory.getName() + "' already exists");
        }

        serviceCategory.setId(id);
        return repo.update(serviceCategory);
    }

    public void deleteServiceCategory(UUID id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Service category with id " + id + " not found");
        }
        repo.deleteById(id);
    }

    public boolean existsById(UUID id) {
        return repo.existsById(id);
    }

    public boolean existsByName(String name) {
        return repo.existsByName(name);
    }
}
