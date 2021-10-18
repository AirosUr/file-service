package ru.samara.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.samara.refs.dto.StoredFileDto;
import ru.samara.refs.service.StoredFileService;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Controller
public class StoredFileController {
	
	private StoredFileService storedFileService;
		
	@Autowired
    public StoredFileController(StoredFileService storedFileService) {
		super();
		this.storedFileService = storedFileService;
	}

    @PreAuthorize("hasAuthority('vuz-ref')")
	@PostMapping(value = "/file/new",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createFile (@RequestBody StoredFileDto file) {
        return ResponseEntity.ok(storedFileService.create(file).toString());
    }	

    @PreAuthorize("hasAuthority('vuz-ref')")
	@PostMapping(value = "/file/upload",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity uploadFile (@RequestParam("guid") String guid,
    		@RequestParam("file") MultipartFile file) {
    	try {
			storedFileService.upload(UUID.fromString(guid), file.getBytes());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ResponseEntity.ok().build();
    }	
    
    @GetMapping(value="/public/file", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> download(@RequestParam("guid") String guid,
    		HttpServletResponse response) {
		if (guid == null || guid.equals("null")){
			return ResponseEntity.noContent().build();
		} else {
			try {
				byte[] c = storedFileService.download(UUID.fromString(guid));
				response.setContentType(MediaType.IMAGE_JPEG_VALUE);
				return ResponseEntity.ok(c);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return ResponseEntity.noContent().build();
			}
		}
    }

	@PreAuthorize("hasAuthority('vuz-ref')")
	@DeleteMapping(value = "/file", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteByGuid(@RequestParam("guid") String guid) {
		storedFileService.delete(UUID.fromString(guid));
		return ResponseEntity.ok("Successfully deleted");
	}

}
