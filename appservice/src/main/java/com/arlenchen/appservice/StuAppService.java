package com.arlenchen.appservice;

import com.arlenchen.pojo.Stu;

/**
 * @author arlenchen
 */
public interface StuAppService {
    /**
     * 查询
     * @param id ID
     * @return Stu
     */
    Stu getStuInfo(int id);

    /**
     * 保存
     */
    void saveStu();

    /**
     * 修改
     * @param id id
     */
    void updateStu(int id);

    /**
     * 删除
     * @param id id
     */
    void deleteStu(int id);
}
