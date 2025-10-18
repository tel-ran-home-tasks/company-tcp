package telran;

import telran.employees.net.CompanyServerProtocol;
import telran.employees.service.ProgrammerMap;
import telran.net.server.ProtocolJava;
import telran.net.server.ServerJava;


public class CompanyServerAppl {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ProtocolJava companyProtocol = new CompanyServerProtocol(new ProgrammerMap());
		ServerJava server;
		try {
			server = new ServerJava(2000, companyProtocol );
			server.run();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		

	}

}
