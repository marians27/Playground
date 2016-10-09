package pl.marian.playground.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.marian.playground.domain.Customer;
import pl.marian.playground.repository.base.SpringDbUnIT;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pure DbUnit test
 */
public class CustomerRepositoryDbUnIT extends SpringDbUnIT {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void findByName_found_xmlDataSet() throws Exception {
        loadDataSet("pl/marian/playground/repository/customerRepository.dataset.xml");

        Customer alan = customerRepository.findByName("Alan");

        assertThat(alan.getCustomerId()).isEqualTo(1);
        assertThat(alan.getName()).isEqualTo("Alan");
        assertThat(alan.getRole()).isEqualTo("Manager");
    }

    @Test
    public void findByName_notFound_xmlDataSet() throws Exception {
        loadDataSet("pl/marian/playground/repository/customerRepository.dataset.xml");

        Customer rocky = customerRepository.findByName("Rocky");

        assertThat(rocky).isNull();
    }
}