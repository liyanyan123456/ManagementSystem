package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.ExcelUtils;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Student;
import com.wms.service.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wms.entity.Supervisor;
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
 * @since 2024-12-13
 */
@RestController
@RequestMapping("/supervisor")
public class SupervisorController {
    @Autowired
    private SupervisorService supervisorService;

    @GetMapping("/list")
    public List<Supervisor> list(){
        return supervisorService.list();
    }

    @GetMapping("/findById")
    public Result findById(@RequestParam String id){
        List list = supervisorService.lambdaQuery().eq(Supervisor::getId,id).list();
        return list.size()>0?Result.suc(list):Result.fail();
    }
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Supervisor supervisor){
        return supervisorService.save(supervisor)?Result.suc():Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Supervisor supervisor){
        return supervisorService.updateById(supervisor)?Result.suc():Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        System.out.println("工号："+id);
        // 根据学号查找对应的主键_id
        Supervisor supervisor = supervisorService.lambdaQuery()
                .eq(Supervisor::getId, id)
                .one();
        if (supervisor== null) {
            return Result.fail().setMsg("未找到对应的老师记录，工号: " + id);
        }
        System.out.println("supervisor："+supervisor);

        // 获取主键_id并删除
        boolean removed = supervisorService.removeById(supervisor.getPrimaryId());
        return removed ? Result.suc().setMsg("删除成功") : Result.fail().setMsg("删除失败");

//        return supervisorService.removeById(id)?Result.suc():Result.fail();
    }

    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody Supervisor supervisor){
        return supervisorService.updateById(supervisor);
    }
    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody Supervisor supervisor){
        return supervisorService.saveOrUpdate(supervisor);
    }
    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return supervisorService.removeById(id);
    }

    //查询（模糊、匹配）
    @PostMapping("/listP")
    public Result listP(@RequestBody Supervisor supervisor){
        LambdaQueryWrapper<Supervisor> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(supervisor.getName())){
            lambdaQueryWrapper.like(Supervisor::getName,supervisor.getName());
        }

        return Result.suc(supervisorService.list(lambdaQueryWrapper));
    }

    @PostMapping("/listPage")
//    public List<User> listPage(@RequestBody HashMap map){
    public List<Supervisor> listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        System.out.println("name==="+(String)param.get("name"));
        /*System.out.println("no==="+(String)param.get("no"));*/
        /*LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(User::getName,user.getName());

        return userService.list(lambdaQueryWrapper);*/

        Page<Supervisor> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Supervisor> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(Supervisor::getName,name);


        IPage result = supervisorService.page(page,lambdaQueryWrapper);

        System.out.println("total=="+result.getTotal());

        return result.getRecords();
    }

    @PostMapping("/listPageC")
    public List<Supervisor> listPageC(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        System.out.println("name==="+(String)param.get("name"));



        Page<Supervisor> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Supervisor> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(Supervisor::getName,name);


        //IPage result = userService.pageC(page);
        IPage result = supervisorService.pageCC(page,lambdaQueryWrapper);

        System.out.println("total=="+result.getTotal());

        return result.getRecords();
    }

    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query) {
        try {
            HashMap param = query.getParam();
            String name = (String) param.get("name");
            String gender = (String) param.get("gender");
//        String roleId = (String)param.get("roleId");

            Page<Supervisor> page = new Page();
            page.setCurrent(query.getPageNum());
            page.setSize(query.getPageSize());

            LambdaQueryWrapper<Supervisor> lambdaQueryWrapper = new LambdaQueryWrapper();
            if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
                lambdaQueryWrapper.like(Supervisor::getName, name);
            }
            if (StringUtils.isNotBlank(gender)) {
                lambdaQueryWrapper.eq(Supervisor::getGender, gender);
            }
//        if(StringUtils.isNotBlank(roleId)){
//            lambdaQueryWrapper.eq(Student::getRoleId,roleId);
//        }

            //IPage result = userService.pageC(page);
            IPage result = supervisorService.pageCC(page, lambdaQueryWrapper);

            System.out.println("total==" + result.getTotal());

            return Result.suc(result.getRecords(), result.getTotal());
        }catch (Exception e) {
            e.printStackTrace();
            return Result.fail().setMsg("查询失败" );
        }
    }

    //解析上传的excel文件
    @PostMapping("/uploadExcel")
    public  Result importExcel(@RequestParam("file") MultipartFile file){
        System.out.println("收到文件");
        List<Supervisor> supervisors = ExcelUtils.excelToOrder1(file);


        return Result.suc(supervisors);

    }

    //excel文件导入数据库
    @PostMapping("/importExcel")
    public Result importData(@RequestBody List<Supervisor> supervisors) {
        try {
            System.out.println("开始导入");
            List<String> failedIds = new ArrayList<>();
            int successCount = 0;
            for (Supervisor supervisor : supervisors) {
                boolean exists = supervisorService.lambdaQuery()
                        .eq(Supervisor::getId, supervisor.getId())
                        .count() > 0;
                if (exists) {
                    failedIds.add(supervisor.getId());
                    continue;
                }
                supervisorService.save(supervisor);
                successCount++;
            }
            System.out.println("导入成功条数: " + successCount);
            System.out.println("重复学号: " + failedIds);
            if(successCount==0){
                return Result.fail().setMsg("无有效数据，重复工号: " + failedIds);
            }else{
                return Result.suc().setMsg("导入成功条数: " + successCount+"条");
            }

        } catch (Exception e) {
            return Result.fail().setMsg("数据导入失败：" + e.getMessage());
        }
    }


}
