package ua.lviv.logos.AppSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

import ua.lviv.logos.serviceImpl.UserServiceimpl;

@SuppressWarnings("all")
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    UserServiceimpl userService;

    @Override
    protected void configure(AuthenticationManagerBuilder AuthenticationManagerBuilder) throws Exception {
        AuthenticationManagerBuilder.userDetailsService(userService);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
	protected void configure(HttpSecurity http) throws Exception {
		
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/registration").permitAll()
            .antMatchers("/index").hasRole("USER")
            .antMatchers("/").hasRole("USER")
            .and()
			.formLogin()
			.loginPage("/login").permitAll()
            .defaultSuccessUrl("/index")
            .and()
            .logout().invalidateHttpSession(true)
            .clearAuthentication(true)
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login").permitAll();

        http.requiresChannel()
            .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
            .requiresSecure();
    }
}
