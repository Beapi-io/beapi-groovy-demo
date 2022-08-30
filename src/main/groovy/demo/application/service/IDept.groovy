package demo.application.service


import demo.application.domain.Branch
import demo.application.domain.Dept

interface IDept {

    Dept save(Dept dept)
    void deleteById(Long id)
    Dept findById(Long id)


}
