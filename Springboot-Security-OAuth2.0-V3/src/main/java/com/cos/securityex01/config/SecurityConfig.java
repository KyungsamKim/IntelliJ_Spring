package com.cos.securityex01.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.securityex01.config.oauth.PrincipalOauth2UserService;

// 1. 코드 받기(인증) 2. 엑세스토큰(권한)
// 3. 사용자 프로필 정보를 가져오고, 4-1 그 정보를 토대로 회원가입 자동으로 진행 하기도함.
// 4-2 (집주소, 등급 등)의 추가 정보를 받기도 함.

@Configuration // IoC 빈(bean)을 등록
@EnableWebSecurity // 필터 체인 관리 시작 어노테이션
//securedEnbaled = @Secured("ROLE_ADMIN")등의 어노테이션 활성화, prePostEnabled = PreAuthorize 어노테이션 활성화
// @PreAuthorize("hasRole('ROLE_MANAGER')or hasRole('ROLE_ADMIN')") // 여러개를 걸고싶다면 secured대신 사용 할 수 있음.
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 특정 주소 접근시 권한 및 인증을 위한 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated()
			//.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
			//.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll()
		.and()
			.formLogin()
			.loginPage("/login")
				//.usernameParameter("username2")
			.loginProcessingUrl("/loginProc")
			.defaultSuccessUrl("/") //특정페이지에서 로그인 요청을 하면 해당페이지를 그대로 열어주는 기능이 있음.
		.and()
			.oauth2Login() //구글로그인같은 oauth 인증 가능하게 함.
			.loginPage("/login") //구글 로그인이 완료된 뒤의 후처리가 필요.
				//Tip. 코드 X (엑세스토큰+ 사용자프로필정보O) 바로 한번에 받을 수 있음. OauthCleint 라이브러리 통해.
			.userInfoEndpoint()
			.userService(principalOauth2UserService);
	}
}





