package org.deeptiagarwal.easylife.security;

import org.deeptiagarwal.easylife.dao.AuthGroupRepoI;
import org.deeptiagarwal.easylife.dao.UsersRepoI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailService implements UserDetailsService {

    UsersRepoI usersRepoI;
    AuthGroupRepoI authGroupRepoI;

    @Autowired
    public AppUserDetailService(UsersRepoI usersRepoI, AuthGroupRepoI authGroupRepoI) {
        this.usersRepoI = usersRepoI;
        this.authGroupRepoI = authGroupRepoI;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new AppUserPrincipal
                (usersRepoI.findByEmailAllIgnoreCase(username).orElseThrow(() -> new UsernameNotFoundException(username+" doesn't exist in the records."))
                        , authGroupRepoI.findByEmail(username));
    }
}
