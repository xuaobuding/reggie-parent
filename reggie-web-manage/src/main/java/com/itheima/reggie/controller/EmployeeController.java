package com.itheima.reggie.controller;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageInfo;
import com.itheima.reggie.common.R;
import com.itheima.reggie.constant.EmployeeConstant;
import com.itheima.reggie.domain.Employee;
import com.itheima.reggie.server.EmployeeServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.StringUtils;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    EmployeeServer employeeServer;

    /**
     * 员工列表分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<PageInfo<Employee>> page(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                      @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                      String name){
        PageInfo<Employee> pageInfo = employeeServer.page(page,pageSize,name);
        return R.success(pageInfo);
    }
    @GetMapping("/list")
    public List<Employee> employees(){
        return employeeServer.employee();
    }
    @PostMapping("/login")//参数的接受
    public R<Employee> login(@RequestBody Employee employee, HttpSession session){
        //2.关键数据校验
        // 2.1 账号或者密码不能为空
        if(StringUtils.isBlank(employee.getUsername()) ||StringUtils.isBlank(employee.getPassword())){
            return R.error("账户或者密码不能为空");
        }
        //3.校验员工账户是否存在（有没有注册）
        Employee emp = employeeServer.getByUsername(employee.getUsername());
        if(emp == null){
            return R.error("账号不存在，请联系管理员注册");
    }
       // 3.2校验账户是否为禁用状态
        if (emp.getStatus()== EmployeeConstant.STATUS_DISABLE){
        return R.error("账户已禁用,请联系管理员");
        }
        //校验密码是否正确糊涂的工具包
        //使用equals时只考虑失败情况，因为正确程序继续执行往下走
        String md5Pwd = SecureUtil.md5(employee.getPassword());
        if (!emp.getPassword().equals(md5Pwd)){
            return R.error("密码错误");
        }
        //登录成功，保存用户到session中
        session.setAttribute(EmployeeConstant.SESSION,emp);
        //返回用户信息给前台保存,不能保存查出来的数据（包含密码信息）所以新建一个空对象赋值返回
        Employee data = new Employee();
        data.setId(emp.getId());
        data.setName(emp.getName());
        return R.success(data);
    }

    /**
     * 用户退出
     * @param session
     * @return
     */
    @PostMapping("/logout")
    public R logout(HttpSession session){
        //1.销毁session达到退出
        session.invalidate();
        //给用户返回结果
        return R.success("正在退出");
    }

    /**
     * 添加员工信息
     * @param employee
     * @param session
     * @return
     */
    @PostMapping
    public R<String> addEmployee(@RequestBody Employee employee,HttpSession session){
       //1.关键数据的校验
        String username = employee.getUsername();
        String phone = employee.getPhone();
        String idNumber = employee.getIdNumber();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(phone) || StrUtil.isBlank(idNumber)){
            return R.error("数据不完整请核实");
        }
      //2.校验用户是否注册了
        Employee emp = employeeServer.getByUsername(username);
        if (emp!=null){
            return R.error("账号["+username+"]已经注册了");
        }
        //2.1校验手机号是否已经注册了
        if (StrUtil.isBlank(phone) || employeeServer.getByphone(phone)!=null){
            return R.error("手机号["+username+"]已注册");
        }
        //2.2校验用户名是否已经注册
        if (StrUtil.isBlank(idNumber) || employeeServer.getByIdNumber(idNumber) != null){
            return R.error("身份证号["+username+"]已注册");
        }

        //3. 调用server进行保存
        //3.1设置用户的id使用雪花算法
//         employee.setId(IdUtil.getSnowflakeNextId());
        employee.setId(IdUtil.getSnowflakeNextId());
        //补全密码
        employee.setPassword(SecureUtil.md5("123456"));

        //3.2补全创建者的信息把用户信息保存到session中
        Employee user = (Employee) session.getAttribute(EmployeeConstant.SESSION);
        employee.setCreateUser(user.getId());
        employee.setUpdateUser(user.getId());
        employeeServer.add(employee);
        //返回结果
        return R.success("保存成功");
    }


}
