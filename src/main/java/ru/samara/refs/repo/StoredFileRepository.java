package ru.samara.refs.repo;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import ru.samara.refs.domain.StoredFile;

public interface StoredFileRepository extends CrudRepository<StoredFile, UUID> {
	
	
}
