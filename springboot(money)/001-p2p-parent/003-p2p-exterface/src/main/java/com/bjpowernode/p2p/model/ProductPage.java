package com.bjpowernode.p2p.model;
import java.io.Serializable;
import java.util.List;

public class ProductPage<T> implements Serializable {
    private List<T> productList;
    private Integer AllPage;//总数量
    private Integer TotalPage;//总页数
    private Integer currentPage;
    private Integer count;//每页查询的数量
    public List<T> getProductList() {
        return productList;

    }


    public ProductPage(List<T> productList, Integer allPage, Integer totalPage, Integer currentPage, Integer count) {
        this.productList = productList;
        AllPage = allPage;
        TotalPage = totalPage;
        this.currentPage = currentPage;
        this.count = count;
    }

    public void setProductList(List<T> productList) {
        this.productList = productList;
    }

    public Integer getAllPage() {
        return AllPage;
    }

    public void setAllPage(Integer allPage) {
        AllPage = allPage;
    }

    public Integer getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(Integer totalPage) {
        TotalPage = totalPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ProductPage() {
    }




}
