package pl.marian.playground.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.marian.playground.domain.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByName(String name);

    @Query("SELECT c FROM Customer c JOIN FETCH c.accounts a WHERE a.status='A'")
    List<Customer> loadCustomerWithActiveAccount();
}
