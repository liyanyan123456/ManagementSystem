package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Menu;
import com.wms.entity.User;
import com.wms.service.MenuService;
import com.wms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wms
 * @since 2022-07-21
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public List<User> list(){
        return userService.list();
    }
    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no){
        List list = userService.lambdaQuery().eq(User::getNo,no).list();
        return list.size()>0?Result.suc(list):Result.fail();
    }
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody User user){
        return userService.save(user)?Result.suc():Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody User user){
        return userService.updateById(user)?Result.suc():Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return userService.removeById(id)?Result.suc():Result.fail();
    }

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        List list = userService.lambdaQuery()
                .eq(User::getNo,user.getNo())
                .list();

        if(list.size()>0){
            User user1 = (User)list.get(0);
            String sqlPassword=user1.getPassword();
            String inputPassword=user.getPassword();
            if(inputPassword==null){
                return Result.fail();
            }

            if (sqlPassword==null || sqlPassword.length()!=64){
                return Result.fail();
            }

            //获取盐值
            String salt=sqlPassword.substring(0,32);
            String calFinalPassword = DigestUtils.md5DigestAsHex((inputPassword+salt).getBytes());
            if( !(salt+calFinalPassword).equals(sqlPassword)){
                return Result.fail();
            };
            user1.setPassword(inputPassword);

            List menuList = menuService.lambdaQuery().like(Menu::getMenuright,user1.getRoleId()).list();
            HashMap res = new HashMap();
            res.put("user",user1);
            res.put("menu",menuList);
            return Result.suc(res);
        }
        return Result.fail();
    }

    //注册
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        // 校验是否传入了必须的字段
        if (user.getNo() == null || user.getPassword() == null || user.getName() == null) {
            return Result.fail();
        }
        // 检查学号是否已经存在
        boolean existingUser = userService.lambdaQuery()
                .eq(User::getNo, user.getNo())
                .count() > 0;

        if (existingUser) {
            return Result.fail().setMsg("学号/工号已存在，不能重复！");
        }
        // 设置默认值（根据业务需要）
        if (user.getRoleId() == null) {
            user.setRoleId(0); // 默认角色（例如：普通用户）
        }
        if (user.getIsvalid() == null) {
            user.setIsvalid("Y"); // 默认角色（例如：普通用户）
        }
        String salt= UUID.randomUUID().toString().replace("-","");
        String finalPassword= DigestUtils.md5DigestAsHex((user.getPassword()+salt).getBytes());
        user.setPassword(salt+finalPassword);

        // 保存用户信息到数据库
        boolean success = userService.save(user);

        // 返回结果
        return success ? Result.suc() : Result.fail();
    }

    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user){
        return userService.updateById(user);
    }
    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }
    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return userService.removeById(id);
    }

    //查询（模糊、匹配）
    @PostMapping("/listP")
    public Result listP(@RequestBody User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(user.getName())){
            lambdaQueryWrapper.like(User::getName,user.getName());
        }

        return Result.suc(userService.list(lambdaQueryWrapper));
    }

    @PostMapping("/listPage")
//    public List<User> listPage(@RequestBody HashMap map){
    public List<User> listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        System.out.println("name==="+(String)param.get("name"));
        /*System.out.println("no==="+(String)param.get("no"));*/
        /*LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(User::getName,user.getName());

        return userService.list(lambdaQueryWrapper);*/

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName,name);


        IPage result = userService.page(page,lambdaQueryWrapper);

        System.out.println("total=="+result.getTotal());

        return result.getRecords();
    }

    @PostMapping("/listPageC")
    public List<User> listPageC(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        System.out.println("name==="+(String)param.get("name"));



        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName,name);


        //IPage result = userService.pageC(page);
        IPage result = userService.pageCC(page,lambdaQueryWrapper);

        System.out.println("total=="+result.getTotal());

        return result.getRecords();
    }

    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String sex = (String)param.get("sex");
        String roleId = (String)param.get("roleId");

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(User::getName,name);
        }
        if(StringUtils.isNotBlank(sex)){
            lambdaQueryWrapper.eq(User::getSex,sex);
        }
        if(StringUtils.isNotBlank(roleId)){
            lambdaQueryWrapper.eq(User::getRoleId,roleId);
        }

        //IPage result = userService.pageC(page);
        IPage result = userService.pageCC(page,lambdaQueryWrapper);

        System.out.println("total=="+result.getTotal());

        return Result.suc(result.getRecords(),result.getTotal());
    }
}
