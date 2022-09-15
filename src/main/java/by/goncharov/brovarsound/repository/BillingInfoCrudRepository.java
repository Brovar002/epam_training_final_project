package by.goncharov.brovarsound.repository;

import by.goncharov.brovarsound.model.BillingInfo;
import org.springframework.data.repository.CrudRepository;

public interface BillingInfoCrudRepository extends CrudRepository<BillingInfo, Integer> {
	public BillingInfo findBillingInfoById(Integer id);
}
