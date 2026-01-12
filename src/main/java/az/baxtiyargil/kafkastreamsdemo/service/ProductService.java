package az.baxtiyargil.kafkastreamsdemo.service;

import az.baxtiyargil.kafkastreamsdemo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Set<Long> findExistingProductIds(Set<Long> ids) {
        return productRepository.findExistingIds(ids);
    }
}
