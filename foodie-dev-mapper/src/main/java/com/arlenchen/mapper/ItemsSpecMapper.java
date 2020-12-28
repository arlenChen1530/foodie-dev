package com.arlenchen.mapper;

import com.arlenchen.pojo.ItemsSpec;
import com.arlenchen.my.mapper.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ItemsSpecMapper extends MyMapper<ItemsSpec> {

    List<ItemsSpec> ListByIds(@Param("paramsList") List<String> paramsList);
}