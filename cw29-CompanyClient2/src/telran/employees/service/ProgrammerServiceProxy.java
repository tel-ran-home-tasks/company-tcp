package telran.employees.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import telran.employees.api.IProgrammer;
import telran.employees.api.ProgrammerApiConstants;
import telran.employees.dto.Programmer;
import telran.employees.dto.SalaryUpdate;
import telran.employees.dto.TechnologyId;
import telran.net.TcpClientJava;

public class ProgrammerServiceProxy implements IProgrammer, AutoCloseable {
    private final TcpClientJava client;

    public ProgrammerServiceProxy(String host, int port) throws Exception {
        this.client = new TcpClientJava(host, port);
    }

    private <T> T send(String type, Serializable payload) {
        return client.sendRequest(type, payload);
    }

    @Override public boolean addProgrammer(Programmer programmer) {
        Boolean ok = send(ProgrammerApiConstants.ADD_PROGRAMMER, programmer);
        return Boolean.TRUE.equals(ok);
    }

    @Override public boolean removeProgrammer(int id) {
        Boolean ok = send(ProgrammerApiConstants.REMOVE_PROGRAMMER, id);
        return Boolean.TRUE.equals(ok);
    }

    @Override public Programmer getProgrammerData(int id) {
        return send(ProgrammerApiConstants.GET_PROGRAMMER_BY_ID, id);
    }

    @Override public boolean addNewTechnology(int id, String technology) {
        Boolean ok = send(ProgrammerApiConstants.ADD_NEW_TECHNOLOGY, new TechnologyId(id, technology));
        return Boolean.TRUE.equals(ok);
    }

    @Override public boolean removeTechnology(int id, String technology) {
        Boolean ok = send(ProgrammerApiConstants.REMOVE_TECHNOLOGY, new TechnologyId(id, technology));
        return Boolean.TRUE.equals(ok);
    }

    @Override public List<Programmer> getProgrammersWithTechnology(String technology) {
        return send(ProgrammerApiConstants.GET_PROGRAMMERS_WITH_TECHNOLOGY, technology);
    }

    @Override public List<Programmer> getProgrammersWithSalaries(int salaryFrom, int salaryTo) {
        return send(ProgrammerApiConstants.GET_PROGRAMMERS_WITH_SALARIES, new int[]{salaryFrom, salaryTo});
    }

    @Override public boolean updateSalary(int id, int salary) {
        Boolean ok = send(ProgrammerApiConstants.UPDATE_SALARY, new SalaryUpdate(id, salary));
        return Boolean.TRUE.equals(ok);
    }

    @Override public Map<String, Set<Programmer>> convertBaseMapToTechProgrammersMap() {
        return send(ProgrammerApiConstants.CONVERT_BASE_MAP, null);
    }

    @Override public Set<Programmer> getAllProgrammers() {
        return send(ProgrammerApiConstants.GET_ALL_PROGRAMMERS, null);
    }

    @Override public void close() throws IOException {
        client.close();
    }
}
