package telran.employees.service;

import java.io.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import telran.employees.api.IProgrammer;
import telran.employees.dto.Programmer;
import telran.employees.utils.IPersistable;

@SuppressWarnings("serial")
public class ProgrammerMap implements IProgrammer, IPersistable, Serializable {

    private final Map<Integer, Programmer> programmers = new HashMap<>();
    private final ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock r = rw.readLock();
    private final ReentrantReadWriteLock.WriteLock w = rw.writeLock();

    @Override
    public boolean addProgrammer(Programmer programmer) {
        if (programmer == null) return false;
        w.lock();
        try {
            return programmers.putIfAbsent(programmer.getId(), deepCopy(programmer)) == null;
        } finally {
            w.unlock();
        }
    }

    @Override
    public boolean removeProgrammer(int id) {
        w.lock();
        try {
            return programmers.remove(id) != null;
        } finally {
            w.unlock();
        }
    }

    @Override
    public Programmer getProgrammerData(int id) {
        r.lock();
        try {
            Programmer p = programmers.get(id);
            return p == null ? null : deepCopy(p);
        } finally {
            r.unlock();
        }
    }

    @Override
    public boolean addNewTechnology(int id, String technology) {
        if (technology == null) return false;
        technology = technology.trim();
        if (technology.isEmpty()) return false;

        w.lock();
        try {
            Programmer p = programmers.get(id);
            if (p == null) return false;
            Set<String> techs = new HashSet<>(p.getTechnologies());
            boolean added = techs.add(technology);
            if (added) {
                programmers.put(id, withTechnologies(p, techs));
            }
            return added;
        } finally {
            w.unlock();
        }
    }

    @Override
    public boolean removeTechnology(int id, String technology) {
        if (technology == null) return false;
        technology = technology.trim();
        if (technology.isEmpty()) return false;

        w.lock();
        try {
            Programmer p = programmers.get(id);
            if (p == null) return false;
            Set<String> techs = new HashSet<>(p.getTechnologies());
            boolean removed = techs.remove(technology);
            if (removed) {
                programmers.put(id, withTechnologies(p, techs));
            }
            return removed;
        } finally {
            w.unlock();
        }
    }

    @Override
    public List<Programmer> getProgrammersWithTechnology(String technology) {
        List<Programmer> res = new ArrayList<>();
        if (technology == null || technology.isBlank()) return res;

        r.lock();
        try {
            for (Programmer p : programmers.values()) {
                if (p.getTechnologies().contains(technology)) {
                    res.add(deepCopy(p));
                }
            }
            return res;
        } finally {
            r.unlock();
        }
    }

    @Override
    public List<Programmer> getProgrammersWithSalaries(int salaryFrom, int salaryTo) {
        List<Programmer> res = new ArrayList<>();
        if (salaryFrom >= salaryTo || salaryFrom <= 0) return res;

        r.lock();
        try {
            for (Programmer p : programmers.values()) {
                int s = p.getSalary();
                if (s >= salaryFrom && s <= salaryTo) {
                    res.add(deepCopy(p));
                }
            }
            return res;
        } finally {
            r.unlock();
        }
    }

    @Override
    public boolean updateSalary(int id, int salary) {
        if (salary <= 0) return false;
        w.lock();
        try {
            Programmer p = programmers.get(id);
            if (p == null || p.getSalary() == salary) return false;
            programmers.put(id, withSalary(p, salary));
            return true;
        } finally {
            w.unlock();
        }
    }

    @Override
    public Map<String, Set<Programmer>> convertBaseMapToTechProgrammersMap() {
        Map<String, Set<Programmer>> result = new HashMap<>();
        r.lock();
        try {
            for (Programmer p : programmers.values()) {
                for (String tech : p.getTechnologies()) {
                    result.computeIfAbsent(tech, k -> new HashSet<>()).add(deepCopy(p));
                }
            }
            return result;
        } finally {
            r.unlock();
        }
    }

    public Set<Programmer> getAllProgrammers() {
        r.lock();
        try {
            Set<Programmer> copy = new HashSet<>();
            for (Programmer p : programmers.values()) copy.add(deepCopy(p));
            return copy;
        } finally {
            r.unlock();
        }
    }

    @Override
    public void save(String fileName) {
        Map<Integer, Programmer> snapshot = new HashMap<>();
        r.lock();
        try {
            for (var e : programmers.entrySet()) {
                snapshot.put(e.getKey(), deepCopy(e.getValue()));
            }
        } finally {
            r.unlock();
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(snapshot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static ProgrammerMap restoreFromFile(String fileName) {
        ProgrammerMap map = new ProgrammerMap();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            Object obj = in.readObject();
            if (obj instanceof Map) {
                map.w.lock();
                try {
                    map.programmers.clear();
                    ((Map<Integer, Programmer>) obj)
                            .forEach((k, v) -> map.programmers.put(k, v));
                } finally {
                    map.w.unlock();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return map;
    }


    private static Programmer deepCopy(Programmer p) {
        return p == null ? null
                : new Programmer(p.getId(), p.getName(),
                p.getTechnologies().toArray(String[]::new), p.getSalary());
    }

    private static Programmer withSalary(Programmer p, int newSalary) {
        return new Programmer(p.getId(), p.getName(),
                p.getTechnologies().toArray(String[]::new), newSalary);
    }

    private static Programmer withTechnologies(Programmer p, Set<String> techs) {
        return new Programmer(p.getId(), p.getName(),
                techs.toArray(String[]::new), p.getSalary());
    }
}
