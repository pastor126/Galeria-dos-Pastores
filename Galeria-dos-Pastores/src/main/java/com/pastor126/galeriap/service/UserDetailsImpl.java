package com.pastor126.galeriap.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.pastor126.galeriap.dto.PerfilUsuarioDTO;
import com.pastor126.galeriap.entity.UsuarioEntity;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    
	private static final long serialVersionUID = 1L;
	private Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    
    public UserDetailsImpl(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
    
	public static UserDetailsImpl build(UsuarioEntity usuario, PerfilUsuarioService perfilUsuarioService) {
        PerfilUsuarioDTO perfil = perfilUsuarioService.buscarPorUsuario(usuario);
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + perfil.getPerfil().getDescricao().toUpperCase());
        List<GrantedAuthority> authorities = Collections.singletonList(authority);
        return new UserDetailsImpl(
            usuario.getId(),
            usuario.getLogin(),
            usuario.getSenha(),
            authorities
        );
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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