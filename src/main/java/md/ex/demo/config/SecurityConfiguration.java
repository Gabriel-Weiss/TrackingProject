package md.ex.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private static final String[] PUBLIC_URL = {
            "/registration**",
            "/js/**",
            "/css/**",
            "/img/**",
            "/h2-console/**"
    };


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(PUBLIC_URL).permitAll()/* Permit all this urls */
                .anyRequest().authenticated()/* Restrict other urls */
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error")
                .permitAll()/* Use custom login page */
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)/* Clear past auth data */
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))/* Logout url */
                .logoutSuccessUrl("/login?logout")
                .permitAll();/* Redirect path after logout */

        http
                .csrf().disable()/* Disable Cross-Site Request Forgery */
                .headers().frameOptions().disable();/* Disable X-Frame-Options in Spring Security (needed only with h2-console)*/
    }
}
