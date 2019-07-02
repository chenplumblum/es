package com.deepexi.elasticsearch.demo.domain;


import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

/**
 * 实体类
 *
 * @author RenWei
 * @date 2019/06/19
 */
@Document(indexName = "inf", type = "employee")
public class Employee {
    private Long id;
    private String name;
    private String type;
    private Date createTime;
    private Date updateTime;
    private String url;
    private Integer readNumber;
    private Integer likeNumber;
    private String content;
    private String writer;
    private String resource;
    private Date publishTime;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", url='" + url + '\'' +
                ", readNumber=" + readNumber +
                ", likeNumber=" + likeNumber +
                ", content='" + content + '\'' +
                ", writer='" + writer + '\'' +
                ", resource='" + resource + '\'' +
                ", publishTime=" + publishTime +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setReadNumber(Integer readNumber) {
        this.readNumber = readNumber;
    }

    public void setLikeNumber(Integer likeNumber) {
        this.likeNumber = likeNumber;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public String getUrl() {
        return url;
    }

    public Integer getReadNumber() {
        return readNumber;
    }

    public Integer getLikeNumber() {
        return likeNumber;
    }

    public String getContent() {
        return content;
    }

    public String getWriter() {
        return writer;
    }

    public String getResource() {
        return resource;
    }

    public Date getPublishTime() {
        return publishTime;
    }
}