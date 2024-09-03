package org.thirtysix.talentnexus.service;

import org.thirtysix.talentnexus.dto.CompanyLoginDto;
import org.thirtysix.talentnexus.pojo.Company;

public interface CompanyService {
    boolean login(CompanyLoginDto loginDto);

    String register(Company company);

    Company getByUsername(String username);
}
