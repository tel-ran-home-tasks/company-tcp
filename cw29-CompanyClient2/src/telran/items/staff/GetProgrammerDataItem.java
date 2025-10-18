package telran.items.staff;


import telran.employees.api.IProgrammer;
import telran.items.CompanyItem;
import telran.view.InputOutput;

public class GetProgrammerDataItem extends CompanyItem {

	public GetProgrammerDataItem(InputOutput io, IProgrammer company) {
		super(io, company);
	}

	@Override
	public String displayedName() {
		return "Get programmer data by Id";
	}

	@Override
	public void perform() {
		
			Integer id = getAndCheckExistsProgrammerId();
			io.outputLine(company.getProgrammerData(id ));
	}


}
