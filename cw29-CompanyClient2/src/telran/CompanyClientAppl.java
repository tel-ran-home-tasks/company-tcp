package telran;

import telran.employees.api.IProgrammer;
import telran.employees.service.ProgrammerServiceProxy;
import telran.items.SaveAndExitItem;
import telran.items.hr.AddNewTechnologyItem;
import telran.items.hr.AddProgrammerItem;
import telran.items.hr.RemoveProgrammerItem;
import telran.items.hr.RemoveTechnologyItem;
import telran.items.staff.GetAllProgrammersItem;
import telran.items.staff.GetProgrammerDataItem;
import telran.items.staff.GetProgrammersWithTechnologyItem;
import telran.items.supervisor.GetProgrammersWithSalaryItem;
import telran.items.supervisor.UpdateSaleryItem;
import telran.view.ConsoleInputOutput;
import telran.view.ExitItem;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.ItemWithSubmenu;
import telran.view.Menu;

public class CompanyClientAppl {

	static IProgrammer company;
	static InputOutput io;

	public static void main(String[] args) {
		try {
			company = new ProgrammerServiceProxy("localhost", 2000);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		io = new ConsoleInputOutput();
		
		Menu menu = new Menu(getMainMenuItems(), io);
		menu.runMenu();
	}

	private static Item[] getMainMenuItems() {
		Item[] items = {
				new ItemWithSubmenu("Staff", getStaffMenuItems(), io),
				new ItemWithSubmenu("HR", getHRMenuItems(), io),
				new ItemWithSubmenu("Supervisor", getSuperMenuItems(), io),
				new SaveAndExitItem(io, company),
				new ExitItem()
		};
		return items;
	}

	private static Item[] getSuperMenuItems() {
		Item[] items = {
				new GetProgrammersWithSalaryItem(io, company),
				new UpdateSaleryItem(io, company),
				new ExitItem()
		};
		return items;
	}

	private static Item[] getHRMenuItems() {
		Item[] items = {
				new AddProgrammerItem(io, company),
				new RemoveProgrammerItem(io, company),
				new AddNewTechnologyItem(io, company),
				new RemoveTechnologyItem(io, company),
				new ExitItem()
		};
		return items;
	}

	private static Item[] getStaffMenuItems() {
		Item[] items = {
				new GetAllProgrammersItem(io, company),
				new GetProgrammerDataItem(io, company),
				new GetProgrammersWithTechnologyItem(io, company),
				new ExitItem()
		};
		return items;
		}


}
