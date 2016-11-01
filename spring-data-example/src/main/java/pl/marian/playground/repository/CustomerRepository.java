package pl.marian.playground.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.marian.playground.domain.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByName(String name);

    @Query("SELECT c FROM Customer c JOIN FETCH c.accounts a WHERE a.status='A'")
    List<Customer> loadCustomerWithActiveAccount();

    /*
        WARNING: This is inefficient because hibernate can't create a query with limit if join fetch is used
        Hence paging will be done in memory, so first all rows will be loaded and then restricted to the proper page.
     */
    @Query(value = "SELECT c FROM Customer c LEFT OUTER JOIN FETCH c.accounts a WHERE a.status='A'",
            countQuery = "SELECT count(*) FROM Customer")
    Page<Customer> loadCustomerWithActiveAccountPage(Pageable pageable);

    @Query("SELECT c.customerId FROM Customer c")
    Page<Integer> loadCustomerIdPage(Pageable pageable);

    @Query("SELECT c FROM Customer c LEFT OUTER JOIN FETCH c.accounts a WHERE a.status='A' AND c.customerId IN (:ids)")
    List<Customer> loadCustomerWithActiveAccountByIds(@Param("ids") List<Integer> ids);
}
