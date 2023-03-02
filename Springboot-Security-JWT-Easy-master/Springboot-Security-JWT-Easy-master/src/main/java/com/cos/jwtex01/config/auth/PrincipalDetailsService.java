package com.cos.jwtex01.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.jwtex01.model.User;
import com.cos.jwtex01.repository.UserRepository;

import lombok.RequiredArgsConstructor;


// http://localhsot:8080/login 요청시 동작X formLogin disable 시켰기 때문.
// JwtAuthentication filter에서 처리.
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("PrincipalDetailsService : 진입");
		User user = userRepository.findByUsername(username);

		// session.setAttribute("loginUser", user);
		return new PrincipalDetails(user);
	}
}
