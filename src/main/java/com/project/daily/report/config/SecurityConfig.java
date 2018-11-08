package com.project.daily.report.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Secured(value = "enabled")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        auth.userDetailsService(jdbcDao()).passwordEncoder(encoder);
    }

    public JdbcDaoImpl jdbcDao() {
        JdbcDaoImpl jdbcDao = new JdbcDaoImpl();
        jdbcDao.setEnableAuthorities(Boolean.FALSE);
        jdbcDao.setEnableGroups(Boolean.TRUE);
        jdbcDao.setUsersByUsernameQuery("SELECT USERNAME, PASSWORD, ACTIVE FROM USERS WHERE UPPER(USERNAME) = UPPER(?) ");
        jdbcDao.setGroupAuthoritiesByUsernameQuery("SELECT R.ID, R.ID AS GROUP_NAME, P.ID AS "
                + "AUTHORITY FROM USERS U INNER JOIN ROLES R ON U.ID_ROLES = R.ID INNER JOIN "
                + "ROLESPERMISSION RP ON RP.ID_ROLES = R.ID INNER JOIN PERMISSION P ON "
                + "RP.ID_PERMISSION = P.ID WHERE UPPER(U.USERNAME) = UPPER(?) ");

        jdbcDao.setDataSource(dataSource);
        return jdbcDao;
    }


    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/daftar/**")
                .antMatchers("/styles/**")
                .antMatchers("/css/**")
                .antMatchers("/js/**")
                .antMatchers("/fonts/**")
                .antMatchers("/img/**")
                .antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final HttpSessionCsrfTokenRepository tokenRepository = new HttpSessionCsrfTokenRepository();
        tokenRepository.setParameterName("csrf");

        http
                .headers()
                .httpStrictTransportSecurity()
                .includeSubDomains(true)
                .maxAgeInSeconds(31536000)
                .and()
                .frameOptions().sameOrigin().and()
                .csrf().csrfTokenRepository(tokenRepository)
                .and()
                .authorizeRequests()
                .antMatchers("/daftar**").permitAll()
                .antMatchers("/login**").permitAll()
                .antMatchers("/api**").permitAll()
                .antMatchers("/error**").permitAll()
                .antMatchers("/logout**").permitAll()
                .antMatchers("/denied**").permitAll()
                .antMatchers("/**").authenticated()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/denied")
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard")
                .failureUrl("/error-login")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and()
                .sessionManagement()
                .maximumSessions(1);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}