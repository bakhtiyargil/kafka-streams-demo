package az.baxtiyargil.kafkastreamsdemo.repository;

import az.baxtiyargil.kafkastreamsdemo.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
                select p.id from Product p where p.id in :ids
            """)
    Set<Long> findExistingIds(@Param("ids") Set<Long> ids);

}
