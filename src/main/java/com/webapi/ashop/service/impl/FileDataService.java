package com.webapi.ashop.service.impl;

import com.webapi.ashop.entity.FileData;
import com.webapi.ashop.entity.dto.FileDataResponseDTO;
import com.webapi.ashop.exception.FileDataNotFoundException;
import com.webapi.ashop.exception.FileDataServiceException;
import com.webapi.ashop.repository.IFileDataRepository;
import com.webapi.ashop.service.IFileDataService;
import com.webapi.ashop.util.FileDataValueMapper;
import com.webapi.ashop.util.ProductValueMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class FileDataService implements IFileDataService {

    private static final String FILE_DIR = "src/main/resources/static/images/";

    @Autowired
    private IFileDataRepository fileDataRepository;

    @Override
    public FileDataResponseDTO uploadFile(MultipartFile file) throws FileDataServiceException {
        FileDataResponseDTO fileDataResponseDTO;

        try {
            log.info("FileDataService:uploadFile execution started");
            /*
             * generate a unique file name
             * */
            String fileName =  UUID.randomUUID() + "_" + file.getOriginalFilename();
            String filePath = FILE_DIR+fileName;

            log.debug("FileDataService:uploadFile request parameters {}", FileDataValueMapper.jsonAsString(file));
            FileData fileResults = fileDataRepository.save(FileData.builder()
                    .name(fileName).type(file.getContentType())
                    .filePath(filePath).build());
            file.transferTo(new File(filePath));
            fileDataResponseDTO = FileDataValueMapper.convertToDTO(fileResults);
            log.debug("FileDataService:uploadFile received response from Database {}", FileDataValueMapper.jsonAsString(fileDataResponseDTO));
        } catch (Exception ex) {
            log.error("Exception occurred while persisting file to database , Exception message {}", ex.getMessage());
            throw new FileDataServiceException("Exception occurred while upload a new file");
        }
        log.info("FileDataService:uploadFile execution ended.");
        return fileDataResponseDTO;
    }

    @Override
    @Cacheable(value = "file", key = "#fileId")
    public FileDataResponseDTO getFileById(int fileId) {
        FileDataResponseDTO fileDataResponseDTO;

        try {
            log.info("FileDataService:getFileById execution started.");
            FileData fileData = fileDataRepository.findById(fileId)
                    .orElseThrow(() -> new FileDataNotFoundException("File not found with id " + fileId));
            fileDataResponseDTO = FileDataValueMapper.convertToDTO(fileData);
            log.debug("FileDataService:getFileById retrieving file from database for id {} {}", fileId, ProductValueMapper.jsonAsString(fileDataResponseDTO));
        } catch (Exception ex) {
            log.error("Exception occurred while retrieving file from database , Exception message {}", ex.getMessage());
            throw new FileDataServiceException("Exception occurred while fetch file from Database " + fileId);
        }
        log.info("FileDataService:getFileById execution ended.");
        return fileDataResponseDTO;
    }

    @Override
    @CacheEvict(value = "file", key = "#fileId")
    public void deleteFile(int fileId) {
        try {
            log.info("FileDataService:deleteFile execution started.");
            FileData fileData = fileDataRepository.findById(fileId)
                    .orElseThrow(() -> new FileDataNotFoundException("File not found with id " + fileId));
            log.debug("FileDataService:deleteFile deleting file from database for id {} {}", fileId, FileDataValueMapper.jsonAsString(fileData));
            fileDataRepository.deleteById(fileId);
        } catch (Exception ex) {
            log.error("Exception occurred while deleting file {} from database , Exception message {}", fileId, ex.getMessage());
            throw new FileDataServiceException("Exception occurred while delete file from Database " + fileId);
        }
    }
}
