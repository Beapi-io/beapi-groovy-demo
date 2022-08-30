package demo.application.service

import demo.application.domain.User
import demo.application.repositories.AuthorityRepository


import demo.application.domain.Authority
import org.springframework.stereotype.Service

import org.springframework.beans.factory.annotation.Autowired;

@Service
//@Qualifier
public class AuthorityService implements IAuthority {

    AuthorityRepository authrepo;

    @Autowired
    public AuthorityService(AuthorityRepository authrepo) {
        this.authrepo = authrepo;
    }

    @Override
    public List<Authority> findAll() {
        return authrepo.findAll();
    }

    @Override
    public Authority save(Authority authority) {
        return authrepo.save(authority)
    }

    public Authority findByAuthority(String authority){
        return authrepo.findByAuthority(authority);
    }

    @Override
    public Optional<Authority> findById(int id) {
        return authrepo.findById(id);
    }

    public void deleteById(Long id) {
        authrepo.deleteById(id);
    }
}
