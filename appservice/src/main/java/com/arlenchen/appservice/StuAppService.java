package com.arlenchen.appservice;

import com.arlenchen.pojo.Stu;

public interface StuAppService {
    Stu getStuInfo(int id);

    void saveStu();

    void updateStu(int id);

    void deleteStu(int id);
}
