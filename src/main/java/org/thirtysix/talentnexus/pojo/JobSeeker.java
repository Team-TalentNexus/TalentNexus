package org.thirtysix.talentnexus.pojo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class JobSeeker {
    private int id;
    private String username;
    private String passwordHash;
    private String email;
    private String phone;
    private String fullName;
    private GenderEnum gender;
    private LocalDate birthDate;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

enum GenderEnum {
    MALE, FEMALE, OTHER
}
