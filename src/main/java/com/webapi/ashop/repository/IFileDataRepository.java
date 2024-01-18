package com.webapi.ashop.repository;

import com.webapi.ashop.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFileDataRepository extends JpaRepository<FileData, Integer> {
}
