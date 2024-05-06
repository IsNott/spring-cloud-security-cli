package ${parent.groupId}.${parent.childLastPackage}.security.manager;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import ${parent.groupId}.${parent.childLastPackage}.common.exception.BusinessException;
import ${parent.groupId}.${parent.childLastPackage}.security.config.JwtConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class PasswordManager {

    private static final Logger logger = LoggerFactory.getLogger(PasswordManager.class);

    @Autowired
    private JwtConfig jwtConfig;

    public String decryptPassword(String data) {
        SecureUtil.disableBouncyCastle();
        AES aes = new AES(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
        String decryptStr;
        String decryptPassword;
        try {
            decryptStr = aes.decryptStr(data);
            decryptPassword = decryptStr.substring(13);
        } catch (Exception e) {
            logger.error("Exception:", e);
            throw new BusinessException("AES decrypt error {}", e);
        }
        return decryptPassword;
    }
}
