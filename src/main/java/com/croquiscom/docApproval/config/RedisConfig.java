package com.croquiscom.docApproval.config;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		LettuceConnectionFactory cf = new LettuceConnectionFactory();
		return cf;
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(){
		RedisTemplate<String, Object> rt = new RedisTemplate<String, Object>();
		rt.setConnectionFactory(redisConnectionFactory());
		rt.setKeySerializer(new StringRedisSerializer());
		rt.setValueSerializer(new StringRedisSerializer());
		
		return rt;
	}
	
	@Bean
	public CacheManager cacheManager() {
		RedisCacheConfiguration redisCacheConfig = RedisCacheConfiguration
				.defaultCacheConfig()
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.disableCachingNullValues()
				.entryTtl(Duration.ofHours(1));
				
		return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory()).cacheDefaults(redisCacheConfig).build();
	}
}
