package com.xcel_upload_data.upload_data_from_file.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {
    @Id
    private  Integer productId;
    private  String productName;
    private String   productDesc;
    private  Double  productPrice;

}
