package pk.habsoft.demo.estore.service;

import java.util.List;

import pk.habsoft.demo.estore.dto.ProductDTO;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    ProductDTO getById(Long id);

}