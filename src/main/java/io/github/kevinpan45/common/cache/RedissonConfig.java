package io.github.kevinpan45.common.cache;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class RedissonConfig {
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson(RedisProperties redisProperties) {
        Config config = new Config();
        SingleServerConfig server = config.useSingleServer();
        server.setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort());
        if (StringUtils.hasText(redisProperties.getUsername()) && StringUtils.hasText(redisProperties.getPassword())) {
            server.setUsername(redisProperties.getUsername());
            server.setPassword(redisProperties.getPassword());
        }
        return Redisson.create(config);
    }
}
