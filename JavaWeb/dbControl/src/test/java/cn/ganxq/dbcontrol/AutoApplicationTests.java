package cn.ganxq.dbcontrol;

import cn.ganxq.dbcontrol.entity.User;
import cn.ganxq.dbcontrol.controller.UserController;
import cn.ganxq.dbcontrol.mapper.UserMapper;

import cn.ganxq.dbcontrol.service.IUserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

@SpringBootTest
class AutoApplicationTests {

  @Autowired
    private IUserService userService;

    @Autowired
    UserController userController;
    @Autowired
    UserMapper userMapper;
@Test
    void test1() {
        User user = new User();

        user.setId(123);
        user.setEmail(".000.");
        user.setUsername("张三0");
        user.setPassword("dfd2");
        boolean flag=userService.save(user);
        System.out.println(flag);
    }


@Test
    void test2() {
        User user = new User();

        user.setId(15);
        user.setEmail("889");
        user.setUsername("mkj");
        user.setPassword("dfr2");
       userMapper.insert2User(user);
    }

    @Test
    void test() {
        User user = new User();

        user.setId(15);
        user.setEmail("889");
        user.setUsername("mkj");
        user.setPassword("dfr2");
        User user1 = userController.findUsers();
        String jsonString = JSONObject.toJSONString(JSONObject.toJSON(user1));
        System.out.println(jsonString);
    }
}
