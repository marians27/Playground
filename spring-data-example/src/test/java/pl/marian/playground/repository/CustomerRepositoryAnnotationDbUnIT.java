package pl.marian.playground.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.marian.playground.domain.Customer;
import pl.marian.playground.repository.base.SpringAnnotationDbUnIT;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Using spring-test-dbUnit library on top of dbUnit
 */
public class CustomerRepositoryAnnotationDbUnIT extends SpringAnnotationDbUnIT {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DatabaseSetup("classpath:/pl/marian/playground/repository/customerRepository.dataset.xml")
    public void findByName_found_xmlDataSet() throws Exception {
        Customer alan = customerRepository.findByName("Alan");

        assertThat(alan.getCustomerId()).isEqualTo(1);
        assertThat(alan.getName()).isEqualTo("Alan");
        assertThat(alan.getRole()).isEqualTo("Manager");
    }

    @Test
    @DatabaseSetup("classpath:/pl/marian/playground/repository/customerRepository.dataset.xml")
    public void findByName_notFound_xmlDataSet() throws Exception {
        Customer rocky = customerRepository.findByName("Rocky");

        assertThat(rocky).isNull();
    }
}