package ru.samara.refs.service;

import java.io.FileNotFoundException;
import java.util.UUID;

import ru.samara.refs.dto.StoredFileDto;

public interface StoredFileService {
	
	UUID create(StoredFileDto file);
	void update(UUID guid, StoredFileDto file) throws FileNotFoundException;
	StoredFileDto get(UUID guid) throws FileNotFoundException;
	void upload(UUID guid,byte[] content) throws FileNotFoundException;
	byte[] download(UUID guid) throws FileNotFoundException;
	void delete(UUID guid);
	
}
