package com.webapi.ashop.service;

import com.webapi.ashop.entity.dto.FileDataResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IFileDataService {
    FileDataResponseDTO uploadFile(MultipartFile file);
    FileDataResponseDTO getFileById(int fileId);
    void deleteFile(int fileId);
}
