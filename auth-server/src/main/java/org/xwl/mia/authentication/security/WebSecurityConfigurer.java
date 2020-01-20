package org.xwl.mia.authentication.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Value("${auth.security.encode.mode}")
	private String encodeMode="";
	
//	@Bean
//	public static PasswordEncoder passwordEncoder() {
//		String idForEncode = "bcrypt";
//		Map<String,PasswordEncoder> encoders = new HashMap<>();
//		encoders.put(idForEncode, new BCryptPasswordEncoder());
//		encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
//		encoders.put("scrypt", new SCryptPasswordEncoder());
//		PasswordEncoder passwordEncoder =
//		    new DelegatingPasswordEncoder(idForEncode, encoders);
//		return passwordEncoder;
////		return new BCryptPasswordEncoder();
//	}
	
   @SuppressWarnings("deprecation")
   @Bean
   public static NoOpPasswordEncoder passwordEncoder() {
	   return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
   }	
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	@Bean
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return super.userDetailsServiceBean();
	}
	
	 @Override
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception
	 {
		 System.out.println("encodeMode is: >>> " + encodeMode);		 
		 auth.inMemoryAuthentication().withUser("john.carnell").password("password1").roles("USER")
		 .and().withUser("william.woodward").password("password2").roles("USER", "ADMIN")
		 .and().withUser("nzdx").password("nzdxadmin").roles("USER", "ADMIN");		 
//		 auth.inMemoryAuthentication()
//		 		.withUser(User.withUsername("john.carnell").roles("USER").password("{" + encodeMode + "}password1").passwordEncoder(passwordEncoder()::encode))
//		 		.withUser(User.withUsername("william.woodward").roles("USER","ADMIN").password("{"  + encodeMode + "password2").passwordEncoder(passwordEncoder()::encode));
	 }
}
