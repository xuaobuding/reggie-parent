package com.itheima.reggie.server.impl;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.reggie.mapper.EmployeeMapper;
import com.itheima.reggie.domain.Employee;
import com.itheima.reggie.server.EmployeeServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServerIpml implements EmployeeServer {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 查询所有员工
     * @return
     */
    @Override
    public List<Employee> employee() {
        return employeeMapper.employee();
    }

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    @Override
    public Employee getByUsername(String username) {
        return employeeMapper.getByUsername(username);
    }

    /**
     * 查询用户传进来的手机号
     * @param phone
     * @return
     */
    @Override
    public Employee getByphone(String phone) {
        return employeeMapper.getByphone(phone);
    }

    /**
     * 根据id查询身份证号
     * @param idNumber
     * @return
     */
    @Override
    public Employee getByIdNumber(String idNumber) {
        return employeeMapper.getByIdNumber(idNumber);
    }

    /**
     * 添加员工信息
     * @param employee
     */
    @Override
    public void add(Employee employee) {
        employeeMapper.insert(employee);
    }

    /**
     * 员工列表分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public PageInfo<Employee> page(Integer page, Integer pageSize, String name) {
        //1.分页查询
        PageHelper.startPage(page,pageSize);
        //模糊查询根据username
        if (StrUtil.isNotBlank(name)){
            name = "%"+name+"%";
        }
        //调用mapper查询
        List<Employee> user = employeeMapper.page(name);

        //封装查询结果
        PageInfo<Employee> pageInfo = new PageInfo<>(user);
        return pageInfo;
    }
}
