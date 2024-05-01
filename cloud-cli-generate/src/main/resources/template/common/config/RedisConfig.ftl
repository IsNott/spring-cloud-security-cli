package ${parent.groupId}.${parent.childLastPackage}.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("<#noparse>${spring.data.redis.host}</#noparse>")
    private String redisHost;

    @Value("<#noparse>${spring.data.redis.port}</#noparse>")
    private Integer redisPort;

    @Value("<#noparse>${spring.data.redis.password: }</#noparse>")
    private String redisPass;

    @Bean
    @ConditionalOnMissingBean
    RedisTemplate<String, Object> redisTemplate() throws Exception {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);
        redisStandaloneConfiguration.setDatabase(0);
        if(StringUtils.isNotEmpty(redisPass)){
            redisStandaloneConfiguration.setPassword(redisPass);
        }
        // Avoid Exception:LettuceConnectionFactory was not initialized through afterPropertiesSet()
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisStandaloneConfiguration);
        factory.afterPropertiesSet();

        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);


        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());

        template.setHashValueSerializer(serializer);
        template.setValueSerializer(serializer);
        return template;
    }
}
