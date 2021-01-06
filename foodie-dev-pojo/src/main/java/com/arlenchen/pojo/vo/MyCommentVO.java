package com.arlenchen.pojo.vo;

import java.util.Date;

/**
 * @author arlenchen
 */
public class MyCommentVO {
    private String commentId;
    private String content;
    private Date createdTime;
    private String itemId;
    private String specName;
    private String itemImage;
    private String itemName;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String toString() {
        return "myCommentVO{" +
                "commentId='" + commentId + '\'' +
                ", content='" + content + '\'' +
                ", createdTime=" + createdTime +
                ", itemId='" + itemId + '\'' +
                ", specName='" + specName + '\'' +
                ", itemImage='" + itemImage + '\'' +
                ", itemName='" + itemName + '\'' +
                '}';
    }
}
