//package com.aprilz.tiny.config.provider;
//
//import org.springframework.security.core.userdetails.UserDetailsService;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public interface CustomUserDetailService extends UserDetailsService {
//
//    //该方法需自定义的UserDetailsService实现，表示该UserDetailsService匹配什么平台
//    Boolean supports(String platform);
//
//    /**
//     * 构造用户登录信息
//     *
//     * @param user  用户
//     * @param binds 员工绑定信息
//     **/
//    static LoginUser buildLoginUser(SysUser user, List<SysUserStaffBind> binds) {
//        LoginUser loginUser = new LoginUser();
//        SysUserStaffBind bind = SysLoginService.getLoginDomainId(binds);
//        List<Long> bindIds = binds.stream().map(SysUserStaffBind::getId).collect(Collectors.toList());
//        loginUser.setBindIds(bindIds);
//        loginUser.setUser(user);
//        loginUser.setUserId(user.getUserId());
//        loginUser.setRealName(user.getRealName());
//        loginUser.setDomainId(bind.getDomainId());
//        loginUser.setDomainName(bind.getDomainName());
//        loginUser.setStaffId(bind.getStaffId());
//        loginUser.setDeptId(bind.getDeptId());
//        loginUser.setPostId(bind.getPostId());
//        loginUser.setStaffBindId(bind.getId());
//
//        loginUser.setDeptName(bind.getDeptName());
//        loginUser.setPostName(bind.getPostName());
//        loginUser.setJobName(bind.getJobName());
//
//        return loginUser;
//    }
//
//    /**
//     * 切换用户登录信息
//     **/
//    static void switchLoginUser(LoginUser loginUser, SysUserStaffBind bind) {
//        loginUser.setDomainId(bind.getDomainId());
//        loginUser.setDomainName(bind.getDomainName());
//        loginUser.setStaffId(bind.getStaffId());
//        loginUser.setDeptId(bind.getDeptId());
//        loginUser.setPostId(bind.getPostId());
//        loginUser.setStaffBindId(bind.getId());
//        loginUser.setDeptName(bind.getDeptName());
//        loginUser.setPostName(bind.getPostName());
//        loginUser.setJobName(bind.getJobName());
//
//    }
//}
