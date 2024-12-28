package cn.ganxq.dbcontrol.controller;


import cn.ganxq.dbcontrol.common.CommonUtil;
import cn.ganxq.dbcontrol.entity.User;
import cn.ganxq.dbcontrol.service.IUserService;
import cn.ganxq.dbcontrol.model.QueryInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author ganxq
 * @since 2024-08-22
 */
@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;
    @ResponseBody
    @GetMapping("/save")
    public boolean getUserInfoById() {
        User user = new User();
        user.setId(14);
        user.setEmail("123");
        user.setUsername("张三30");
        user.setPassword("dfd24");
        return userService.save1(user);
    }
    /**
     * 创建用户
     *
     * @param user 用户信息
     * @return 创建成功的响应
     */
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            userService.createUser(user); // 调用服务层方法保存用户
            return ResponseEntity.ok("用户创建成功");
        } catch (Exception e) {
            // 处理异常并返回错误信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("创建用户失败: " + e.getMessage());
        }
    }

    @ResponseBody
    @GetMapping("/find")
    public User findUsers() {
        User user = new User();
        user.setId(10);
        user.setEmail("123");
        user.setUsername("张三30");
        user.setPassword("dfd24");
        return userService.getById(user);
    }
    @CrossOrigin(origins = "http://localhost:1648")
    @ResponseBody
    @GetMapping("/list")
    public String getUserList(QueryInfo queryInfo){
        System.out.println(queryInfo);
        int userCounts = userService.getUserCounts(queryInfo.getQuery());
        List<User> users = userService.userList(queryInfo);
        HashMap<String, Object> res = new HashMap<>();
        res.put("numbers",userCounts);
        res.put("data",users);
        System.out.println("总条数"+userCounts);
        String jsonString = JSON.toJSONString(res);
        return jsonString;

    }
    @PostMapping("/addRole")
    public JSONObject addRole(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "roleName,permissions");
        return userService.addRole(requestJson);
    }
}
