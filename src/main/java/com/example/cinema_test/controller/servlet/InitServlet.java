package com.example.cinema_test.controller.servlet;


import com.example.cinema_test.model.entity.Role;
import com.example.cinema_test.model.entity.User;
import com.example.cinema_test.model.service.RoleService;
import com.example.cinema_test.model.service.UserService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(name = "InitServlet", urlPatterns = "/init.do", loadOnStartup = 1)
public class InitServlet extends HttpServlet {

    @Inject
    private UserService userService;

    @Inject
    private RoleService roleService;

    @Override
    public void init() throws ServletException {
        try {

            Role role = Role.builder().role("admin").build();
            roleService.save(role);

            User user =
                    User
                            .builder()
                            .username("admin")
                            .password("admin")
                            .role(role)
                            .locked(false)
                            .deleted(false)
                            .build();
            userService.save(user);
            log.info("Admin created !!! ");

        } catch (Exception e){
            log.error("Error in init : ", e);
        }
    }
}
