package demo.application.repositories

import demo.application.domain.Branch
import demo.application.domain.Dept
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
public interface DeptRepository extends JpaRepository<Dept, Long> {

    Dept save(Dept dept)
    void deleteById(Long id)
    Dept findById(Long id)


}