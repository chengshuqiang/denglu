package com.example.demo.mapper;

import com.example.demo.model.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {

    List<Admin> searchAdmin(String value);

    Admin searchAdminById(int id);

    Admin searchAdminByUsername(String username);

    int addAdmin(Admin admin);

    int modifyAdminInformation(Admin admin);

    int modifyAdminPassword(Admin admin);

    int deleteAdminById(int id);
}
