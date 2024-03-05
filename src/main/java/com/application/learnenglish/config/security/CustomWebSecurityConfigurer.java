package com.application.learnenglish.config.security;

import com.application.learnenglish.config.security.handler.CustomAuthenticationFailureHandler;
import com.application.learnenglish.config.security.jwt.CustomAuthenticationFilter;
import com.application.learnenglish.config.security.jwt.ExceptionHandlerFilter;
import com.application.learnenglish.config.security.jwt.TokenAuthenticationFilter;
import com.application.learnenglish.config.security.jwt.TokenProvider;
import com.application.learnenglish.config.security.user.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity()
@EnableMethodSecurity()
public class CustomWebSecurityConfigurer {
    private TokenProvider tokenProvider;
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    public static String[] mergeArrays(String[]... arrays) {
        int totalLength = 0;
        for (String[] array : arrays) {
            totalLength += array.length;
        }
        String[] result = new String[totalLength];
        int destPos = 0;
        for (String[] array : arrays) {
            System.arraycopy(array, 0, result, destPos, array.length);
            destPos += array.length;
        }
        return result;
    }

    @Autowired
    public void setTokenProvider(@Lazy TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public ExceptionHandlerFilter exceptionHandlerFilter() {
        return new ExceptionHandlerFilter(resolver);
    }

    @Bean
    @Qualifier("BCrypt")
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(4));
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
            }
        };
    }

    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    public CustomAuthenticationFilter customAuthenticationFilter() {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(tokenProvider);
        customAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
        return customAuthenticationFilter;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(permitAllPaths());
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        // Set permission on endpoints
        http
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                .authorizeHttpRequests()
                // .requestMatchers(permitAllPaths()).permitAll()
                // allow access with which role
//                .requestMatchers("/api/v1/admin/**").hasAnyAuthority("ADMINISTRATOR")
//                .requestMatchers("/api/v1/users/**", "/api/v1/image/**", "/api/v1/post/**").hasAnyAuthority("ADMINISTRATOR", "USER")
                // link do not need permission
                .requestMatchers("/error/**").permitAll()
                .anyRequest().permitAll();
        // Add our custom Token based authentication filter
        http.addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(exceptionHandlerFilter(), LogoutFilter.class);
        return http.build();
    }

    public String[] permitAllPaths() {
        String[] homePaths = new String[]{
                "/", // Home path
                "/error",
                "/favicon.ico"};
        String[] resourcePaths = new String[]{
                "/**/*.png",
                "/**/*.gif",
                "/**/*.svg",
                "/**/*.jpg",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js"};
        String[] authenPaths = new String[]{
                "/auth/**",
                "/oauth2/**"};
        String[] additionalPaths = new String[]{
                "/testTrigger",
                "/api/v1/test",
                "/api/v1/admin/upload",
                "/api/v1/user/reset-password",
                "/api/v1/refreshToken",
                "/api/v1/image/**",
                "/api/v1/public/**",
                "/api/v1/quote/**"
        };
        return mergeArrays(homePaths, resourcePaths, authenPaths, additionalPaths);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
}
