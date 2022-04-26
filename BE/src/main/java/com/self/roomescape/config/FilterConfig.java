package com.self.roomescape.config;

import com.self.roomescape.filter.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JwtFilter> filter1(){
        FilterRegistrationBean<JwtFilter> bean = new FilterRegistrationBean<>(new JwtFilter());
        bean.addUrlPatterns("/**");
        bean.setOrder(0); // 낮은 번호가 우선순위가 가장 높다.
        return bean;
    }
}
