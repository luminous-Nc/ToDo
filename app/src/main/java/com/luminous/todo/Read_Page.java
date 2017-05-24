package com.luminous.todo;


public class Read_Page {
    private String pageArticle;

    private String pageSummary;

    public Read_Page(String pageArticle,String pageSummary) {
        this.pageArticle=pageArticle;
        this.pageSummary=pageSummary;

    }
    public String getPageArticle(){
        return pageArticle;
    }
    public String getPageSummary(){
        return pageSummary;
    }
}

