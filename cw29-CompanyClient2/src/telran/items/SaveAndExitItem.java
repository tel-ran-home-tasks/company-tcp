package telran.items;


import telran.employees.api.IProgrammer;
import telran.employees.utils.Config;
import telran.employees.utils.IPersistable;
import telran.view.InputOutput;


public class SaveAndExitItem extends CompanyItem {
	
	public SaveAndExitItem(InputOutput io, IProgrammer company) {
		super(io, company);
	}

	@Override
	public String displayedName() {
		return "Save and Exit";
	}

	@Override
	public void perform() {
		((IPersistable)company).save(Config.FILE_NAME);
	}
	
	public boolean isExit() {
		return true;
	}
}
