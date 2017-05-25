package com.luminous.todo;


public class Read_Page {
    private String pageArticle;

    private String pageSummary;

    private String pageURL;

    public Read_Page(String pageArticle,String pageSummary,String pageURL) {
        this.pageArticle=pageArticle;
        this.pageSummary=pageSummary;
        this.pageURL=pageURL;

    }
    public String getPageArticle(){
        return pageArticle;
    }
    public String getPageSummary(){
        return pageSummary;
    }
    public String getPateURL() {return pageURL;}
}

