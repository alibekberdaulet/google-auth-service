package kz.alibek.googleauthservice.repository;

import com.warrenstrange.googleauth.ICredentialRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class CredentialRepository implements ICredentialRepository {

    private final Map<String, UserTOTP> usersKeys = new HashMap<>();

    @Override
    public String getSecretKey(String userName) {
        log.info("Username: " + userName);
        String secretKey = usersKeys.get(userName).getSecretKey();
        log.info("secretKey: " + secretKey);
        return secretKey;
    }

    @Override
    public void saveUserCredentials(String userName,
                                    String secretKey,
                                    int validationCode,
                                    List<Integer> scratchCodes) {
        log.info("Username: " + userName + ", secretKey: " + secretKey + ", validationCode: " + validationCode);
        if (!CollectionUtils.isEmpty(scratchCodes)) {
            scratchCodes.stream().map(Object::toString).forEach(log::info);
        }
        usersKeys.put(userName, new UserTOTP(userName, secretKey, validationCode, scratchCodes));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class UserTOTP {
        private String username;
        private String secretKey;
        private int validationCode;
        private List<Integer> scratchCodes;
    }
}
