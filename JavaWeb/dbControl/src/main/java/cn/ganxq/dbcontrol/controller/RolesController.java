package cn.ganxq.dbcontrol.controller;

import cn.ganxq.dbcontrol.common.CommonUtil;
import cn.ganxq.dbcontrol.service.IUserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

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
@RequestMapping("/roles")
public class RolesController {
    @Autowired
     IUserService userService;
    @GetMapping("/listRole")
    public JSONObject listRole() {
        System.out.println("角色访问成功");
        JSONObject json=new JSONObject();
        json=userService.listRole();
        System.out.println(json.toJSONString());
        return json;
    }
    @PostMapping("/addRole")
    public JSONObject addRole(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "roleName,permissions");
        return userService.addRole(requestJson);
    }
    /**
     * 修改角色
     */
    @PostMapping("/updateRole")
    public JSONObject updateRole(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "roleId,roleName,permissions");
        return userService.updateRole(requestJson);
    }
    /**
     * 删除角色
     */
    @PostMapping("/deleteRole")
    public JSONObject deleteRole(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "roleId");
        return userService.deleteRole(requestJson);
    }
}
