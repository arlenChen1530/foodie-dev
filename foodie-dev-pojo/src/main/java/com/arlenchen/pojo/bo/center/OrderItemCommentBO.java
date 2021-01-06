package com.arlenchen.pojo.bo.center;

import javax.persistence.Id;
import java.util.Date;

/**
 * 展示商品评价
 *
 * @author arlenchen
 */
public class OrderItemCommentBO {

    /**
     * id主键
     */
    @Id
    private String id;

    /**
     * 用户id 用户名须脱敏
     */
    private String userId;

    /**
     * 商品id
     */
    private String itemId;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 商品规格id 可为空
     */
    private String itemSpecId;

    /**
     * 规格名称 可为空
     */
    private String sepcName;

    /**
     * 评价等级 1：好评 2：中评 3：差评
     */
    private Integer commentLevel;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSpecId() {
        return itemSpecId;
    }

    public void setItemSpecId(String itemSpecId) {
        this.itemSpecId = itemSpecId;
    }

    public String getSepcName() {
        return sepcName;
    }

    public void setSepcName(String sepcName) {
        this.sepcName = sepcName;
    }

    public Integer getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(Integer commentLevel) {
        this.commentLevel = commentLevel;
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

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return "OrderItemCommentBO{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemSpecId='" + itemSpecId + '\'' +
                ", sepcName='" + sepcName + '\'' +
                ", commentLevel=" + commentLevel +
                ", content='" + content + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
