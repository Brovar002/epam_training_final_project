package by.goncharov.brovarsound.repository;

import by.goncharov.brovarsound.model.ConditionType;
import org.springframework.data.repository.CrudRepository;

public interface ConditionCrudRepository extends CrudRepository<ConditionType, Integer> {
	public ConditionType findConditionTypeById(Integer id);
}
