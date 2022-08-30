package demo.application.controller


import demo.application.domain.Company
import demo.application.service.CompanyService
import io.beapi.api.controller.BeapiController
import io.beapi.api.properties.ApiProperties
import io.beapi.api.service.PrincipleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class CompanyController extends BeapiController {

	@Autowired
	private ApiProperties apiProperties

	@Autowired
	private CompanyService compService



	def show(HttpServletRequest request, HttpServletResponse response){
		try{

			Company comp = compService.findById(request.getSession().getAttribute('params').id.toLong())

			if (comp) {
				//traceService.endTrace('CompanyController','show')
				return comp
			} else {
				// todo : use error method in BeapiController
				//traceService.endTrace('CompanyController','show')
				//writeErrorResponse(response,'500', request.getRequestURI())
			}

		}catch(Exception e){
			throw new Exception("[CompanyController : get] : Exception - full stack trace follows:",e)
		}
    }


	def create(HttpServletRequest request, HttpServletResponse response){
			Company comp = new Company()
			comp.setName(request.getSession().getAttribute('params').name)

			// todo : need rollback upon fail
			return compService.save(comp)
	}

	def update(HttpServletRequest request, HttpServletResponse response){
			Company comp = compService.findById(request.getSession().getAttribute('params').id.toLong())

			if (comp) {
				//traceService.endTrace('CompanyController','show')
				comp.setName(request.getSession().getAttribute('params').name)

				// todo : need rollback upon fail
				if(compService.save(comp)){
					return comp
				}
			} else {
				// todo : use error method in BeapiController
				//traceService.endTrace('CompanyController','show')
				writeErrorResponse(response,'500', request.getRequestURI())
			}
	}

	def delete(HttpServletRequest request, HttpServletResponse response) {
		Company comp
		try {
			comp = compService.findById(request.getSession().getAttribute('params').id.toLong())
			if(comp){
				compService.deleteById(request.getSession().getAttribute('params').id.toLong())
				//(flush: true, failOnError: true)
				return [id: request.getSession().getAttribute('params').id.toLong()]
			}
		}catch(Exception e){
			throw new Exception("[CompanyController : delete] : Exception - full stack trace follows:",e)
		}
	}

}
