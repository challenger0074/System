package cn.ganxq.dbcontrol.service.impl;

import cn.ganxq.dbcontrol.common.CommonUtil;
import cn.ganxq.dbcontrol.common.constants.ErrorEnum;
import cn.ganxq.dbcontrol.entity.RolePermissions;
import cn.ganxq.dbcontrol.entity.Roles;
import cn.ganxq.dbcontrol.entity.User;
import cn.ganxq.dbcontrol.entity.UserRoles;
import cn.ganxq.dbcontrol.mapper.RolePermissionsMapper;
import cn.ganxq.dbcontrol.mapper.RolesMapper;
import cn.ganxq.dbcontrol.mapper.UserMapper;
import cn.ganxq.dbcontrol.mapper.UserRolesMapper;
import cn.ganxq.dbcontrol.model.QueryInfo;
import cn.ganxq.dbcontrol.service.IUserService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author ganxq
 * @since 2024-08-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    UserMapper mapper;
    @Autowired
    UserRolesMapper rolesMapper;
    @Override
    public boolean save1(User entity) {

        return mapper.insert2User(entity);
    }

    @Override
    public int getUserCounts(String username) {
        // 模糊查询
        int userCounts = mapper.getUserCounts("%"+username+"%");
        return userCounts;
    }

    @Override
    public List<User> userList(QueryInfo queryInfo) {

        //从哪里开始查询
        int pageStart = (queryInfo.getPageNum()-1)*queryInfo.getPageSize();
        System.out.println("$$$$$$$$$$$$$$$$$"+pageStart);
        List<User> userList = mapper.getAllUser("%"+queryInfo.getQuery()+"%",pageStart,queryInfo.getPageSize());
       int SIZE = queryInfo.getPageSize();
        System.out.println("$$$$$$$$$$$$$$$$$"+SIZE);
        return userList;
    }
/*1.普通用户 2.超级管理员 3.管理员*/
    @Override
    public void createUser(User user) {
        mapper.insert(user);//mybatisplus封装
        int roleId = 0;
        if (user.getRole().equals("普通用户")) {
            roleId = 1;
        } else if (user.getRole().equals("超级管理员")) {
            roleId = 2;
        } else if (user.getRole().equals("管理员")) {
            roleId = 3;
        }
        UserRoles roles = new UserRoles();
        roles.setRoleId(roleId);
        roles.setUserId(user.getId());
        rolesMapper.insert(roles);
    }

    /**
     * 用户列表
     */
    public JSONObject listUser(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = mapper.countUser(jsonObject);
        List<JSONObject> list = mapper.listUser(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 添加用户
     */
    public JSONObject addUser(JSONObject jsonObject) {
        int exist = mapper.queryExistUsername(jsonObject);
        if (exist > 0) {
            return CommonUtil.errorJson(ErrorEnum.E_10009);
        }
        mapper.addUser(jsonObject);
        mapper.batchAddUserRole(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 查询所有的角色
     * 在添加/修改用户的时候要使用此方法
     */
    public JSONObject getAllRoles() {
        List<JSONObject> roles = mapper.getAllRoles();
        return CommonUtil.successPage(roles);
    }

    /**
     * 修改用户
     */
    public JSONObject updateUser(JSONObject jsonObject) {
        //不允许修改管理员信息
        if (jsonObject.getIntValue("userId") == 10001) return CommonUtil.successJson();
        mapper.updateUser(jsonObject);
        mapper.removeUserAllRole(jsonObject.getIntValue("userId"));
        if (!jsonObject.getJSONArray("roleIds").isEmpty()) {
            mapper.batchAddUserRole(jsonObject);
        }
        return CommonUtil.successJson();
    }

    /**
     * 角色列表
     */
    public JSONObject listRole() {
        List<JSONObject> roles = mapper.listRole();
        return CommonUtil.successPage(roles);
    }

    /**
     * 查询所有权限, 给角色分配权限时调用
     */

    public JSONObject listAllPermission() {
        List<JSONObject> permissions = mapper.listAllPermission();
        return CommonUtil.successPage(permissions);
    }

    /**
     * 添加角色
     */
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    public JSONObject addRole(JSONObject jsonObject) {
        mapper.insertRole(jsonObject);
        mapper.insertRolePermission(jsonObject.getString("roleId"), (List<Integer>) jsonObject.get("permissions"));
        return CommonUtil.successJson();
    }

    /**
     * 修改角色
     */
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    public JSONObject updateRole(JSONObject jsonObject) {
        String roleId = jsonObject.getString("roleId");
        List<Integer> newPerms = (List<Integer>) jsonObject.get("permissions");
        JSONObject roleInfo = mapper.getRoleAllInfo(jsonObject);
        Set<Integer> oldPerms = (Set<Integer>) roleInfo.get("permissionIds");
        //修改角色名称
        updateRoleName(jsonObject, roleInfo);
        //添加新权限
        saveNewPermission(roleId, newPerms, oldPerms);
        //移除旧的不再拥有的权限
        removeOldPermission(roleId, newPerms, oldPerms);
        return CommonUtil.successJson();
    }

    /**
     * 修改角色名称
     */
    public void updateRoleName(JSONObject paramJson, JSONObject roleInfo) {
        String roleName = paramJson.getString("roleName");
        if (!roleName.equals(roleInfo.getString("roleName"))) {
            mapper.updateRoleName(paramJson);
        }
    }

    /**
     * 为角色添加新权限
     */
    public void saveNewPermission(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
        List<Integer> waitInsert = new ArrayList<>();
        for (Integer newPerm : newPerms) {
            if (!oldPerms.contains(newPerm)) {
                waitInsert.add(newPerm);
            }
        }
        if (waitInsert.size() > 0) {
            mapper.insertRolePermission(roleId, waitInsert);
        }
    }

    /**
     * 删除角色 旧的 不再拥有的权限
     */
    public void removeOldPermission(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
        List<Integer> waitRemove = new ArrayList<>();
        for (Integer oldPerm : oldPerms) {
            if (!newPerms.contains(oldPerm)) {
                waitRemove.add(oldPerm);
            }
        }
        if (waitRemove.size() > 0) {
            mapper.removeOldPermission(roleId, waitRemove);
        }
    }

    /**
     * 删除角色
     */
    @Transactional(rollbackFor = Exception.class)
    public JSONObject deleteRole(JSONObject jsonObject) {
        String roleId = jsonObject.getString("roleId");
        int userCount = mapper.countRoleUser(roleId);
        if (userCount > 0) {
            return CommonUtil.errorJson(ErrorEnum.E_10008);
        }
        mapper.removeRole(roleId);
        mapper.removeRoleAllPermission(roleId);
        return CommonUtil.successJson();
    }
    // 其他方法


}
