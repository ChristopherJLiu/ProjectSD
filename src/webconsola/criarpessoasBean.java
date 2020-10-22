package webconsola;


import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.CopyOnWriteArrayList;

import RMI.*;


public class criarpessoasBean {
	private UserInfo user;
	private static RmiRemoto svrmi;
	static String portNum="12345";
	
	public criarpessoasBean(){
		try{
			String registryURL = "rmi://localhost:" + portNum + "/callback";
		     svrmi = (RmiRemoto) Naming.lookup(registryURL);
		     System.out.println("Lookup completed " );
		     svrmi.HelloRMI();
		}catch(IOException |NotBoundException e){
			e.printStackTrace();
		}
	}

	public UserInfo getUser() {
		return user;
	}

	public boolean setUser(UserInfo user) throws RemoteException {
		this.user = user;
		return svrmi.CriarUser(user);
	}
	
	public CopyOnWriteArrayList<DepFacInfo> getDeps() throws RemoteException{
		return svrmi.RetornaDepFac();
	}

}
