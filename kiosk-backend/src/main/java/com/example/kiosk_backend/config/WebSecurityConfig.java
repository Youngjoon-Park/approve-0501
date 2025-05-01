package com.example.kiosk_backend.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.example.kiosk_backend.jwt.JwtAuthenticationFilter;
import com.example.kiosk_backend.service.UserDetailService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

        private final UserDetailService userService;
        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .cors() // ✅ CORS 따로 분리 (아래 CorsFilter로)
                                .and()
                                .csrf().disable()
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/admin/login").permitAll()
                                                .requestMatchers("/login", "/signup", "/user", "/h2-console/**")
                                                .permitAll()
                                                .requestMatchers("/api/payment/**").permitAll()
                                                .requestMatchers("/api/orders/**").permitAll() // ✅ 추가!!
                                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                                .requestMatchers("/api/**", "/articles").authenticated()
                                                .anyRequest().permitAll())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .loginProcessingUrl("/login")
                                                .successHandler((req, res, auth) -> {
                                                        System.out.println("✅ 로그인 성공: " + auth.getName());
                                                        res.setStatus(HttpServletResponse.SC_OK);
                                                })
                                                .failureHandler((req, res, ex) -> {
                                                        System.out.println("❌ 로그인 실패: " + ex.getMessage());
                                                        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                                })
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessHandler((req, res, auth) -> {
                                                        res.setStatus(HttpServletResponse.SC_OK);
                                                })
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID")
                                                .permitAll())
                                .exceptionHandling(ex -> ex
                                                .authenticationEntryPoint((req, res, ex1) -> {
                                                        res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                                                }))
                                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

                http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        // ✅ 개발용 CORS 필터 (나중에 배포할 때 제거 가능)
        @Bean
        public CorsFilter corsFilter() {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(List.of(
                                "http://localhost:5173", // 로컬 개발 서버
                                "http://kiosktest.shop", // ✅ HTTP 도메인 추가 (지금 사용 중)
                                "https://kiosktest.shop", // HTTPS 도메인
                                "http://3.38.6.220:8081" // ✅ 추가!!
                ));
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(List.of("*"));
                config.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", config);

                return new CorsFilter(source);
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
                return http.getSharedObject(AuthenticationManagerBuilder.class)
                                .userDetailsService(userService)
                                .passwordEncoder(passwordEncoder())
                                .and()
                                .build();
        }
}
