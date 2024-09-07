package org.thirtysix.talentnexus.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Company {
    private int id;
    private String username;
    private String password;
    private String companyName;
    private String email;
    private String phoneNumber;
    private String website;
    private String address;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
