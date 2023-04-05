package no.ntnu.group7.coffeshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import no.ntnu.group7.coffeshop.model.Category;

/**
 * Repository interface for accessing Category data in the database.
 * Spring will auto-generate necessary methods.
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
