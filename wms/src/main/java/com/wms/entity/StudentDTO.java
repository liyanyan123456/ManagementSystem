package com.wms.entity;

import lombok.Data;


@Data
public class StudentDTO {
        private Integer primaryId;//student的主键
        private String id; // 学号
        private String name; // 学生姓名
        private String gender; // 性别
        private String grade; // 年级
        private String major; // 专业
        private String supervisorId; // 导师工号
        private String supervisorName; // 导师姓名
        private Integer labId;//机房
        private Integer seatId;//机位
}


