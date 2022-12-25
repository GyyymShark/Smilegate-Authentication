package com.example.authentication.Controller;

import com.example.authentication.Dto.LoginDto;
import com.example.authentication.Dto.TokenDto;
import com.example.authentication.Jwt.JwtFilter;
import com.example.authentication.Jwt.TokenProvider;
import com.example.authentication.Util.SecurityUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }   //TokenProvider와 AuthenticationManagerBuilder를 주입받는다
        //AuthenticationManagerBuilder는 본적이없는데 어디서 나온걸까

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        //로그인 시 입력받은 username과 password로 authenticationtoken객체를 생성한다
        System.out.println("loginDto.getUsername() = " + loginDto.getUsername());
        System.out.println("loginDto.getPassword() = " + loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        //authenticationToken을 이용해서 authenticate 메소드가 실행될때 CustomUserDetailsService에서
        //loadUserByUsername이 실행된다 그리고 이 결과를 통해 authentication 객체를 생성한다

        SecurityContextHolder.getContext().setAuthentication(authentication);
        //authentication객체를 SecurityContext에 저장하고

        String jwt = tokenProvider.createToken(authentication); //인증정보를 기준으로 jwt token을 생성한다

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);   //jwt token을 response header에 넣어준다

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK); //jwt token을 response body에도 넣는다
    }


    @PostMapping("/user/name/{token}")
    public String getUsername(@PathVariable String token){
        Authentication authentication = tokenProvider.getAuthentication(token);
        System.out.println("authentication = " + authentication);
        return authentication.getName();
    }


}
