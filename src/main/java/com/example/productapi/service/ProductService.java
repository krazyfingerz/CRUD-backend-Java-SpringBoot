// Uses ProductRepository and ProductMapper

import com.example.productapi.dto.ProductDTO;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO getProductById(UUID id);
    List<ProductDTO> getAllProducts();
    ProductDTO updateProduct(UUID id, ProductDTO productDTO);
    void deleteProduct(UUID id);
}
