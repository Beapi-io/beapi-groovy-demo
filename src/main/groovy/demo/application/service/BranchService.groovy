package demo.application.service

import demo.application.domain.Branch
import demo.application.domain.Company
import demo.application.repositories.BranchRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
public class BranchService implements IBranch {

    BranchRepository branchrepo

    @Autowired
    public BranchService(BranchRepository branchrepo) {
        this.branchrepo = branchrepo;
    }

    //@Override
    public Branch save(Branch branch){
        // TODO Auto-generated method stub
        return branchrepo.save(branch);
    }

    @Override
    public void deleteById(Long id){
        // TODO Auto-generated method stub
        branchrepo.deleteById(id);
    }

    public Branch findById(Long id){
        // TODO Auto-generated method stub
        return branchrepo.findById(id).get();
    }

}
