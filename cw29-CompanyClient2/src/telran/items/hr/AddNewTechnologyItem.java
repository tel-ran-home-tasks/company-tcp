package telran.items.hr;


import telran.employees.api.IProgrammer;
import telran.employees.utils.Config;
import telran.items.CompanyItem;
import telran.view.InputOutput;

public class AddNewTechnologyItem extends CompanyItem {

	public AddNewTechnologyItem(InputOutput io, IProgrammer company) {
		super(io, company);
	}

	@Override
	public String displayedName() {
		return "Add new programmer's technology";
	}

	@Override
	public void perform() {
		Integer id = getAndCheckExistsProgrammerId();
		if(id == null || id < 0) return;
		
		String tech = io.inputString("Enter new programmer's technology from list:  " + Config.TECHNOLOGY_LIST + " ", Config.TECHNOLOGY_LIST);
		if(tech == null) return;
		
		boolean result = company.addNewTechnology(id, tech);
		if(result) io.outputLine("Success!");
		else io.outputLine("Something went wrong(((");
	}

}
