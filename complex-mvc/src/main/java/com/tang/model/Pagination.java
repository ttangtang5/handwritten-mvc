package com.tang.model;

import com.tang.util.PropertyUtil;

public class Pagination {

    private static final String KEY_PAGE_SIZE = "pagination.pageSize";
    private int pageSize ;
    private int totalCount;
    private int pageCount;
    private int currentPage;
    private int offset;

    public int getTotalCount(){
        return totalCount;
    }
    public int getPageSize(){
        if (pageSize <= 0 ) {
            return Integer.parseInt(PropertyUtil.getProperties(KEY_PAGE_SIZE));
        }
        return pageSize;
    }
    public void setPageSize(int pageSize){
     this.pageSize = pageSize;
    }
    public void setTotalCount(int totalCount){
        this.totalCount = totalCount;
    }
    public int getPageCount(){
        if(totalCount< 1){
            pageCount = 1;
            return pageCount;
        }
        pageCount = (totalCount-1) / getPageSize() + 1;
        return pageCount;
    }
    public int getCurrentPage(){
        if(currentPage < 1){
            currentPage = 1;
        }
        return currentPage;
    }
    public void setCurrentPage(int currentPage){
        this.currentPage = currentPage;
    }
    public int getOffset(){
        offset = (getCurrentPage() - 1) * getPageSize();
        return offset;
    }
}
