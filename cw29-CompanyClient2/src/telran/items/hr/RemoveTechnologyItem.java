package telran.items.hr;


import telran.employees.api.IProgrammer;
import telran.employees.utils.Config;
import telran.items.CompanyItem;
import telran.view.InputOutput;

public class RemoveTechnologyItem extends CompanyItem {

	public RemoveTechnologyItem(InputOutput io, IProgrammer company) {
		super(io, company);
	}

	@Override
	public String displayedName() {
		return "Remove programmer's technology";
	}

	@Override
	public void perform() {
		Integer id = getAndCheckExistsProgrammerId();
		if(id == null || id < 0) return;
		
		String tech = io.inputString("Enter programmer's technology you want to remove from list:  " + Config.TECHNOLOGY_LIST + " ", Config.TECHNOLOGY_LIST);
		if(tech == null) return;
		
		boolean result = company.removeTechnology(id, tech);
		if(result) io.outputLine("Success!");
		else io.outputLine("Something went wrong(((");
	}

}
