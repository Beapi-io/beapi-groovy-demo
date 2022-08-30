package demo.application.repositories

import demo.application.domain.Branch
import demo.application.domain.Company
import demo.application.domain.User
import demo.application.domain.UserAuthority
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    Branch save(Branch branch)
    void deleteById(Long id)
    Branch findById(Long id)


}