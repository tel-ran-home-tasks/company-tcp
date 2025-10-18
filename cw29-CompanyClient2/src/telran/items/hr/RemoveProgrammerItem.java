package telran.items.hr;

import telran.employees.api.IProgrammer;
import telran.employees.dto.Programmer;

import telran.items.CompanyItem;
import telran.view.InputOutput;

public class RemoveProgrammerItem extends CompanyItem {

	public RemoveProgrammerItem(InputOutput io, IProgrammer company) {
		super(io, company);
	}

	@Override
	public String displayedName() {
		return "Fire Employee";
	}

	@Override
	public void perform() {
		Integer id = getAndCheckExistsProgrammerId();
		if(id == null || id < 0) return;
		Programmer fired = company.getProgrammerData(id);
	if(company.removeProgrammer(id))
		io.outputLine("Programmer " + fired.getName() + " was successfully fired");
	}

}
