package telran.items.hr;

import telran.employees.api.IProgrammer;
import telran.employees.dto.Programmer;
import telran.employees.utils.Config;
import telran.items.CompanyItem;
import telran.view.InputOutput;

import java.util.Arrays;

public class AddProgrammerItem extends CompanyItem {

    public AddProgrammerItem(InputOutput io, IProgrammer company) {
        super(io, company);
    }

    @Override
    public String displayedName() {
        return "Hire programmer";
    }

    @Override
    public void perform() {
        Integer id = io.inputInteger("Enter programmer id or cancel for exit", 1, Integer.MAX_VALUE);
        if (id == null) return;
        try {
            if (company.getProgrammerData(id) != null) {
                io.outputLine("Programmer with id " + id + " already exists");
                return;
            }
        } catch (Exception e) {
            io.outputLine("Communication error while checking id: " + e.getMessage());
            return;
        }

        String progName = io.inputString("Enter programmer name ");
        String[] technologies = getTechnologies();
        int salary = io.inputInteger("Enter salary ", Config.MIN_SALARY, Config.MAX_SALARY);

        try {
            Programmer prog = new Programmer(id, progName, technologies, salary);

            boolean ok = company.addProgrammer(prog);
            io.outputLine(ok ? "Hired" : "Not hired (already exists?)");
        } catch (Exception e) {
            io.outputLine("Something went wrong! Programmer data not added! " + e.getMessage());
        }
    }

    private String[] getTechnologies() {
        String temp = io.inputString(
                "Enter programmer's technologies separated by comma from list: " + Config.TECHNOLOGY_LIST + " ",
                Config.TECHNOLOGY_LIST);
        return Arrays.stream(temp.split(","))
                     .map(String::trim)
                     .filter(s -> !s.isEmpty())
                     .toArray(String[]::new);
    }
}
