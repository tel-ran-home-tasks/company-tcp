package telran.items.supervisor;

import java.util.List;

import telran.employees.api.IProgrammer;
import telran.employees.dto.Programmer;
import telran.employees.utils.Config;
import telran.items.CompanyItem;
import telran.view.InputOutput;

public class GetProgrammersWithSalaryItem extends CompanyItem {

	public GetProgrammersWithSalaryItem(InputOutput io, IProgrammer company) {
		super(io, company);
	}

	@Override
	public String displayedName() {
	return "Get programmer's list by salary range";
	}

	@Override
	public void perform() {

		
		int salaryFrom = io.inputInteger("Enter start salary range", Config.MIN_SALARY, Config.MAX_SALARY);
		int salaryTo = io.inputInteger("Enter end salary range", Config.MIN_SALARY, Config.MAX_SALARY);
		
		List<Programmer> result  = company.getProgrammersWithSalaries(salaryFrom, salaryTo);
		result.forEach(io::outputLine);
	}

}
