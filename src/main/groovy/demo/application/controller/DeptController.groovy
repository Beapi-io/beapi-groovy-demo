package demo.application.controller

import demo.application.domain.Branch
import demo.application.domain.Company
import demo.application.domain.Dept
import demo.application.service.BranchService
import demo.application.service.CompanyService
import demo.application.service.DeptService
import io.beapi.api.controller.BeapiController
import io.beapi.api.properties.ApiProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class DeptController extends BeapiController {

	@Autowired
	private ApiProperties apiProperties

	@Autowired
	private BranchService branchService

	@Autowired
	private DeptService deptService

	def show(HttpServletRequest request, HttpServletResponse response){

		try{

			Dept dept = deptService.findById(request.getSession().getAttribute('params').id.toLong())

			if (dept) {
				//traceService.endTrace('CompanyController','show')
				return dept
			} else {
				// todo : use error method in BeapiController
				//traceService.endTrace('CompanyController','show')
				//writeErrorResponse(response,'500', request.getRequestURI())
			}

		}catch(Exception e){
			throw new Exception("[DeptController : get] : Exception - full stack trace follows:",e)
		}
    }


	def create(HttpServletRequest request, HttpServletResponse response){
		try{
			Branch branch = branchService.findById(request.getSession().getAttribute('params').branchId)

			if(branch) {
				Dept dept = new Dept()
				dept.setName(request.getSession().getAttribute('params').name)
				dept.setBranchId(branch)
				return deptService.save(dept)
			}else{
				throw new Exception("[DeptController : create] : No branch with ID '${request.getSession().getAttribute('params').companyId}' exists . Please try again.")
			}
			// todo : need rollback upon fail


		}catch(Exception e){
			throw new Exception("[DeptController : create] : Exception - full stack trace follows:",e)
		}
	}

	def update(HttpServletRequest request, HttpServletResponse response){
		Dept dept = deptService.findById(request.getSession().getAttribute('params').id.toLong())

		if (dept) {
			//traceService.endTrace('CompanyController','show')
			dept.setName(request.getSession().getAttribute('params').name)

			// todo : need rollback upon fail
			if(deptService.save(dept)){
				return dept
			}
		} else {
			// todo : use error method in BeapiController
			//traceService.endTrace('CompanyController','show')
			writeErrorResponse(response,'500', request.getRequestURI())
		}
	}

	def delete(HttpServletRequest request, HttpServletResponse response) {
		Dept dept

		try {
			dept = deptService.findById(request.getSession().getAttribute('params').id.toLong())
			if(dept){
				deptService.deleteById(request.getSession().getAttribute('params').id.toLong())
				//(flush: true, failOnError: true)
				return [id: request.getSession().getAttribute('params').id.toLong()]
			}
		}catch(Exception e){
			throw new Exception("[DeptController : delete] : Exception - full stack trace follows:",e)
		}
	}


}
