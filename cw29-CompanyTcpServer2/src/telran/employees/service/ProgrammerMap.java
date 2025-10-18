package telran.employees.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import telran.employees.api.IProgrammer;
import telran.employees.dto.Programmer;
import telran.employees.utils.IPersistable;

@SuppressWarnings("serial")
public class ProgrammerMap implements IProgrammer, IPersistable, Serializable{
		
	private HashMap<Integer, Programmer> programmers = new HashMap<>();

		@Override
		public boolean addProgrammer(Programmer programmer) {
			if(programmer == null)
			return false;
			return programmers.putIfAbsent(programmer.getId(), programmer) == null;
		}

		@Override
		public boolean removeProgrammer(int id) {
			
			return programmers.remove(id) != null;
		}

		@Override
		public Programmer getProgrammerData(int id) {
			
			return programmers.get(id);
		}

		@Override
		public boolean addNewTechnology(int id, String technology) {
		    if (technology == null) return false;
		    technology = technology.trim();
		    if (technology.isEmpty()) return false;

		    Programmer prog = programmers.get(id);
		    return prog != null && prog.getTechnologies().add(technology);
		}

		@Override
		public boolean removeTechnology(int id, String technology) {
		    if (technology == null) return false;
		    technology = technology.trim();
		    if (technology.isEmpty()) return false;

		    Programmer prog = programmers.get(id);
		    return prog != null && prog.getTechnologies().remove(technology);
		}


		@Override
		public List<Programmer> getProgrammersWithTechnology(String technology) {
			List<Programmer>res = new ArrayList<>();
			if(technology == null || technology.isBlank())
			return res;
			
			for(Programmer p : programmers.values()) {
				if(p.getTechnologies().contains(technology))
					res.add(p);
			}
			return res;
		}

		@Override
		public List<Programmer> getProgrammersWithSalaries(int salaryFrom, int salaryTo) {
			List<Programmer>res = new ArrayList<>();
			if(salaryFrom  >= salaryTo || salaryFrom <=0)
			return res;
			for(Programmer p : programmers.values()) {
				int salary = p.getSalary();
				if(salary >=salaryFrom && salary <= salaryTo)
					res.add(p);
			}
			return res;
		}

		@Override
		public boolean updateSalary(int id, int salary) {
			if(salary <=0)
			return false;
			Programmer prog = programmers.get(id);
			if( prog == null || salary == prog.getSalary())
				return false;
			prog.setSalary(salary);
			return true;
		}

		@Override
		public Map<String, Set<Programmer>> convertBaseMapToTechProgrammersMap() {
			Map<String, Set<Programmer>> techProgrammers = new HashMap<>();
			for(Programmer p : programmers.values()) {
				for(String tech : p.getTechnologies()) {
					Set<Programmer> progs = techProgrammers.getOrDefault(tech, new HashSet<>());
					techProgrammers.compute(tech, new BiFunction<String, Set<Programmer>, Set<Programmer>>() {

						@Override
						public Set<Programmer> apply(String t, Set<Programmer> u) {
							if(u == null) 
								u = new HashSet<>();
							u.add(p);						
							return u;
						}
					});
				}
			}
			return techProgrammers;
		}
		
		public static Map<String, Set<Programmer>> convertOtherMapToTechProgrammersMap(Map<Integer, Programmer> programmers) {
			Map<String, Set<Programmer>> techProgrammers = new HashMap<>();
			for(Programmer p : programmers.values()) {
				for(String tech : p.getTechnologies()) {
					Set<Programmer> progs = techProgrammers.getOrDefault(tech, new HashSet<>());
					techProgrammers.compute(tech, new BiFunction<String, Set<Programmer>, Set<Programmer>>() {

						@Override
						public Set<Programmer> apply(String t, Set<Programmer> u) {
							if(u == null) 
								u = new HashSet<>();
							u.add(p);						
							return u;
						}
					});
				}
			}
			return techProgrammers;
		}

		@Override
		public void save(String fileName) {
			try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName))){
				output.writeObject(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		public static ProgrammerMap restoreFromFile(String fileName) {
			try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName))){
				return (ProgrammerMap) input.readObject();
			}catch (Exception e) {
			System.out.println(e.getMessage());
			return new ProgrammerMap();
			}
		}
		
		public Set<Programmer> getAllProgrammers(){
			return new HashSet<Programmer>(programmers.values());
		}
}
