package com.wms.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.ExcelUtils;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Student;
import com.wms.entity.StudentDTO;
import com.wms.entity.Supervisor;
import com.wms.mapper.StudentMapper;
import com.wms.service.MenuService;
import com.wms.service.StudentService;
import com.wms.service.SupervisorService;
import javafx.scene.control.TextArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liyanyan
 * @since 2024-12-07
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private SupervisorService supervisorService;

    @GetMapping("/list")
    public List<Student> list(){
        return studentService.list();
    }

    @GetMapping("/findById")
    public Result findById(@RequestParam String id){
        List list = studentService.lambdaQuery().eq(Student::getId,id).list();
        return list.size()>0?Result.suc(list):Result.fail();
    }
    @GetMapping("/findSupervisorBySupervisorId")//查找老师姓名
    public Result findSupervisorById(@RequestParam String id){
        Supervisor supervisor=supervisorService.lambdaQuery().eq(Supervisor::getId,id).one();
        return supervisor !=null?Result.suc(supervisor):Result.fail();
    }
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Student student){
        return studentService.save(student)?Result.suc():Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Student student){
        System.out.println(student);
        // 检查学号是否已经存在
        Student existingStudent = studentService.lambdaQuery()
                .eq(Student::getId, student.getId())
                .ne(Student::getPrimaryId, student.getPrimaryId()) // 排除当前记录
                .one();

        if (existingStudent != null) {
            return Result.fail().setMsg("学号已存在，不能重复！");
        }

        return studentService.updateById(student)?Result.suc():Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        System.out.println("学号："+id);
        // 根据学号查找对应的主键_id
        Student student = studentService.lambdaQuery()
                .eq(Student::getId, id)
                .one();
        if (student == null) {
            return Result.fail().setMsg("未找到对应的学生记录，学号: " + id);
        }
        System.out.println("student："+student);


        // 获取主键_id并删除
        boolean removed = studentService.removeById(student.getPrimaryId());
        return removed ? Result.suc().setMsg("删除成功") : Result.fail().setMsg("删除失败");

    }

    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody Student student){
        return studentService.updateById(student);
    }
    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody Student student){
        return studentService.saveOrUpdate(student);
    }
    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return studentService.removeById(id);
    }

    //查询（模糊、匹配）
    @PostMapping("/listP")
    public Result listP(@RequestBody Student student){
        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(student.getName())){
            lambdaQueryWrapper.like(Student::getName,student.getName());
        }

        return Result.suc(studentService.list(lambdaQueryWrapper));
    }

    @PostMapping("/listPage")
//    public List<User> listPage(@RequestBody HashMap map){
    public List<Student> listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        System.out.println("name==="+(String)param.get("name"));
        /*System.out.println("no==="+(String)param.get("no"));*/
        /*LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(User::getName,user.getName());

        return userService.list(lambdaQueryWrapper);*/

        Page<Student> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(Student::getName,name);


        IPage result = studentService.page(page,lambdaQueryWrapper);

        System.out.println("total=="+result.getTotal());

        return result.getRecords();
    }

    @PostMapping("/listPageC")
    public List<Student> listPageC(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        System.out.println("name==="+(String)param.get("name"));



        Page<Student> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(Student::getName,name);


        //IPage result = userService.pageC(page);
        IPage result = studentService.pageCC(page,lambdaQueryWrapper);

        System.out.println("total=="+result.getTotal());

        return result.getRecords();
    }

    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String id = (String)param.get("id");
        String gender = (String)param.get("gender");
        String grade = (String)param.get("grade");
        String supervisorName = (String)param.get("supervisorName");

//        String roleId = (String)param.get("roleId");

        Page<StudentDTO> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        QueryWrapper<StudentDTO> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            queryWrapper.like("s.name", name); // 手动指定表的字段名
        }
        if(StringUtils.isNotBlank(id) && !"null".equals(id)){
            queryWrapper.eq("s.id", id );
        }
        if (StringUtils.isNotBlank(gender)) {
            queryWrapper.eq("s.gender", gender);
        }
        if (StringUtils.isNotBlank(grade)) {
            queryWrapper.eq("s.grade", grade);
        }
        if(StringUtils.isNotBlank(supervisorName)){
            queryWrapper.like("t.name", supervisorName);
        }


       // 自定义查询，关联老师表返回姓名
        IPage<StudentDTO> result = studentService.pageWithSupervisorName(page,queryWrapper);
       // IPage result = studentService.pageCC(page,lambdaQueryWrapper);

        System.out.println("total=="+result.getTotal());

        return Result.suc(result.getRecords(),result.getTotal());
    }




    //解析上传的excel文件
    @PostMapping("/uploadExcel")
    public  Result importExcel(@RequestParam("file") MultipartFile file){
        System.out.println("收到文件");
        List<Student> students = ExcelUtils.excelToOrder(file);


        return Result.suc(students);

    }

    //excel文件导入数据库
    @PostMapping("/importExcel")
    public Result importData(@RequestBody List<Student> students) {
        try {
            System.out.println("开始导入");
            List<String> failedIds = new ArrayList<>();
            int successCount = 0;
            for (Student student : students) {
                boolean exists = studentService.lambdaQuery()
                        .eq(Student::getId, student.getId())
                        .count() > 0;
                if (exists) {
                    failedIds.add(student.getId());
                    continue;
                }
                studentService.save(student);
                successCount++;
            }
            System.out.println("导入成功条数: " + successCount);
            System.out.println("重复学号: " + failedIds);
            if(successCount==0){
                return Result.fail().setMsg("无有效数据，重复学号: " + failedIds);
            }else{
                return Result.suc().setMsg("导入成功条数: " + successCount+"条");
            }

        } catch (Exception e) {
            return Result.fail().setMsg("数据导入失败：" + e.getMessage());
        }
    }


}

