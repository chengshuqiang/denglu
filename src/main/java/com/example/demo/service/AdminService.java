package com.example.demo.service;

import com.example.demo.mapper.AdminMapper;
import com.example.demo.model.Admin;
import com.example.demo.model.AdminDTO;
import com.example.demo.util.IDUtil;
import com.example.demo.util.JSONUtil;
import com.example.demo.util.JWTUtils;
import com.example.demo.util.result.ResultDataPageUtil;
import com.example.demo.util.result.Response;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    private final AdminMapper adminMapper;

    private final JWTUtils jwtUtils;

    public AdminService(AdminMapper adminMapper, JWTUtils jwtUtils) {
        this.adminMapper = adminMapper;
        this.jwtUtils = jwtUtils;
    }

    public String login(String json) {
        Response response = new Response();
        if (JSONUtil.isFieldNull(json, "username") ||
                JSONUtil.isFieldNull(json, "password")) {
            return response.toJson();
        }
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        String username = jsonObject.get("username").getAsString();
        Admin admin = adminMapper.searchAdminByUsername(username);
        if (admin == null) {
            response.setError();
            response.setMessage("用户名不存在");
            return response.toJson();
        }
        String password = jsonObject.get("password").getAsString();
        if (!password.equals(admin.getPassword())) { // 密码不正确
            response.setError();
            response.setMessage("密码不正确");
            return response.toJson();
        }

        response.setResultSuccess();

        Map<String, Object> claims = new HashMap<>();
        claims.put("adminId", admin.getId());
        claims.put("username", admin.getUsername());
        claims.put("realName", admin.getRealName());

        String jwt = jwtUtils.createJwt(String.valueOf(IDUtil.generateId()), admin.getUsername(), claims);

        response.setMessage(jwt);
        response.setData(AdminDTO.parseDTO(admin));

        return response.toJson();
    }


    public String registerAdmin(String json) {
        Response response = new Response();
        if (JSONUtil.isFieldNull(json, "username") ||
                JSONUtil.isFieldNull(json, "password") ||
                JSONUtil.isFieldNull(json, "realName")) {
            return response.toJson();
        }
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        String username = jsonObject.get("username").getAsString();
        Admin admin = adminMapper.searchAdminByUsername(username);
        if (admin != null) {
            response.setError();
            response.setMessage("用户名已存在");
            return response.toJson();
        }
        String password = jsonObject.get("password").getAsString();
        String realName = jsonObject.get("realName").getAsString();
        admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setRealName(realName);

        int registerStatus = adminMapper.addAdmin(admin);
        response.setResultSuccess();
        response.setMessage("注册成功");
        response.setData(admin.getId());

        return response.toJson();
    }

    public String modifyPassword(String json) {
        Response response = new Response();
        if (JSONUtil.isFieldNull(json, "id") ||
                JSONUtil.isFieldNull(json, "password")) {
            return response.toJson();
        }
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        int id = jsonObject.get("id").getAsInt();
        Admin admin = adminMapper.searchAdminById(id);
        if (admin == null) {
            response.setMessage("指定id的管理员不存在");
            response.setError();
            return response.toJson();
        }
        String password = jsonObject.get("password").getAsString();
        admin = new Admin();
        admin.setId(id);
        admin.setPassword(password);

        int modifyStatus = adminMapper.modifyAdminPassword(admin);
        response.setResultSuccess();
        response.setData(admin.getId());
        return response.toJson();
    }

    public String searchAdmin(String json) {
        Response response = new Response();
        if (JSONUtil.isFieldNull(json, "searchValue")) {
            return response.toJson();
        }
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        int pageNum = jsonObject.get("pageNum").getAsInt();
        int pageSize = jsonObject.get("pageSize").getAsInt();
        String searchValue = jsonObject.get("searchValue").getAsString();

        PageHelper.startPage(pageNum, pageSize);
        List<Admin> adminList = adminMapper.searchAdmin(searchValue);
        List<AdminDTO> adminDTOList = new ArrayList<>();
        for (Admin admin : adminList) {
            AdminDTO adminDTO = AdminDTO.parseDTO(admin);
            adminDTOList.add(adminDTO);
        }

        PageInfo pageInfo = new PageInfo<>(adminList);
        ResultDataPageUtil<AdminDTO> resultDataPageUtil = new ResultDataPageUtil<>();
        resultDataPageUtil.setTotal(pageInfo.getTotal());
        resultDataPageUtil.setPageNum(pageInfo.getPageNum());
        resultDataPageUtil.setPageSize(pageInfo.getPageSize());
        resultDataPageUtil.setResultSuccess();
        resultDataPageUtil.setDataList(adminDTOList);

        return resultDataPageUtil.toJson();
    }

    public String deleteAdmin(String json) {
        Response response = new Response();
        if (JSONUtil.isFieldNull(json, "id")) {
            return response.toJson();
        }
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        int id = jsonObject.get("id").getAsInt();
        Admin admin = adminMapper.searchAdminById(id);
        if (admin == null) {
            response.setMessage("指定id的管理员不存在");
            response.setError();
            return response.toJson();
        }
        int deleteStatus = adminMapper.deleteAdminById(id);
        response.setResultSuccess();
        return response.toJson();
    }
}
