package com.wms.common;

import com.wms.entity.Student;
import com.wms.entity.Supervisor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {
    public static List<Student> excelToOrder(MultipartFile file){
        // 新建一个list，用于存放解析结果
        List<Student> list=new ArrayList<>();
        try {
            // 拿到对象
           // HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
            // 判断文件格式
            Workbook workbook;
            if (file.getOriginalFilename().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(file.getInputStream()); // 处理 .xlsx 文件
            } else if (file.getOriginalFilename().endsWith(".xls")) {
                workbook = new HSSFWorkbook(file.getInputStream()); // 处理 .xls 文件
            } else {
                throw new IllegalArgumentException("文件格式错误，仅支持 .xls 和 .xlsx 格式！");
            }
            // 一个excel可能有多个sheet,所以遍历sheet
            for (int i = 0; i <workbook.getNumberOfSheets() ; i++) {
                // 拿到sheet对象
                Sheet sheet = workbook.getSheetAt(i);
//                System.out.println("行数"+sheet.getPhysicalNumberOfRows());
                // 遍历每一行
                for (int j = 0; j <sheet.getPhysicalNumberOfRows() ; j++) {
                    //略过首行，即标题行
                    if(j==0){
                        continue;
                    }
                    // 拿到行的对象
                    Row row = sheet.getRow(j);
                    // 即使你的表格中没有空行，但也可能会被解析出一些空行，所以我们略过空行，否则会报错
                    if (row==null){
                        continue;
                    }
//                    System.out.println("列数"+row.getPhysicalNumberOfCells());
                    // new一个自己的实体类的对象，方便一会儿将解析出来的值存放进去
                    Student student = new Student();
                    // 注意下面这种方法是最简单但是也最死板的方法，就是固定excel的表头
                    // 每一列只能是对应的字段。一旦excel的某两列相互替换一下位置
                    // 那么写入student的值就乱了

                    // 遍历一行中的每一个单元格
                    for (int k = 0; k <row.getPhysicalNumberOfCells() ; k++) {
                        // 写固定了，每一列内容不能变
                        student.setPrimaryId(null);// 不设置主键，让数据库自动生成
                        if(k==0){
                            //学号
                            student.setId(row.getCell(k).getStringCellValue());
                        }
                        else if(k==1){
                            //名称
                            student.setName(row.getCell(k).getStringCellValue());
                        }
                        else if(k==2){
                            // 性别
                            student.setGender(row.getCell(k).getStringCellValue());
                        }
                        else if(k==3){
                            // 年级
                            student.setGrade(row.getCell(k).getStringCellValue());
                        }
                        else if(k==4){
                            // 专业
                            student.setMajor(row.getCell(k).getStringCellValue());
                        }
                        else if(k==5){
                            // 导师编号？
                            student.setSupervisorId(String.valueOf((int) row.getCell(k).getNumericCellValue()));
                        }
                        else if(k==6){
                            // 机房编号
                            student.setLabId((int) row.getCell(k).getNumericCellValue());
                        }
                        else if(k==7){
                            // 座位号
                            student.setSeatId((int) row.getCell(k).getNumericCellValue());
                        }
                        else ;
                    }
                    // 将order对象添加到list中
                    list.add(student);
                    System.out.println(student) ;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Supervisor> excelToOrder1(MultipartFile file){
        // 新建一个list，用于存放解析结果
        List<Supervisor> list=new ArrayList<>();
        try {
            // 拿到对象
            // HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
            // 判断文件格式
            Workbook workbook;
            if (file.getOriginalFilename().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(file.getInputStream()); // 处理 .xlsx 文件
            } else if (file.getOriginalFilename().endsWith(".xls")) {
                workbook = new HSSFWorkbook(file.getInputStream()); // 处理 .xls 文件
            } else {
                throw new IllegalArgumentException("文件格式错误，仅支持 .xls 和 .xlsx 格式！");
            }
            // 一个excel可能有多个sheet,所以遍历sheet
            for (int i = 0; i <workbook.getNumberOfSheets() ; i++) {
                // 拿到sheet对象
                Sheet sheet = workbook.getSheetAt(i);
//                System.out.println("行数"+sheet.getPhysicalNumberOfRows());
                // 遍历每一行
                for (int j = 0; j <sheet.getPhysicalNumberOfRows() ; j++) {
                    //略过首行，即标题行
                    if(j==0){
                        continue;
                    }
                    // 拿到行的对象
                    Row row = sheet.getRow(j);
                    // 即使你的表格中没有空行，但也可能会被解析出一些空行，所以我们略过空行，否则会报错
                    if (row==null){
                        continue;
                    }
//                    System.out.println("列数"+row.getPhysicalNumberOfCells());
                    // new一个自己的实体类的对象，方便一会儿将解析出来的值存放进去
                    Supervisor supervisor = new Supervisor();
                    // 注意下面这种方法是最简单但是也最死板的方法，就是固定excel的表头
                    // 每一列只能是对应的字段。一旦excel的某两列相互替换一下位置
                    // 那么写入student的值就乱了

                    // 遍历一行中的每一个单元格
                    for (int k = 0; k <row.getPhysicalNumberOfCells() ; k++) {
                        // 写固定了，每一列内容不能变
                        supervisor.setPrimaryId(null);// 不设置主键，让数据库自动生成
                        if(k==0){
                            //工号
                            supervisor.setId(String.valueOf((int) row.getCell(k).getNumericCellValue()));
                        }
                        else if(k==1){
                            //名称
                            supervisor.setName(row.getCell(k).getStringCellValue());
                        }
                        else if(k==2){
                            // 性别
                            supervisor.setGender(row.getCell(k).getStringCellValue());
                        }
                        else if(k==3){
                            // 职称
                            supervisor.setTitle(row.getCell(k).getStringCellValue());
                        }
                        else if(k==4){
                            // 办公室
                            supervisor.setOffice(row.getCell(k).getStringCellValue());
                        }
                        else if(k==5){
                            // 学生数量
                            supervisor.setStudentNum((int) row.getCell(k).getNumericCellValue());
                        }
                        else ;
                    }
                    // 将order对象添加到list中
                    list.add(supervisor);
                    System.out.println(supervisor) ;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
