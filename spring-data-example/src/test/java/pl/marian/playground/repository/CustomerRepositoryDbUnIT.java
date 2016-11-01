package pl.marian.playground.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pl.marian.playground.domain.Customer;
import pl.marian.playground.repository.base.SpringDbUnIT;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
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

    @Test
    public void loadWithJoinPageable() throws Exception {
        loadDataSet("pl/marian/playground/repository/customerWithAccounts.dataset.xml");

        Pageable pageRequest = new PageRequest(0, 1, Sort.Direction.ASC, "customerId");
        Page<Customer> customerPage = customerRepository.loadCustomerWithActiveAccountPage(pageRequest);

        assertThat(customerPage).hasSize(1);
        Customer customer1 = customerPage.iterator().next();
        assertThat(customer1.getAccounts()).hasSize(1);
        assertThat(customer1.activeAccount()).isNotNull();
        assertThat(customer1.activeAccount().getDescription()).isEqualTo("A Account");
    }

    @Test
    public void loadCustomerIdPage() throws Exception {
        loadDataSet("pl/marian/playground/repository/customerWithAccounts.dataset.xml");

        Pageable pageRequest = new PageRequest(0, 1, Sort.Direction.ASC, "customerId");
        Page<Integer> idPage = customerRepository.loadCustomerIdPage(pageRequest);

        assertThat(idPage).hasSize(1);
        assertThat(idPage.getContent().get(0)).isEqualTo(1);
    }

    @Test
    public void loadCustomerIdPageTwoElementsPage() throws Exception {
        loadDataSet("pl/marian/playground/repository/customerWithAccounts.dataset.xml");

        Pageable pageRequest = new PageRequest(0, 2, Sort.Direction.ASC, "customerId");
        Page<Integer> idPage = customerRepository.loadCustomerIdPage(pageRequest);
        assertThat(idPage).hasSize(2);
        assertThat(idPage.getContent().get(0)).isEqualTo(1);
        assertThat(idPage.getContent().get(1)).isEqualTo(2);
    }

    @Test
    public void loadCustomerByIds() throws Exception {
        loadDataSet("pl/marian/playground/repository/customerWithAccounts.dataset.xml");

        List<Customer> customers = customerRepository.loadCustomerWithActiveAccountByIds(asList(1, 2, 99));

        assertThat(customers).hasSize(2);
        assertThat(customers.get(0).getCustomerId()).isEqualTo(1);
        assertThat(customers.get(0).activeAccount().getDescription()).isEqualTo("A Account");
        assertThat(customers.get(1).getCustomerId()).isEqualTo(2);
        assertThat(customers.get(1).activeAccount().getDescription()).isEqualTo("A Account for 2");
    }

    @Test
    public void loadCustomerByIdsCustomerWithoutActiveAccount() throws Exception {
        loadDataSet("pl/marian/playground/repository/customerWithAccounts.dataset.xml");

        List<Customer> customers = customerRepository.loadCustomerWithActiveAccountByIds(asList(3, 32));

        assertThat(customers).hasSize(0);
    }

    @Test
    public void loadCustomerByIdsEmptyIdsList() throws Exception {
        loadDataSet("pl/marian/playground/repository/customerWithAccounts.dataset.xml");

        List<Customer> customers = customerRepository.loadCustomerWithActiveAccountByIds(emptyList());

        assertThat(customers).isEmpty();
    }

    private Customer findById(List<Customer> customers, int customerId) {
        return customers.stream().filter(c -> c.getCustomerId() == customerId).findAny().orElse(null);
    }
}