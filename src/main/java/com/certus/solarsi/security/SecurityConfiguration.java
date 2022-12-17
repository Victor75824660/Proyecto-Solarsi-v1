package com.certus.solarsi.security;

import org.aspectj.weaver.ast.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.certus.solarsi.interfaces.IClienteService;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter{
	
	@Autowired
	IClienteService IntClt;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(IntClt);
		auth.setPasswordEncoder(passwordEncoder());
		
		return auth;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/css/**", "/images/*","/bootstrap/**",  "/App/Register",
				"/App/crearUsuario",
				"/App/acceder",
				"/App/cerrar",
				"/App/Inicio",
				"/App/inicio",
				"/App/",
				"/App/Integrantes",
				"/App/Productos",
				"/App/detailProducto/{id}",
				"/App/CarritodeCompras",
				"/App/delete/{id}",
				"/App/buscarProducto",
				"/App/AddProducto",
				"/App/añadirProducto",
				"/App/Eliminar/{id}",
				"/App//Editar/{id}",
				"/App/OrdenesUser",
				"/App/detalleOrdenAdmin/{id}",
				"/App/MisCompras",
				"/App/detalleCompra/{id}",
				"/App/detailProducto/{id}",
				"/App/AddCar",
				"/App/saveOrder",
				"/App/OrdenDetail")
				.permitAll().anyRequest().authenticated()
					.and()
					.formLogin().loginPage("/App/Login").permitAll()
					.and().logout()
					.invalidateHttpSession(true)
					.clearAuthentication(true)
					.logoutRequestMatcher(new AntPathRequestMatcher("/App/cerrar"))
					.logoutSuccessUrl("/App/Inicio")
					.permitAll();				
				
				
				
				
				
				
				
				
				
	}
	
}
