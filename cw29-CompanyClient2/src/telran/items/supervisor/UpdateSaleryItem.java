package telran.items.supervisor;

import telran.employees.api.IProgrammer;
import telran.employees.utils.Config;
import telran.items.CompanyItem;
import telran.view.InputOutput;

public class UpdateSaleryItem extends CompanyItem {

	public UpdateSaleryItem(InputOutput io, IProgrammer company) {
		super(io, company);
	}

	@Override
	public String displayedName() {
	return "Change programmer's salary";
	}

	@Override
	public void perform() {
		Integer id = getAndCheckExistsProgrammerId();
		if(id == null || id < 0) return;
		
		int salary = io.inputInteger("Enter new salary ", Config.MIN_SALARY, Config.MAX_SALARY);
		
		boolean result = company.updateSalary(id, salary);
		if(result) io.outputLine("Success!");
		else io.outputLine("Something went wrong(((");
	}

}
