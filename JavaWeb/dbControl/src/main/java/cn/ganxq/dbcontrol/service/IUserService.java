package cn.ganxq.dbcontrol.service;

import cn.ganxq.dbcontrol.entity.User;
import cn.ganxq.dbcontrol.model.QueryInfo;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author ganxq
 * @since 2024-08-21
 */

public interface IUserService extends IService<User> {

    public boolean save1(User entity);

    public int getUserCounts(String username);

    public List<User> userList(QueryInfo queryInfo);

    public void createUser(User user); // 添加创建用户的方法
    /**
     * 查询所有权限, 给角色分配权限时调用
     */
    public JSONObject listAllPermission();

    /**
     * 角色列表
     */
    JSONObject listRole();

    JSONObject addRole(JSONObject requestJson);

    /**
     * 用户列表
     */
    public JSONObject listUser(JSONObject jsonObject);
    /**
     * 添加用户
     */
    public JSONObject addUser(JSONObject jsonObject);
    /**
     * 查询所有的角色
     * 在添加/修改用户的时候要使用此方法
     */
    public JSONObject getAllRoles();
    /**
     * 修改用户
     */
    public JSONObject updateUser(JSONObject jsonObject);
    /**
     * 修改角色
     */
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    public JSONObject updateRole(JSONObject jsonObject);
    /**
     * 为角色添加新权限
     */
    public void saveNewPermission(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms);
    public void updateRoleName(JSONObject paramJson, JSONObject roleInfo);
    /**
     * 删除角色 旧的 不再拥有的权限
     */
    public void removeOldPermission(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms);
    /**
     * 删除角色
     */
    @Transactional(rollbackFor = Exception.class)
    public JSONObject deleteRole(JSONObject jsonObject);

}
