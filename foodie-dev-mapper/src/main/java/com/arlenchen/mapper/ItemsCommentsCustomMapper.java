package com.arlenchen.mapper;

import com.arlenchen.pojo.vo.MyCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author arlenchen
 */
public interface ItemsCommentsCustomMapper {
    /**
     * 保存商品评价
     *
     * @param map 评价数据
     */
    void saveComments(@Param("paramsMap") Map<String, Object> map);

    /**
     * 查询我的所有评价
     *
     * @param map map
     * @return list
     */
    List<MyCommentVO> queryMyComments(@Param("paramsMap") Map<String, Object> map);
}