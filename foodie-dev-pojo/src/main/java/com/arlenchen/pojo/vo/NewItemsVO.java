package com.arlenchen.pojo.vo;

import java.util.List;

/**
 * 最新商品VO
 * @author arlenchen
 */
public class NewItemsVO {
    private Integer rootCatId;
    private String rootCatName;
    private String slogan;
    private String catImage;
    private String bgColor;
    private List<SimpleItemVO>  simpleItemVoList;

    public Integer getRootCatId() {
        return rootCatId;
    }

    public void setRootCatId(Integer rootCatId) {
        this.rootCatId = rootCatId;
    }

    public String getRootCatName() {
        return rootCatName;
    }

    public void setRootCatName(String rootCatName) {
        this.rootCatName = rootCatName;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public List<SimpleItemVO> getSimpleItemVoList() {
        return simpleItemVoList;
    }

    public void setSimpleItemVoList(List<SimpleItemVO> simpleItemVoList) {
        this.simpleItemVoList = simpleItemVoList;
    }

    @Override
    public String toString() {
        return "NewItemsVO{" +
                "rootCatId=" + rootCatId +
                ", rootCatName='" + rootCatName + '\'' +
                ", slogan='" + slogan + '\'' +
                ", catImage='" + catImage + '\'' +
                ", bgColor='" + bgColor + '\'' +
                ", simpleItemVoList=" + simpleItemVoList +
                '}';
    }
}
