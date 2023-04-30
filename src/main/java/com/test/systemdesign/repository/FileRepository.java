package com.test.systemdesign.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.systemdesign.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {

}
