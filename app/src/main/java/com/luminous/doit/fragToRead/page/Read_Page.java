package com.luminous.doit.fragToRead.page;


import org.litepal.crud.DataSupport;

import java.util.Date;

public class Read_Page extends DataSupport{
    private int id;

    private int pageID;

    private String pageArticle;

    private String pageSummary;

    private String pageURL;

    private Date pageDate;

    public Read_Page(int pageID,String pageArticle,String pageSummary,String pageURL,Date pageDate) {
        this.pageID=pageID;
        this.pageArticle=pageArticle;
        this.pageSummary=pageSummary;
        this.pageURL=pageURL;
        this.pageDate=pageDate;

    }
    public void setPageID(int pageID){
        this.pageID=pageID;
    }
    public void setPageArticle(String pageArticle){
        this.pageArticle=pageArticle;
    }
    public void setPageSummary(String pageSummary){
        this.pageSummary=pageSummary;
    }
    public void setPageURL(String pageURL){
        this.pageURL=pageURL;
    }
    public void setPageDate(Date pageDate){
        this.pageDate=pageDate;
    }

    public int getPageID() {return pageID;}
    public String getPageArticle(){
        return pageArticle;
    }
    public String getPageSummary(){
        return pageSummary;
    }
    public String getPateURL() {return pageURL;}
    public Date getPageDate() {return pageDate;}
    public int getId(){ return id;}
}

