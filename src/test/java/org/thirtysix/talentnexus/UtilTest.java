package org.thirtysix.talentnexus;

import org.junit.jupiter.api.Test;
import org.thirtysix.talentnexus.util.PasswordUtil;

public class UtilTest {
    @Test
    void testHashPassword() {
        String pswd = "123456";
        String email = "a@bcom";
        if(email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            System.out.println("yes");
        }
        System.out.println(PasswordUtil.hashPassword(pswd));
    }
}
