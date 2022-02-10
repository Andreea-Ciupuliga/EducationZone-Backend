package com.example.EducationZoneBackend.Config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import java.util.List;

@Configuration
@PropertySource("classpath:application.yml")
public class SecurityConfig {

    @Bean
    public CustomCorsFilter customCorsFilter() {
        return new CustomCorsFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Default order of 3.
     */
    @Configuration
    @EnableWebSecurity
    @EnableResourceServer
    public static class OAuth2SecurityConfiguration extends ResourceServerConfigurerAdapter {





        @SneakyThrows
        @Override
        public void configure(HttpSecurity http) {
            http.apply(CommonSecurityConfiguration
                    .commonSecurityConfiguration())
                    .and()
                    .anonymous()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/student/register").permitAll() //dam acces la toti ptc nu ai cum sa fii autentificat atunci cand te inregistrezi pt prima data
                    .antMatchers("/professor/register").permitAll();
        }
    }

//    @Configuration
//    @Order(1)
//    public static class ClockifySecretAuthSecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//
//
//        /**
//         * Setter-based DI to any possible avoid circular bean dependency
//         * with constructor-based DI.
//         */
//
//        @SneakyThrows
//        @Override
//        @Bean
//        public AuthenticationManager authenticationManagerBean() {
//            return super.authenticationManagerBean();
//        }
//
//
//
//        @Override
//        public void configure(WebSecurity web) {
//            web.ignoring()
//                    .antMatchers("/timeOffs/approve/**",
//                            "/timeOffs/reject",
//                            "/users/requestChangePassword",
//                            "/users/resetPassword",
//                            "/companies",
//                            "/swagger-ui/**",
//                            "/v3/api-docs/**",
//                            "/swagger-ui.html");
//        }
//    }

    public static class CommonSecurityConfiguration extends AbstractHttpConfigurer<CommonSecurityConfiguration, HttpSecurity> {

        public static CommonSecurityConfiguration commonSecurityConfiguration() {
            return new CommonSecurityConfiguration();
        }

        @SneakyThrows
        @Override
        public void init(HttpSecurity http) {
            http
                    .cors()
                    .and()
                    .csrf()
                    .disable()
                    .authorizeRequests();
        }

    }

}
