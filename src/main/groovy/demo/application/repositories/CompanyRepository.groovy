package demo.application.repositories

import demo.application.domain.Authority
import demo.application.domain.Company
import demo.application.domain.User
import demo.application.domain.UserAuthority
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company save(Company company)
    void deleteById(Long id)
    Company findById(Long id)

}