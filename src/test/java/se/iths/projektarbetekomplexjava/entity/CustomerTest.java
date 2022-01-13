package se.iths.projektarbetekomplexjava.entity;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CustomerTest {

    CustomerRepository repository;
    @Autowired
    TestEntityManager entityManager;


    @Test
    public void testCreateCustomer() {
        Customer customer = new Customer();

        customer.setFirstName("biniam");
        customer.setLastName("haile");
        customer.setAddress("styrbjörnsvägen");
        customer.setPhone("0790111111");
        customer.setPassword("Biniam@123");
        customer.setEmail("biniam@gmail.com");
        customer.setShoppingCart(null);

        Customer savedCustomer = repository.save(customer);

        Customer existcustomer = entityManager.find(Customer.class, savedCustomer.getId());

        assertThat(customer.getEmail()).isEqualTo(existcustomer.getEmail());

    }
}
