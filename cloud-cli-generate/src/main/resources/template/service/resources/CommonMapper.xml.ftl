<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${parent.groupId}.${parent.childLastPackage}.service.mapper.CommonMapper">

    <!-- 定义查询语句 -->
    <select id="executeSql" resultType="java.util.Map" parameterType="java.lang.String">
        <#noparse>${sql}</#noparse>
    </select>

</mapper>
