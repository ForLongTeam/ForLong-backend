package DevBackEnd.ForLong.common.config;

import DevBackEnd.ForLong.core.repository.RefreshRepository;
import DevBackEnd.ForLong.features.login.Filter.*;
import DevBackEnd.ForLong.features.login.service.CustomOauth2UserService;
import DevBackEnd.ForLong.common.utils.CookieUtil;
import DevBackEnd.ForLong.common.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {


    private final AuthenticationConfiguration configuration;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final RefreshRepository refreshRepository;
    private final CustomOauth2UserService customOauth2UserService;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    private final CustomOAuth2FailuerHandler customOAuth2FailureHandler;

    public SecurityConfig(AuthenticationConfiguration configuration, JwtUtil jwtUtil, CookieUtil cookieUtil, RefreshRepository refreshRepository, CustomOauth2UserService customOauth2UserService, CustomOAuth2SuccessHandler customOAuth2SuccessHandler, CustomOAuth2FailuerHandler customOAuth2FailureHandler) {
        this.configuration = configuration;
        this.jwtUtil = jwtUtil;
        this.cookieUtil = cookieUtil;
        this.refreshRepository = refreshRepository;
        this.customOauth2UserService = customOauth2UserService;
        this.customOAuth2SuccessHandler = customOAuth2SuccessHandler;
        this.customOAuth2FailureHandler = customOAuth2FailureHandler;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /**
         * 접근 가능한 url detail modify
         * */
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                );

        /**
         * csrf 보호 해제
         * */
        http
                .csrf((csrf) -> csrf.disable());

        /**
         * OAuth2 로그인 로직
         * */
        http
                .oauth2Login((oauth) -> oauth
                        .userInfoEndpoint((userInfo) ->
                            {userInfo.userService(customOauth2UserService);}
                        )
                        .successHandler(customOAuth2SuccessHandler)
                        .failureHandler(customOAuth2FailureHandler)
                );

        /**
         * 커스텀 로그인, 로그아웃 필터 사용
         * */
        http
                .formLogin((formLogin) -> formLogin.disable())
                .logout((formLogout) -> formLogout.disable());


        /**
         * Custom FormLogin Filter And Custom Logout Filter
         * */
        http.
                addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        http
                .addFilterAt(new LoginFilter(authenticationManager(configuration), jwtUtil, cookieUtil, refreshRepository),
                        UsernamePasswordAuthenticationFilter.class);
        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);

        http
                .addFilterBefore(new CutomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);

        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );


        /**
         * cors 관련 설정
         * */
        http
                .cors((cors) -> cors
                        .configurationSource(new CorsConfigurationSource() {
                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                CorsConfiguration config = new CorsConfiguration();

                                config.setAllowedOrigins(Collections.singletonList("http://localhost:57584"));
                                config.setAllowedMethods(Collections.singletonList("*")); // 허용할 메소드 Get ect on
                                config.setAllowCredentials(true);
                                config.setAllowedHeaders(Collections.singletonList("*"));
                                config.setMaxAge(3600L);

                                config.setExposedHeaders(Collections.singletonList("Authorization"));

                                return config;
                            }
                        }));

        return http.build();
    }
}
