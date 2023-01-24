package com.gmhis_backk.repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class FileSystemRepository {
	
	private final Path rootLocation = Paths.get("upload-dir");
	
	 String RESOURCES_DIR = FileSystemRepository.class.getResource("/")
		        .getPath();

		    public String save(MultipartFile file,byte[] content, String imageName) throws Exception {
		    	
		        Path newFile = Paths.get(RESOURCES_DIR +"/images/"+ new Date().getTime() + "-" + imageName);
		        
		        Path destinationFile = this.rootLocation.resolve(
						Paths.get(file.getOriginalFilename()))
						.normalize().toAbsolutePath();
		        
		        
		        Files.createDirectories(destinationFile.getParent());

		        Files.write(destinationFile, content);

		        return destinationFile.toAbsolutePath()
		            .toString();
		    }
		    
		    public FileSystemResource findInFileSystem(String location) {
		        try {
		            return new FileSystemResource(Paths.get(location));
		        } catch (Exception e) {
		            // Handle access or file not found problems.
		            throw new RuntimeException();
		        }
		    }
}
