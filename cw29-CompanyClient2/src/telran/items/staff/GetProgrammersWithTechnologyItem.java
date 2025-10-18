package telran.items.staff;


import telran.employees.api.IProgrammer;
import telran.items.CompanyItem;
import telran.view.InputOutput;

public class GetProgrammersWithTechnologyItem extends CompanyItem {

	public GetProgrammersWithTechnologyItem(InputOutput io, IProgrammer company) {
		super(io, company);
	}

	@Override
	public String displayedName() {
	
		return "Get programmers with technology";
	}

	@Override
	public void perform() {
		
		String tech = io.inputString("Enter technology");
		if(tech == null) return;
		io.outputLine(company.getProgrammersWithTechnology(tech));
	}

}
