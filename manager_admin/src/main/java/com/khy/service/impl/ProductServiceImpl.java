package com.khy.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.khy.entity.Product;
import com.khy.entity.User;
import com.khy.mapper.ProductMapper;
import com.khy.service.ProductService;
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;
	@Override
	public PageInfo<Product> page(Product product) {
		PageHelper.startPage(product.getPageNum(), product.getPageSize());
		List<Product> list = productMapper.list(product);
		PageInfo <Product>pageInfo = new PageInfo<Product>(list);
		return pageInfo;
	}
	
	
	@Override
	public JSONObject saveProduct(Product product) {
		JSONObject json = new JSONObject();
		json.put("code",2000);
		product.setStatus(0);
		product.setSalesAmount(0);
		product.setCreateTime(new Date());
		int flag = productMapper.insert(product);
		if(flag>0){
			json.put("code",1000);
			json.put("msg","保存成功");
		}
		return json;
	}


	@Override
	public JSONObject updateProduct(Product product) {
		JSONObject json = new JSONObject();
		json.put("code",2000);
		if(null == product || null ==product.getProductId()){
			json.put("msg","参数不能为空");
			return json;
		}
		Product productDB = productMapper.findByProductId(product.getProductId());
		if(null == productDB){
			json.put("msg","该商品不存在");
			return json;
		}
		BeanUtils.copyProperties(product, productDB);
		int flag = productMapper.updateProduct(productDB);
		if(flag>0){
			json.put("code",1000);
			json.put("msg","修改成功");
		}
		return json;
	}


	
	@Override
	public JSONObject findByProductId(Product product) {
		JSONObject json = new JSONObject();
		json.put("code",2000);
		if(null == product || null == product.getProductId()){
			json.put("msg","参数productId不能为空");
			return json;
		}
		Product productDb = productMapper.findByProductId(product.getProductId());
		if(null == productDb){
			json.put("msg","未查询到该条记录");
			return json;
		}
		json.put("code",1000);
		json.put("product", productDb);
		return json;
	}


	@Override
	public JSONObject setProductStatus(Product product) {
		JSONObject json = new JSONObject();
		json.put("code",2000);
		json.put("msg","操作失败");
		if(null != product){
			json.put("msg","参数不能为空");
		}
		int num = productMapper.updateProduct(product);
		if(num>0){
			json.put("code",1000);
			json.put("msg","操作成功");
		}
		return json;
	}


	@Override
	public JSONObject delProduct(Product product) {
		JSONObject json = new JSONObject();
		json.put("code",2000);
		if(null == product || null ==product.getProductId()){
			json.put("msg","参数不能为空");
			return json;
		}
		int num = productMapper.delProduct(product.getProductId());
		if(num>0){
			json.put("code",1000);
			json.put("msg","删除成功");
		}
		return json;
	}

}
