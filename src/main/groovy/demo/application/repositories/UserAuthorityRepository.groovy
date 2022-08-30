package demo.application.repositories

import demo.application.domain.Authority
import demo.application.domain.User
import demo.application.domain.UserAuthority
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {

    UserAuthority save(UserAuthority userAuthority)
    List<UserAuthority> findByUser(User user);
    List<UserAuthority> findByAuthority(Authority authority);

}