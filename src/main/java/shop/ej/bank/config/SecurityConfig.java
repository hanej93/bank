package shop.ej.bank.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		log.debug("디버그 : BCryptPasswordEncoder 빈 등록됨");
		return new BCryptPasswordEncoder();
	}

	// todo: JWT 등록이 필요함!

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.headers(conf -> conf.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)); // iframe 사용안함
		http.csrf(AbstractHttpConfigurer::disable);
		http.cors(conf -> conf.configurationSource(configurationSource()));

		http.sessionManagement(conf -> conf.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.formLogin(AbstractHttpConfigurer::disable);
		http.httpBasic(AbstractHttpConfigurer::disable);

		http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
			authorizationManagerRequestMatcherRegistry
				.requestMatchers("/api/s/**").authenticated()
				.requestMatchers("/api/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll()
		);

		return http.build();
	}

	public CorsConfigurationSource configurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용
		configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**" ,configuration); // 어떤 주소에 대해서 위 설정을 적용

		return source;
	}
}
