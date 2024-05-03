package ${parent.groupId}.${parent.childLastPackage}.service.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import ${parent.groupId}.${parent.childLastPackage}.bean.model.Users;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${parent.author}
 * @since 2024-02-19
 */
public interface UsersMapper extends BaseMapper<Users> {

    @Select("select * from your_user_table tb where tb.username = <#noparse>#{username}</#noparse>")
    Users getUserByUserName(@Param("username") String username);
}
