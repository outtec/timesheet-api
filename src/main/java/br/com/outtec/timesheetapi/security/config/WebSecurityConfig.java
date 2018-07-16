package br.com.outtec.timesheetapi.security.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import br.com.outtec.timesheetapi.security.filters.JwtAuthenticationFilter;
import br.com.outtec.timesheetapi.security.filters.JwtAuthorizationFilter;
import br.com.outtec.timesheetapi.security.utils.JwtTokenUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private Environment env;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	private static final String[] PUBLIC_MATCHERS = {
			"/timesheets**",
			"/collaborators**",
			"/api/v1/**",
			"/api/v1/timesheets**",
			"/api/v1/timesheets/**",
			"/collaborators**"
	};

	private static final String[] PUBLIC_MATCHERS_POST = {
			"/api/v1/timesheets/**",
			"/collaborators**"
	};

	private static final String[] PUBLIC_MATCHERS_PUT = {
			"/api/v1/timesheets/{id}**"
	};

	private static final String[] PUBLIC_MATCHERS_GET = {
			"/timesheets**",
			"/collaborators**",
			"/api/v1/auth**",
			"/api/v1/timesheets/**",
			"/api/v1/timesheets/collaborator**"
	};


	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().and().csrf().disable();
		httpSecurity.authorizeRequests()
		.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS).permitAll()
		.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
		.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
		.antMatchers(HttpMethod.PUT, PUBLIC_MATCHERS_PUT).permitAll()
		.anyRequest().authenticated();
		httpSecurity.addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtTokenUtil));
		httpSecurity.addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtTokenUtil, userDetailsService));
		httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}



	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("HEAD",
				"GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setExposedHeaders(Arrays.asList("X-Auth-Token","Authorization","Access-Control-Allow-Origin","Access-Control-Allow-Credentials"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsService).passwordEncoder(bCryptpasswordEncoder());
	}


	@Bean
	public PasswordEncoder bCryptpasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
