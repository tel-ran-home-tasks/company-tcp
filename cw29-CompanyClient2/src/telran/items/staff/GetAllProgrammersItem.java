package telran.items.staff;


import telran.employees.api.IProgrammer;
import telran.items.CompanyItem;
import telran.view.InputOutput;

public class GetAllProgrammersItem extends CompanyItem {

	public GetAllProgrammersItem(InputOutput io, IProgrammer company) {
		super(io, company);
	}

	@Override
	public String displayedName() {
		return "Get all employees data in list ";
	}

	@Override
	public void perform() {
company.getAllProgrammers().forEach(io::outputLine);
	}

}
