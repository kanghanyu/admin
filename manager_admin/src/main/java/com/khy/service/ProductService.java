package com.khy.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.khy.entity.Product;

public interface ProductService {

	PageInfo<Product> page(Product product);

	JSONObject saveProduct(Product product);

	JSONObject findByProductId(Product product);

	JSONObject setProductStatus(Product product);

	JSONObject updateProduct(Product product);

	JSONObject delProduct(Product product);

}
