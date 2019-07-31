package smart.business.controller;

import smart.annotations.Aautowire;
import smart.annotations.Ccontroller;
import smart.business.service.LoginService;

/**
 * @author : pettygadfly@gmail.com
 * @description :
 * @date : 2019-07-31
 */
@Ccontroller("login")
public class Login {
    @Aautowire
    private LoginService loginService;
}
