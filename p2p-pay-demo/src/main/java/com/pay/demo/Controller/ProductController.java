package com.pay.demo.Controller;

import com.pay.demo.Vo.Result;
import com.pay.demo.entity.Product;
import com.pay.demo.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin//开放前端,跨域访问
@Api(tags = "商品管理")
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @ApiOperation("测试接口")
    @RequestMapping("/list")
    public Result list(){
        List<Product> list = productService.list();
        String id = list.get(0).getId();
        System.out.println(id);
        return Result.ok().data("productList ",list);
    }
}
