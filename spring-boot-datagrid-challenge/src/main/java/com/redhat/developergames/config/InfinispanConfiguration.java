package com.redhat.developergames.config;

import java.net.URI;
import java.net.URISyntaxException;

import org.infinispan.spring.remote.provider.SpringRemoteCacheManagerFactoryBean;
import org.infinispan.spring.remote.session.configuration.EnableInfinispanRemoteHttpSession;
import org.infinispan.spring.starter.remote.InfinispanRemoteCacheCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@EnableInfinispanRemoteHttpSession(maxInactiveIntervalInSeconds = 3600)
public class InfinispanConfiguration {

   @Bean
   @Order(Ordered.HIGHEST_PRECEDENCE)
   public InfinispanRemoteCacheCustomizer caches() {
      return b -> {
         // Configure the weather cache to be created if it does not exist in the first call
         URI weatherCacheConfigUri = cacheConfigURI("weatherCache.xml");

         b.remoteCache("weather")
                 .configurationURI(weatherCacheConfigUri);
         b.tcpKeepAlive(false);
         b.addServers("rhdg:11222").security().authentication().username("developer").password("thedeveloperpassword");
      };
   }

   private URI cacheConfigURI(String cacheConfigFile) {
      URI cacheConfigUri;
      try {
         cacheConfigUri = this.getClass().getClassLoader().getResource(cacheConfigFile).toURI();
      } catch (URISyntaxException e) {
         throw new RuntimeException(e);
      }
      return cacheConfigUri;
   }
   
   
   @Bean
   public InfinispanRemoteCacheCustomizer sessions() {
      return b -> {
         // Configure the weather cache to be created if it does not exist in the first call
         URI weatherCacheConfigUri = cacheConfigURI("sessionsCache.xml");

         b.remoteCache("sessions")
                 .configurationURI(weatherCacheConfigUri);
         b.tcpKeepAlive(false);
         b.addServers("rhdg:11222").security().authentication().username("developer").password("thedeveloperpassword");
      };
   }
   
//   @Bean
//   public SpringRemoteCacheManagerFactoryBean springCache() {
//       return new SpringRemoteCacheManagerFactoryBean();
//   }
}
