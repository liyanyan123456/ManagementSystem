package com.wms.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Lab;
import com.wms.service.LabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liyanyan
 * @since 2025-02-20
 */
@RestController
@RequestMapping("/lab")
public class LabController {
    @Autowired
    private LabService labService;

    @GetMapping("/list")
    public List<Lab> list(){
        return labService.list();
    }

    @PostMapping("/save")
    public Result save(@RequestBody Lab lab){
        return labService.save(lab)?Result.suc():Result.fail();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Lab lab){
        System.out.println(lab);
        // 检查机房是否已经存在
        Lab existingLab = labService.lambdaQuery()
                .eq(Lab::getName, lab.getName())
                .ne(Lab::getId, lab.getId()) // 排除当前记录
                .one();

        if (existingLab != null) {
            return Result.fail().setMsg("机房已存在，不能重复！");
        }

        return labService.updateById(lab)?Result.suc():Result.fail();
    }

    @GetMapping("/del")
    public Result del(@RequestParam String id){
        System.out.println("机房ID号："+id);
        // 根据机房ID号查找对应的主键id
        Lab lab = labService.lambdaQuery()
                .eq(Lab::getId, id)
                .one();
        if (lab == null) {
            return Result.fail().setMsg("未找到对应的机房记录，机房ID号: " + id);
        }
        System.out.println("lab："+lab);


        // 获取主键id并删除
        boolean removed = labService.removeById(lab.getId());
        return removed ? Result.suc().setMsg("删除成功") : Result.fail().setMsg("删除失败");

    }

    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query) {
        try {
            HashMap param = query.getParam();
            String name = (String) param.get("name");



            Page<Lab> page = new Page();
            page.setCurrent(query.getPageNum());
            page.setSize(query.getPageSize());

            LambdaQueryWrapper<Lab> lambdaQueryWrapper = new LambdaQueryWrapper();
            if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
                lambdaQueryWrapper.like(Lab::getName, name);
            }
//            if (StringUtils.isNotBlank(gender)) {
//                lambdaQueryWrapper.eq(Supervisor::getGender, gender);
//            }

            IPage result = labService.pageCC(page, lambdaQueryWrapper);

            System.out.println("total==" + result.getTotal());

            return Result.suc(result.getRecords(), result.getTotal());
        }catch (Exception e) {
            e.printStackTrace();
            return Result.fail().setMsg("查询失败" );
        }
    }

    //查询
    @PostMapping("/listPage")
    public List<Lab> listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        System.out.println("name==="+(String)param.get("name"));
        /*System.out.println("no==="+(String)param.get("no"));*/
        /*LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(User::getName,user.getName());

        return userService.list(lambdaQueryWrapper);*/

        Page<Lab> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Lab> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(Lab::getName,name);


        IPage result = labService.page(page,lambdaQueryWrapper);

        System.out.println("total=="+result.getTotal());

        return result.getRecords();
    }

}
