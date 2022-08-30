package demo.application.controller

import demo.application.domain.Branch
import demo.application.domain.Company
import demo.application.service.BranchService
import demo.application.service.CompanyService
import io.beapi.api.controller.BeapiController
import io.beapi.api.properties.ApiProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class BranchController extends BeapiController {

	@Autowired
	private ApiProperties apiProperties

	@Autowired
	private BranchService branchService

	@Autowired
	private CompanyService compService

	def show(HttpServletRequest request, HttpServletResponse response){

		try{
			Branch branch = branchService.findById(request.getSession().getAttribute('params').id.toLong())

			if (branch) {
				//traceService.endTrace('CompanyController','show')
				return branch
			} else {
				// todo : use error method in BeapiController
				//traceService.endTrace('CompanyController','show')
				//writeErrorResponse(response,'500', request.getRequestURI())
			}

		}catch(Exception e){
			throw new Exception("[BranchController : get] : Exception - full stack trace follows:",e)
		}
    }


	def create(HttpServletRequest request, HttpServletResponse response){
		try{
			Company comp = compService.findById(request.getSession().getAttribute('params').companyId)

			if(comp) {
				Branch branch = new Branch()
				branch.setName(request.getSession().getAttribute('params').name)
				branch.setCompanyId(comp)
				return branchService.save(branch)
			}else{
				throw new Exception("[BranchController : create] : No company with ID '${request.getSession().getAttribute('params').companyId}' exists . Please try again.")
			}
			// todo : need rollback upon fail


		}catch(Exception e){
			throw new Exception("[BranchController : create] : Exception - full stack trace follows:",e)
		}
	}

	def delete(HttpServletRequest request, HttpServletResponse response) {
		Branch branch

		try {
			branch = branchService.findById(request.getSession().getAttribute('params').id.toLong())
			if(branch){
				branchService.deleteById(request.getSession().getAttribute('params').id.toLong())
				//(flush: true, failOnError: true)
				return [id: request.getSession().getAttribute('params').id.toLong()]
			}
		}catch(Exception e){
			throw new Exception("[BranchController : delete] : Exception - full stack trace follows:",e)
		}
	}


}
