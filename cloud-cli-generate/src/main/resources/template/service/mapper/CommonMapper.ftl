package ${parent.groupId}.${parent.childLastPackage}.service.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommonMapper {

    @MapKey("id")
    List<Map<String, Object>> executeSql(@Param(value = "sql") String sql);
}
