package com.gupao.edu.controller;

import com.dubber.dubbo.IOrderService;
import com.dubber.dubbo.OrderRequest;
import com.dubber.dubbo.OrderResponse;
import com.dubber.dubbo.user.IUserBalanceService;
import com.dubber.dubbo.user.dto.Response;
import com.gupao.edu.controller.support.ResponseData;
import com.gupao.edu.controller.support.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 */
@Controller
@RequestMapping("/index/")
public class IndexController extends BaseController {

    @Autowired
    IOrderService orderService;

    @Autowired
    IUserBalanceService userBalanceService;


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {
            return "/login";
        }
        return "/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "/login";
    }

    /**
     * 测试 dubbo-user updateUserBalance服务
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateUserBalance", method = RequestMethod.POST)
    @ResponseBody
    public String updateUserBalance(HttpServletRequest request){

        Response response = userBalanceService.updateUserBalance();
        return response.toString();
    }

    /**
     * 测试 dubbo-order doOrder 下单服务
     * @param request
     * @return
     */
    @RequestMapping(value = "/doOrder", method = RequestMethod.POST)
    @ResponseBody
    public String doOrder(HttpServletRequest request){
        OrderRequest request1 = new OrderRequest();
        OrderResponse response = orderService.doOrder(request1);
        return response.toString();
    }


    @RequestMapping(value = "/submitLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submitLogin(HttpServletRequest request, String loginname, String password) {
        /*OrderRequest request1 = new OrderRequest();
        OrderResponse response = orderService.doOrder(request1);*/

        if ("000000".equals("000000")) {
            request.getSession().setAttribute("user", "user");
            return setEnumResult(ResponseEnum.SUCCESS, "/");
        }
        ResponseData data = new ResponseData();
        data.setStatus(ResponseEnum.FAILED.getCode());
        return data;
    }

    /**
     * 退出
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        try {
            request.getSession().removeAttribute("user");
        } catch (Exception e) {
            LOG.error("errorMessage:" + e.getMessage());
        }
        return redirectTo("/index/login.shtml");
    }
}
