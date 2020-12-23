package com.arlenchen.service;

import com.arlenchen.pojo.Stu;

public interface StuService {
    Stu getStuInfo(int id);

    void saveStu();

    void updateStu(int id);

    void deleteStu(int id);
}
