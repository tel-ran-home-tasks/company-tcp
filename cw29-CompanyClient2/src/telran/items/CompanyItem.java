package telran.items;

import telran.employees.api.IProgrammer;
import telran.employees.utils.Config;
import telran.view.InputOutput;
import telran.view.Item;


public abstract class CompanyItem implements Item {
	protected InputOutput io;
	protected IProgrammer company;
	
	public CompanyItem(InputOutput io, IProgrammer company) {
		super();
		this.io = io;
		this.company = company;
	}
	
	protected Integer getAndCheckExistsProgrammerId() {
		Integer id = io.inputInteger("Enter programmer id ", Config.MIN_ID, Config.MAX_ID);
		if(id == null) {
			io.outputLine("Wrong ID. Try again. Type 'cancel' if you want stop process");	
		}
		else {
			if(company.getProgrammerData(id) == null) {
					id = -id;
					io.outputLine("Programmer not found");	
			}
		
			else io.outputLine("Programmer exists");
		}
		return id;
	}
	}
