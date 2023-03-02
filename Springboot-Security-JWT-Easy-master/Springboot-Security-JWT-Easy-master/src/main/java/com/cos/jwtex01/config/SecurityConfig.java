package com.cos.jwtex01.config;



import com.cos.jwtex01.Filter.MyFilter1;
import com.cos.jwtex01.Filter.MyFilter3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.jwtex01.config.jwt.JwtAuthenticationFilter;
import com.cos.jwtex01.config.jwt.JwtAuthorizationFilter;
import com.cos.jwtex01.repository.UserRepository;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter{	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CorsConfig corsConfig;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(new MyFilter3(), BasicAuthenticationFilter.class);
		http
				// @CrossOrigin( 인증 x)인 경우 사용 ,인증(O)이 있는 경우엔 시큐리티 필터에서 등록해야함.
				.addFilter(corsConfig.corsFilter())
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.formLogin().disable()
				.httpBasic().disable()
				
				.addFilter(new JwtAuthenticationFilter(authenticationManager()))
				.addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
				.authorizeRequests()
				.antMatchers("/api/v1/user/**")
				.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
				.antMatchers("/api/v1/manager/**")
					.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
				.antMatchers("/api/v1/admin/**")
					.access("hasRole('ROLE_ADMIN')")
				.anyRequest().permitAll();
	}
}






