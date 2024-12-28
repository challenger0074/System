package cn.ganxq.dbcontrol.satoken.imp;

import cn.dev33.satoken.stp.StpInterface;
import cn.ganxq.dbcontrol.entity.Permissions;
import cn.ganxq.dbcontrol.entity.RolePermissions;
import cn.ganxq.dbcontrol.entity.UserRoles;
import cn.ganxq.dbcontrol.service.IPermissionsService;
import cn.ganxq.dbcontrol.service.IRolePermissionsService;
import cn.ganxq.dbcontrol.service.IUserRolesService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义权限加载接口实现类
 */
@Component    // 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {


    //    角色权限表
    @Autowired
    IRolePermissionsService rolePermissionsService;
    //    用户角色表
    @Autowired
    IUserRolesService userRolesService;
    //权限表
    @Autowired
    IPermissionsService permissionsService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        //        根据用户id从用户角色表中获取角色id  ,一个用户有多个角色,一个角色又有多个权限
        List<UserRoles> roleIds = userRolesService.list(new LambdaQueryWrapper<UserRoles>()
                .eq(UserRoles::getUserId,Integer.parseInt(loginId.toString())));
        List<Integer> rolesList = roleIds.stream().map(UserRoles::getRoleId).toList();
        if (!(rolesList.size() >0)){
//    没有任何权限
            return null;
        }
        // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询权限
        List<String> list = new ArrayList<String>();
        //根据角色id获取权限id
        rolesList.forEach(roleId -> {
            List<RolePermissions> rolePermissions = rolePermissionsService.list(new LambdaQueryWrapper<RolePermissions>().
                    eq(RolePermissions::getRoleId, roleId));
            rolePermissions.forEach(permissionsId -> {
                Permissions permissions = permissionsService.getById(permissionsId.getPermissionId());
                list.add(permissions.getPermissionCode());
            });
        });
        //admin:manager 允许管理其他用户
        list.add("101");
        list.add("user.add");
        list.add("user.update");
        list.add("user.get");
        // list.add("user.delete");
        list.add("art.*");
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<UserRoles> roleIds = userRolesService.list(new LambdaQueryWrapper<UserRoles>()
                .eq(UserRoles::getUserId,Integer.parseInt(loginId.toString())));
        // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询角色
        List<String> list = new ArrayList<String>();
        if (!(roleIds.size() >0)){
//            用户没有分配角色
            return null;
        }

        roleIds.forEach(roleId -> list.add(roleId.getRoleId().toString()));
        list.add("admin");
        list.add("super-admin");
        return list;
    }

}
