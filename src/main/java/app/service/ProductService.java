package app.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import app.model.dto.NewServiceProduct;
import app.model.exception.ResourceNotFoundException;
import app.model.pojo.BaseServiceProduct;
import app.model.pojo.ImageProduct;
import app.model.pojo.ProductEntity;
import app.repository.ProductRepository;
import app.utils.FileUtils;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ImageProductService imageProductService;

    public List<ProductEntity> getAllProducts() {
        List<ProductEntity> allProducts = productRepo.getAll();

        return allProducts;
    }

    public ProductEntity findById(String id) {
        try {
            ProductEntity result = productRepo.findDetails(id);
            return result;
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Produk tidak ditemukan!", "admin/products/items/" + id);
        }

    }

    public BaseServiceProduct addNewProduct(NewServiceProduct newProduct) throws IOException {

        BaseServiceProduct svResult = productRepo.save(newProduct);

        // System.out.println(svResult);

        // save dan verif image dulu boy

        List<String> allImageUrl = null;

        if (newProduct.getImages() != null) {
            allImageUrl = FileUtils.handleBatchUploadsImage(newProduct.getImages());
            List<ImageProduct> convertRs = imageProductService.makeImageObject(svResult.getId(), allImageUrl);
            imageProductService.save(convertRs);

        }

        return svResult;

    }

    public void deleteProducts(UUID productId) {

        List<ImageProduct> ls = imageProductService.findByServiceId(productId);

        // delete dulu image di server nya
        for (ImageProduct imageProduct : ls) {
            FileUtils.deleteImage(imageProduct.getImageUrl());
        }

        productRepo.remove(productId);
    }

}
