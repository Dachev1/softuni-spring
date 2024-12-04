package bg.softuni.mobilele.initializer;

import bg.softuni.mobilele.model.entity.*;
import bg.softuni.mobilele.model.enums.*;
import bg.softuni.mobilele.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;
    private final OfferRepository offerRepository;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository,
                           BrandRepository brandRepository, ModelRepository modelRepository,
                           OfferRepository offerRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.brandRepository = brandRepository;
        this.modelRepository = modelRepository;
        this.offerRepository = offerRepository;
    }

    @Override
    @Transactional // Ensures all operations are within a single transaction
    public void run(String... args) {
        initializeRoles();
        initializeUsers();
        initializeBrandsAndModels();
        initializeOffers();
    }

    private void initializeRoles() {
        if (!this.roleRepository.existsByName(UserRole.ADMIN)) {
            Role adminRole = new Role();
            adminRole.setName(UserRole.ADMIN);
            this.roleRepository.saveAndFlush(adminRole);
        }

        if (!this.roleRepository.existsByName(UserRole.USER)) {
            Role userRole = new Role();
            userRole.setName(UserRole.USER);
            this.roleRepository.saveAndFlush(userRole);
        }
    }

    private void initializeUsers() {
        Role adminRole = this.roleRepository.findByName(UserRole.ADMIN).orElseThrow();
        Role userRole = this.roleRepository.findByName(UserRole.USER).orElseThrow();

        if (this.userRepository.count() == 0) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword("admin123"); // Encode in real scenarios
            adminUser.setFirstName("System");
            adminUser.setLastName("Administrator");
            adminUser.setIsActive(true);
            adminUser.setRole(adminRole);
            adminUser.setCreated(LocalDateTime.now());
            adminUser.setModified(LocalDateTime.now());
            this.userRepository.saveAndFlush(adminUser);

            User regularUser = new User();
            regularUser.setUsername("user");
            regularUser.setPassword("user123"); // Encode in real scenarios
            regularUser.setFirstName("John");
            regularUser.setLastName("Doe");
            regularUser.setIsActive(true);
            regularUser.setRole(userRole);
            regularUser.setCreated(LocalDateTime.now());
            regularUser.setModified(LocalDateTime.now());
            this.userRepository.saveAndFlush(regularUser);
        }
    }

    private void initializeBrandsAndModels() {
        if (this.brandRepository.count() == 0) {
            Brand brand = new Brand();
            brand.setName("Toyota");
            brand.setCreated(LocalDateTime.now());
            brand.setModified(LocalDateTime.now());
            this.brandRepository.saveAndFlush(brand);

            Model model = new Model();
            model.setName("Corolla");
            model.setCategory(Category.CAR);
            model.setImageUrl("https://example.com/toyota-corolla.jpg");
            model.setStartYear(2000);
            model.setEndYear(2022);
            model.setCreated(LocalDateTime.now());
            model.setModified(LocalDateTime.now());
            model.setBrand(brand);
            this.modelRepository.saveAndFlush(model);
        }
    }

    private void initializeOffers() {
        if (this.offerRepository.count() == 0) {
            User seller = this.userRepository.findByUsername("admin")
                    .orElseThrow(() -> new IllegalStateException("Admin user not found"));

            Model model = (Model) this.modelRepository.findByName("Corolla")
                    .orElseThrow(() -> new IllegalStateException("Model not found"));

            Offer offer = new Offer();
            offer.setDescription("Excellent condition, low mileage.");
            offer.setEngine(Engine.GASOLINE);
            offer.setImageUrl("https://example.com/toyota-corolla-offer.jpg");
            offer.setMileage(50000);
            offer.setPrice(15000);
            offer.setTransmission(Transmission.MANUAL);
            offer.setYear(2020);
            offer.setCreated(LocalDateTime.now());
            offer.setModified(LocalDateTime.now());
            offer.setModel(model);
            offer.setSeller(seller);

            this.offerRepository.saveAndFlush(offer);
        }
    }
}
