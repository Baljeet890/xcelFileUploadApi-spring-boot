package com.xcel_upload_data.upload_data_from_file.repository;

import com.xcel_upload_data.upload_data_from_file.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XcelUploadRepository extends JpaRepository<Product, Integer> {

}
