package cn.ganxq.dbcontrol.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.ganxq.dbcontrol.entity.Mainmenu;
import cn.ganxq.dbcontrol.mapper.MainmenuMapper;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ganxq
 * @since 2024-10-15
 */
@Controller
@RequestMapping("/menu")
public class MainmenuController {
    @Autowired
    MainmenuMapper mapper;
    @CrossOrigin(origins = "http://localhost:1648")
    @ResponseBody
    @GetMapping("/find")
    public String findMenus() {
        //查询当前账号是否含有指定权限，返回true或false
        boolean b = StpUtil.hasPermission("admin:manager");//是否有管理权限
        HashMap<String, Object> data= new HashMap<>();
        List<Mainmenu> mainMenus = mapper.getMainMenus();
        if (!b){
            mainMenus.remove(5);
            System.out.println("不具有管理员权限"+b);
        }
        System.out.println(mainMenus.toArray());
        data.put("menus",mainMenus);
        data.put("flag",200);
        String jsonString = JSON.toJSONString(data);
        System.out.println("菜单访问成功");
        return jsonString;
    }
    @ResponseBody
    @GetMapping("/test")
    public List<Mainmenu> findUsers() {
        HashMap<String, Object> data= new HashMap<>();
        List<Mainmenu> mainMenus = mapper.getMainMenus();
        data.put("menus",mainMenus);
        data.put("flag",200);
        String jsonString = JSON.toJSONString(data);
        return mainMenus;
    }
}
