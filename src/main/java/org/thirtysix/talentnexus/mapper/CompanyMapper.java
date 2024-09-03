package org.thirtysix.talentnexus.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.thirtysix.talentnexus.pojo.Company;

@Mapper
public interface CompanyMapper {

    @Select("SELECT password FROM companies WHERE username = #{username}")
    String getPasswordByUsername(@Param("username") String username);

    @Insert("INSERT INTO companies (username, password, email, phone_number, company_name, website, address, description) " +
            "VALUES (#{username}, #{password}, #{email}, #{phoneNumber}, #{companyName}, #{website}, #{address}, #{description})")
    void insertCompany(Company company);

    @Select("SELECT count(*) FROM companies WHERE username = #{username}")
    Integer getUsernameCount(@Param("username") String username);

    @Select("SELECT count(*) FROM companies WHERE email = #{email}")
    Integer getEmailCount(@Param("email") String email);

    @Select("SELECT * FROM companies WHERE username = #{username}")
    Company getCompanyByUsername(@Param("username") String username);
}
