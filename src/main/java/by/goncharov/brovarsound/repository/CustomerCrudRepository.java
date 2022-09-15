package by.goncharov.brovarsound.repository;

import by.goncharov.brovarsound.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerCrudRepository extends CrudRepository<Customer, Integer> {
    public Customer findCustomerByUsername(String username);
    public Customer findCustomerById(Integer id);
}
