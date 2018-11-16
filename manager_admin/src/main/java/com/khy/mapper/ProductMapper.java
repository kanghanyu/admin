package com.khy.mapper;

import java.util.List;

import com.khy.entity.Product;

public interface ProductMapper {

    int insert(Product product);

	List<Product> list(Product product);

	Product findByProductId(Long productId);

	int updateProduct(Product product);

	int delProduct(Long productId);
}