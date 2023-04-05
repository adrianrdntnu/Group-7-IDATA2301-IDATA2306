package no.ntnu.group7.coffeshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import no.ntnu.group7.coffeshop.model.OrderProduct;

/**
 * Repository interface for accessing OrderProduct data in the database.
 * Spring will auto-generate necessary methods.
 */
public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {

}
