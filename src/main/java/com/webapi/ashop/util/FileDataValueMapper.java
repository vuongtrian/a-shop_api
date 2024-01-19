package com.webapi.ashop.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapi.ashop.entity.FileData;
import com.webapi.ashop.entity.dto.FileDataResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class FileDataValueMapper {
    public static FileDataResponseDTO convertToDTO(FileData fileData) {
        FileDataResponseDTO fileDataResponseDTO = new FileDataResponseDTO();
        fileDataResponseDTO.setId(fileData.getId());
        fileDataResponseDTO.setName(fileData.getName());
        fileDataResponseDTO.setType(fileData.getType());
        fileDataResponseDTO.setFilePath(fileData.getFilePath());
        return fileDataResponseDTO;
    }

    public static List<FileDataResponseDTO> convertToListDTO(List<FileData> fileDataList) {
        return fileDataList
                .stream()
                .map(fileData -> convertToDTO(fileData))
                .collect(Collectors.toList());
    }

    public static String jsonAsString(Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
