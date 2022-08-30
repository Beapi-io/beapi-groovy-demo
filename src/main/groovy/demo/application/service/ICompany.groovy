package demo.application.service

import demo.application.domain.Authority
import demo.application.domain.Company
import demo.application.domain.User

interface ICompany {

    Company save(Company company)
    void deleteById(Long id)
    Company findById(Long id)

}
