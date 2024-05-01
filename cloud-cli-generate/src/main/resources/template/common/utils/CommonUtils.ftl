package ${parent.groupId}.${parent.childLastPackage}.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
@Slf4j
public class CommonUtils {

    public static String getHttpRequestBody(HttpServletRequest request){
        StringBuilder req = null;
        try {
            BufferedReader reader = request.getReader();
            req = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                req.append(line);
            }
        } catch (IOException e) {
            log.error("getHttpRequestBody error:{}",e.getMessage(),e);
        }
        return req.toString();
    }
}
