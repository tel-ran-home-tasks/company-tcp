package telran.employees.net;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import telran.employees.api.IProgrammer;
import telran.employees.api.ProgrammerApiConstants;
import telran.employees.dto.Programmer;
import telran.employees.dto.SalaryUpdate;
import telran.employees.dto.TechnologyId;
import telran.net.RequestJava;
import telran.net.ResponseJava;
import telran.net.TcpResponseCode;
import telran.net.server.ProtocolJava;

public class CompanyServerProtocol implements ProtocolJava {
	private final IProgrammer service;

	public CompanyServerProtocol(IProgrammer service) {
		this.service = service;
	}

	@Override
	public ResponseJava getResponse(RequestJava request) {
		if (request == null)
			return wrong("Request is null");

		final String type = request.requestType;
		final Serializable data = request.requestData;

		 try {
	            switch (type) {

	            case ProgrammerApiConstants.ADD_PROGRAMMER: {
	                if (!(data instanceof Programmer p))
	                    return wrong("add_prog expects Programmer");
	                boolean ok = service.addProgrammer(p);
	                return ok(ok);
	            }

	            case ProgrammerApiConstants.REMOVE_PROGRAMMER: {
	                if (!(data instanceof Integer id))
	                    return wrong("remove_programmer expects Integer id");
	                boolean ok = service.removeProgrammer(id);
	                return ok(ok);
	            }

	            case ProgrammerApiConstants.GET_PROGRAMMER_BY_ID: {
	                if (!(data instanceof Integer id))
	                    return wrong("get_prog_id expects Integer id");
	                Programmer p = service.getProgrammerData(id);
	                return new ResponseJava(TcpResponseCode.OK, p);
	            }

	            case ProgrammerApiConstants.ADD_NEW_TECHNOLOGY: {
	                if (!(data instanceof TechnologyId t))
	                    return wrong("add_new_tech expects TechnologyId");
	                boolean ok = service.addNewTechnology(t.getId(), t.getTechnology());
	                return ok(ok);
	            }

	            case ProgrammerApiConstants.REMOVE_TECHNOLOGY: {
	                if (!(data instanceof TechnologyId t))
	                    return wrong("remove_technology expects TechnologyId");
	                boolean ok = service.removeTechnology(t.getId(), t.getTechnology());
	                return ok(ok);
	            }

	            case ProgrammerApiConstants.GET_PROGRAMMERS_WITH_TECHNOLOGY: {
	                if (!(data instanceof String tech))
	                    return wrong("get_programmers_with_technology expects String");
	                List<Programmer> list = service.getProgrammersWithTechnology(tech);
	                return new ResponseJava(TcpResponseCode.OK, (Serializable) list);
	            }

	            case ProgrammerApiConstants.GET_PROGRAMMERS_WITH_SALARIES: {
	                if (!(data instanceof int[] arr) || arr.length != 2)
	                    return wrong("get_programmers_with_salaries expects int[2] {from,to}");
	                List<Programmer> list = service.getProgrammersWithSalaries(arr[0], arr[1]);
	                return new ResponseJava(TcpResponseCode.OK, (Serializable) list);
	            }

	            case ProgrammerApiConstants.UPDATE_SALARY: {
	                if (!(data instanceof SalaryUpdate s))
	                    return wrong("update_salary expects SalaryUpdate{id,salary}");
	                boolean ok = service.updateSalary(s.getId(), s.getSalary());
	                return ok(ok);
	            }

	            case ProgrammerApiConstants.CONVERT_BASE_MAP: {
	                Map<String, Set<Programmer>> map = service.convertBaseMapToTechProgrammersMap();
	                return new ResponseJava(TcpResponseCode.OK, (Serializable) map);
	            }

	            case ProgrammerApiConstants.GET_ALL_PROGRAMMERS: {
	                Set<Programmer> all = (service instanceof telran.employees.service.ProgrammerMap pm)
	                        ? pm.getAllProgrammers()
	                        : new java.util.HashSet<>(
	                                service.getProgrammersWithSalaries(Integer.MIN_VALUE, Integer.MAX_VALUE));
	                return new ResponseJava(TcpResponseCode.OK, (Serializable) all);
	            }

	            default:
	                return wrong("Unknown request type: " + type);
	            }
	        } catch (Exception e) {
	            return wrong(e.getClass().getSimpleName() + ": " + e.getMessage());
	        }
	    }

	private static ResponseJava ok(boolean value) {
		return new ResponseJava(TcpResponseCode.OK, value);
	}

	private static ResponseJava wrong(String msg) {
		return new ResponseJava(TcpResponseCode.WRONG_REQUEST, msg);
	}
}
