package demo.application.service

import demo.application.domain.Authority
import demo.application.domain.Branch
import demo.application.domain.Company
import demo.application.domain.User

interface IBranch {

    Branch save(Branch branch)
    void deleteById(Long id)
    Branch findById(Long id)

}
