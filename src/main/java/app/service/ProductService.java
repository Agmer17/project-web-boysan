package app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.pojo.ProductEntity;
import app.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    public List<ProductEntity> getAllProducts() {
        List<ProductEntity> allProducts = productRepo.getAll();

        return allProducts;
    }

    public ProductEntity findById(String id) {
        ProductEntity result = productRepo.findDetails(id);

        return result;
    }

}
