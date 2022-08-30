package demo.application.service

import demo.application.domain.Authority
import demo.application.domain.User

interface IAuthority {

    List<Authority> findAll()

    Authority save(Authority Role)

    void deleteById(Long id);

    Optional<User> findById(int id)

}
