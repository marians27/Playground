package pl.marian.playground.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.marian.playground.domain.Customer;
import pl.marian.playground.repository.base.SpringDbUnIT;

import java.util.List;

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

    @Test
    public void loadWithJoin() throws Exception {
        loadDataSet("pl/marian/playground/repository/customerWithAccounts.dataset.xml");

        List<Customer> customers = customerRepository.loadCustomerWithActiveAccount();

        assertThat(customers).hasSize(2);
        Customer customer1 = findById(customers, 1);
        assertThat(customer1.getAccounts()).hasSize(1);
        assertThat(customer1.activeAccount()).isNotNull();
        assertThat(customer1.activeAccount().getDescription()).isEqualTo("A Account");

        Customer customer2 = findById(customers, 2);
        assertThat(customer2.getAccounts()).hasSize(1);
        assertThat(customer2.activeAccount()).isNotNull();
        assertThat(customer2.activeAccount().getDescription()).isEqualTo("A Account for 2");
    }

    private Customer findById(List<Customer> customers, int customerId) {
        return customers.stream().filter(c -> c.getCustomerId() == customerId).findAny().orElse(null);
    }
}