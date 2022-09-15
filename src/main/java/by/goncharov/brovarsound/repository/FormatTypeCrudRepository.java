package by.goncharov.brovarsound.repository;

import by.goncharov.brovarsound.model.FormatType;
import org.springframework.data.repository.CrudRepository;

public interface FormatTypeCrudRepository extends CrudRepository<FormatType, Integer> {
	public FormatType findFormatTypeById(Integer id);
}
