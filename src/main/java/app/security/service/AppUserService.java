package app.security.service;

import app.security.entity.AppUser;
import app.security.repository.AppUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByEmailIgnoreCase(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));

        GrantedAuthority permission = new SimpleGrantedAuthority("ROLE_" + appUser.getRole().toString());

        List<GrantedAuthority> permissions = new ArrayList<>();
        permissions.add(permission);

        return new User(email, appUser.getPassword(), permissions);
    }

    public AppUser newUser(AppUser user) {
        return appUserRepository.save(user);
    }

}
