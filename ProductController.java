package com.example.ecommerce;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;
 
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductRepository repo;
    @GetMapping
    public List<Product> getAllProducts() {
        return repo.findAll();
    }
    @GetMapping("/search")
    public List<Product> search(@RequestParam String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }
    @GetMapping("/filter")
    public List<Product> filter(@RequestParam(required = false) String category,
                                @RequestParam(required = false) Double minPrice,
                                @RequestParam(required = false) Double maxPrice) {
        if (category != null) {
            return repo.findByCategory(category);
        }
        if (minPrice != null && maxPrice != null) {
            return repo.findByPriceBetween(minPrice, maxPrice);
        }
        return repo.findAll();
    }
 
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Product addProduct(@RequestBody Product p) {
        return repo.save(p);
    }
 
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product p) {
        p.setProductId(id);
        return repo.save(p);
    }
 
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
