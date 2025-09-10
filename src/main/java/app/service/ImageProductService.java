package app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.pojo.ImageProduct;
import app.repository.ProductImageRepository;

@Service
public class ImageProductService {

    @Autowired
    private ProductImageRepository repo;

    public ImageProduct save(ImageProduct product) {
        return repo.save(product);
    }

    public List<ImageProduct> save(List<ImageProduct> listOfImage) {
        return repo.saveBatchWithReturning(listOfImage);
    }

    public List<ImageProduct> makeImageObject(UUID serviceProductId, List<String> listOfUrls) {

        if (listOfUrls.isEmpty()) {
            return new ArrayList<ImageProduct>();
        }

        List<ImageProduct> listOfImageProducts = new ArrayList<>();

        for (String string : listOfUrls) {
            listOfImageProducts.add(
                    ImageProduct.builder().serviceId(serviceProductId).imageUrl(string).build());
        }

        return listOfImageProducts;

    }

    public List<ImageProduct> findByServiceId(UUID serviceId) {
        return repo.findByServiceId(serviceId);
    }
}
