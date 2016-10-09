package pl.marian.playground;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.marian.playground.config.repository.RepositoryConfig;
import pl.marian.playground.domain.Customer;
import pl.marian.playground.repository.CustomerRepository;

public class Runner {

    public static void main(String[] args) {
        ApplicationContext applicationContext = setUpContext();

        CustomerRepository customerRepository = applicationContext.getBean(CustomerRepository.class);
        customerRepository.save(createCustomer());
        System.out.println(customerRepository.findByName("Alan"));
        System.out.println(customerRepository.findByName("Mark"));
    }

    private static AnnotationConfigApplicationContext setUpContext() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(RepositoryConfig.class);
        return applicationContext;
    }

    private static Customer createCustomer() {
        Customer customer = new Customer();
        customer.setName("Alan");
        customer.setRole("Manager");
        return customer;
    }
}
