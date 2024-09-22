package mmtk.backend.dreamshop.services.product;

import mmtk.backend.dreamshop.dtos.ProductDto;
import mmtk.backend.dreamshop.models.Category;
import mmtk.backend.dreamshop.models.Product;
import mmtk.backend.dreamshop.requests.AddProductRequest;
import mmtk.backend.dreamshop.requests.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest request);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest productUpdateRequest, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);
    List<ProductDto> getConvertedProducts(List<Product> products);
    ProductDto convertToDto(Product product);
}
