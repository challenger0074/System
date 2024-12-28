package cn.ganxq.dbcontrol.controller;

import cn.ganxq.dbcontrol.service.IUserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ganxq
 * @since 2024-11-01
 */
@ResponseBody
@Controller
@RequestMapping("/permissions")
public class PermissionsController {
    @Autowired
    IUserService userService;
    /**
     * 查询所有权限, 给角色分配权限时调用
     */
    @GetMapping("/listAllPermission")
    public JSONObject listAllPermission() {
        return userService.listAllPermission();
    }
}
