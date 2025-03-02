package DevBackEnd.ForLong.features.login.dto;

import DevBackEnd.ForLong.core.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


// OAuth방식 로그인
// 스프링 시큐리티와의 통합을 위함
public class CustomUserDetail implements UserDetails, OAuth2User {

    @Getter
    private final User user;
    private Map<String, Object> attributes;


    public CustomUserDetail(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return user.getNickname();
    }


    @Override
    public Map<String, Object> getAttributes() {
        return attributes != null ? Collections.unmodifiableMap(attributes) : Collections.emptyMap();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Stream.of(new SimpleGrantedAuthority(user.getRole()))
                .collect(toList());
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLoginId();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
