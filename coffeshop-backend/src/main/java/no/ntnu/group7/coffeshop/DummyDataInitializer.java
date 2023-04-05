package no.ntnu.group7.coffeshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import no.ntnu.group7.coffeshop.model.Product;
import no.ntnu.group7.coffeshop.model.security.Role;
import no.ntnu.group7.coffeshop.model.security.User;
import no.ntnu.group7.coffeshop.repositories.ProductRepository;
import no.ntnu.group7.coffeshop.repositories.security.RoleRepository;
import no.ntnu.group7.coffeshop.repositories.security.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class which inserts some dummy data into the database, when Spring Boot app
 * has started.
 */
@Component
public class DummyDataInitializer implements ApplicationListener<ApplicationReadyEvent> {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private ProductRepository productRepository;

  private final Logger logger = LoggerFactory.getLogger("DummyInit");

  /**
   * This method is called when the application is ready (loaded)
   *
   * @param event Event which we don't use :)
   */
  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    if (userRepository.count() == 0) {
      logger.info("Importing test users...");

      /* The passwords are hashed using bcrypt */
      User testUser = new User("testUser", "$2a$12$AIbp1s0f5REEJ1Ck7jNlGOcfiApTge164eT9mCxREtjHoNbnv6B2u",
          "test bio");
      User adminUser = new User("admin", "$2a$12$j7zoEDLcKGeNfF/V8eVVVuDV6gYJBsSVmREZfyUN7jErSQV.Ic1Ba",
          "admin man");

      Role user = new Role("ROLE_USER");
      Role admin = new Role("ROLE_ADMIN");

      testUser.addRole(user);
      adminUser.addRole(user);
      adminUser.addRole(admin);

      roleRepository.save(user);
      roleRepository.save(admin);

      userRepository.save(adminUser);
      userRepository.save(testUser);
      logger.info("DONE importing test products");
    } else {
      logger.info("Users already in the database, not importing anything");
    }

    if (productRepository.count() == 0) {
      logger.info("Importing test products...");

      Product brazilianCoffee = new Product("Brazilian coffee", 80, "ground, 500 grams");
      Product greenTea = new Product("Green tea", 50, "200 grams");
      Product peruCoffeeBeans = new Product("Peru coffee beans", 120, "500 grams");

      productRepository.save(brazilianCoffee);
      productRepository.save(greenTea);
      productRepository.save(peruCoffeeBeans);

      logger.info("DONE importing test products");
    } else {
      logger.info("Products already in the database, not importing anything");
    }
  }
}
