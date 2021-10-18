package ru.samara.refs.service;

import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.samara.exception.EntityNotFoundException;
import ru.samara.refs.domain.StoredFile;
import ru.samara.refs.dto.StoredFileDto;
import ru.samara.refs.repo.StoredFileRepository;

@Service
public class StoredFileServiceImpl implements StoredFileService {

	private final StoredFileRepository storedFileRepository;

	private static final String ERROR_MESSAGE = "File to update not found in DB {}";
	
	@Autowired
	public StoredFileServiceImpl(StoredFileRepository storedFileRepository) {
		super();
		this.storedFileRepository = storedFileRepository;
	}

	@Override
	public UUID create(StoredFileDto file) {
		StoredFile f = StoredFile.builder().
				name(file.getName()).
				fileType(file.getFileType()).
				description(file.getDescription()).
				build();
		return storedFileRepository.save(f).getGuid();	
	}

	@Override
	public void update(UUID guid, StoredFileDto f) throws FileNotFoundException {
		Optional<StoredFile> oFile = storedFileRepository.findById(guid);
		if (oFile.isEmpty()) {
			throw new FileNotFoundException(String.format(ERROR_MESSAGE,guid));
		}
		else {
			StoredFile file = oFile.get();
			file.setName(f.getName());
			file.setDescription(f.getDescription());
			file.setFileType(f.getFileType());
			storedFileRepository.save(file);
		}
	}

	@Override
	public StoredFileDto get(UUID guid) throws FileNotFoundException {
		Optional<StoredFile> oFile = storedFileRepository.findById(guid);
		if (oFile.isEmpty()) {
			throw new FileNotFoundException(String.format(ERROR_MESSAGE,guid));
		}
		else {
			StoredFile f = oFile.get();
			return StoredFileDto.builder().
					name(f.getName()).
					description(f.getDescription()).
					fileType(f.getFileType()).build();
		}
	}

	@Override
	public byte[] download(UUID guid) throws FileNotFoundException {
		Optional<StoredFile> oFile = storedFileRepository.findById(guid);
		if (oFile.isEmpty()) {
			throw new FileNotFoundException(String.format(ERROR_MESSAGE,guid));
		}
		else {
			return oFile.get().getContent();
		}
	}

	@Override
	public void upload(UUID guid, byte[] content) throws FileNotFoundException {
		Optional<StoredFile> oFile = storedFileRepository.findById(guid);
		if (oFile.isEmpty()) {
			throw new FileNotFoundException(String.format(ERROR_MESSAGE,guid));
		}
		else {
			StoredFile file = oFile.get();
			file.setContent(content);
			storedFileRepository.save(file);
		}
		
	}

	@Override
	public void delete(UUID guid) {
		StoredFile storedFile = storedFileRepository.findById(guid)
				.orElseThrow(() -> new EntityNotFoundException("Stored file with guid# " + guid + " not found"));
		storedFileRepository.delete(storedFile);
	}
}
