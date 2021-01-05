package com.arlenchen.mapper;

import com.arlenchen.my.mapper.MyMapper;
import com.arlenchen.pojo.ItemsSpec;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author arlenchen
 */
public interface ItemsSpecMapper extends MyMapper<ItemsSpec> {

    /**
     * 根据ids查询商品规格
     *
     * @param paramsList id
     * @return List
     */
    List<ItemsSpec> listByIds(@Param("paramsList") List<String> paramsList);
}