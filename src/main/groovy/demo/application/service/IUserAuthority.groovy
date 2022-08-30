package demo.application.service

import demo.application.domain.Authority
import demo.application.domain.User
import demo.application.domain.UserAuthority

interface IUserAuthority {

    UserAuthority save(UserAuthority userAuthority)
    List<UserAuthority> findByUser(User user);
    List<UserAuthority> findByAuthority(Authority authority);
}
