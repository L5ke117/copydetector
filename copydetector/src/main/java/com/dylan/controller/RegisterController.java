package com.dylan.controller;

import com.dylan.entity.model.SysRole;
import com.dylan.entity.model.SysUser;
import com.dylan.entity.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RegisterController {
    @Autowired
    SysUserRepository sysUserRepository;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm() {
        return "registerForm";
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam("username") String username, @RequestParam("password") String password) {
        SysUser user = new SysUser();
        user.setUsername(username);
        user.setPassword(password);
        SysRole role = new SysRole();
        role.setId(new Long(2));
        role.setName("ROLE_USER");
        List<SysRole> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        sysUserRepository.save(user);
        return "login";
    }
}
