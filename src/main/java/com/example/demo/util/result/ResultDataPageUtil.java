package com.example.demo.util.result;

import com.google.gson.Gson;

import java.util.List;

public class ResultDataPageUtil<T> extends Response {

    private long total; // 总条数
    private int pageNum; // 当前页
    private int pageSize; // 每页条数
    private List<T> dataList;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    @Override
    public String toJson() {
        return (new Gson()).toJson(this);
    }
}
