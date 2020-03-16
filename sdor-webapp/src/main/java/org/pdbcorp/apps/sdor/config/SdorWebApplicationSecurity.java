/* Copyright 2020 PDB Corp.
 *
 * Proprietary Software built off of open-source software?
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.pdbcorp.apps.sdor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SdorWebApplicationSecurity extends WebSecurityConfigurerAdapter {
	private static final String ROLE_ADMIN = "ADMIN";
	private static final String ROLE_ROOT = "ROOT";
	private static final String ROLE_USER = "USER";

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("user").password("{noop}password").roles(ROLE_USER)
			.and()
			.withUser("admin").password("{noop}secretPassword").roles(ROLE_USER, ROLE_ADMIN)
			.and()
			.withUser("root").password("{noop}superSecretPassword").roles(ROLE_USER, ROLE_ADMIN, ROLE_ROOT);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/", "/secured", "/css/**", "/js/**", "/**/favicon.ico").permitAll()
			.antMatchers("/admin", "/actuator/health").hasRole(ROLE_ADMIN)
			.antMatchers("/actuator/**", "/initiateShutdown").access("hasRole('" + ROLE_ROOT + "')")
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login").defaultSuccessUrl("/secured").permitAll()
			.and()
			.logout()
				.deleteCookies("remove")
				.invalidateHttpSession(true)
				.logoutUrl("/logout")
				.logoutSuccessUrl("/secured")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.permitAll();
		http.exceptionHandling().accessDeniedPage("/error");
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}
}