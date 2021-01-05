package com.arlenchen.mapper;


import org.apache.ibatis.annotations.Param;

/**
 * @author arlenchen
 */
public interface ItemsSpecMapperCustom {
    /**
     * 减少库存
     *
     * @param specId       规格id
     * @param pendingCount 库存数量
     * @return 修改结果
     */
    int decreaseItemSpecStock(@Param("specId") String specId, @Param("pendingCount") Integer pendingCount);

}