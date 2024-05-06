package ${parent.groupId}.${parent.childLastPackage}.security.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Data
public class SysUser {

    private String username;

    private String password;

    private Date storedTime;

    public boolean hasExpired() {
        if(storedTime == null){
            return true;
        }
        LocalDateTime localDateTime = storedTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusHours(1);
        return  Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()).before(new Date());
    }
}
