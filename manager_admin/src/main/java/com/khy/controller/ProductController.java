package com.khy.controller;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.khy.entity.Product;
import com.khy.entity.User;
import com.khy.service.ProductService;
import com.khy.utils.FileUtils;
import com.khy.utils.SessionHolder;
import com.khy.utils.Utils;

@Controller
@RequestMapping("/product")
public class ProductController {
	public static String BASE_URL = "http://nongke.oss-cn-beijing.aliyuncs.com/";
	@Autowired
	private ProductService productService;
	@RequestMapping("/toProductList")
	public ModelAndView toProductList(Product product){
		ModelAndView model = new ModelAndView("product/list");
		PageInfo<Product> page =  productService.page(product);
		model.addObject("page", page);
		return model;
	}
	
	@RequestMapping("/dataList")
	@ResponseBody
	public String dateList(@RequestBody Product product){
		PageInfo<Product> page =  productService.page(product);
		return JSON.toJSONString(page);
	}
	
	@RequestMapping("/setProductStatus")
	@ResponseBody
	public String setProductStatus(@RequestBody Product product) throws UnsupportedEncodingException{
		JSONObject json = productService.setProductStatus(product);
		return json.toString();
	}
	
	@RequestMapping("/saveProduct")
	@ResponseBody
	public String saveProduct(@RequestBody Product product){
		JSONObject json = productService.saveProduct(product);
		return json.toString();
	}
	
	@RequestMapping("/updateProduct")
	@ResponseBody
	public String updateProduct(@RequestBody Product product){
		JSONObject json = productService.updateProduct(product);
		return json.toString();
	}
	@RequestMapping("/delProduct")
	@ResponseBody
	public String delProduct(@RequestBody Product product){
		JSONObject json = productService.delProduct(product);
		return json.toString();
	}
	
	@RequestMapping("/findByProductId")
	@ResponseBody
	public String findByProductId(@RequestBody Product product){
		JSONObject json = productService.findByProductId(product);
		return json.toString();
	}
	
	@RequestMapping("/uploadCoverImg")
	@ResponseBody
	public String upload(MultipartRequest request) {
		JSONObject json = new JSONObject();
		json.put("code", "2000");
		json.put("msg", "文件不也能为空");
		Iterator<String> fileNames = request.getFileNames();
		if(null == fileNames && !fileNames.hasNext()){
			return json.toString();
		}
		String next = fileNames.next();
		MultipartFile file = request.getFile(next);
		if(null == file || file.getSize() <= 0 ){
			return json.toString();
		}
		String mimetype = file.getContentType();
		String type = mimetype.split("/")[0];
		if(!type.equals("image")){
			json.put("msg", "文件必须是图片类型");
			return json.toString();
		}
		String originalFilename = file.getOriginalFilename();
		String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));		
		String fileName =next+"/"+Utils.getFileName()+suffix;
		FileUtils.uploadImg(file,fileName);
		json.put("code", "1000");
		json.put("url", BASE_URL+fileName);
		return json.toString();
	}
	
}

