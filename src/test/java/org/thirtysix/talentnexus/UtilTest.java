package org.thirtysix.talentnexus;

import org.junit.jupiter.api.Test;
import org.thirtysix.talentnexus.utl.PasswordUtil;

public class UtilTest {
    @Test
    void testHashPassword() {
        String pswd = "123456";
        System.out.println(PasswordUtil.hashPassword(pswd));
    }
}
