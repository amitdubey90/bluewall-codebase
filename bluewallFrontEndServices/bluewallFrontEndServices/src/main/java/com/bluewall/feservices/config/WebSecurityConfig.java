package com.bluewall.feservices.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
	 @Autowired
	 DataSource dataSource;
	 
	 @Override
		protected void configure(HttpSecurity http) throws Exception { 
		 	http
				.httpBasic()
				.and()
				.authorizeRequests()
				.antMatchers("/", "/home","/register/**","/fitnessApp/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.logout().permitAll()
				.and()
				.csrf().disable();
		 		//TODO enable CSRF for better security.
		}
	 
	 @Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.jdbcAuthentication().dataSource(dataSource)
					.usersByUsernameQuery("select username,password, enabled from users where username=?")
					.authoritiesByUsernameQuery("select username, role from user_roles where username=?");
		}

	//To disable mime type checking on public resources
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/js/**");
		web.ignoring().antMatchers("/css/**");
		web.ignoring().antMatchers("/partials/**");
		web.ignoring().antMatchers("/fonts/**");
		web.ignoring().antMatchers("/images/**");
	}
}
