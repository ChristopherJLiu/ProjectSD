package webconsola;


import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.CopyOnWriteArrayList;

import RMI.*;


public class alterarpessoaBean {
	private UserInfo user;
	private static RmiRemoto svrmi;
	static String portNum="12345";
	private CopyOnWriteArrayList<UserInfo> todos = new CopyOnWriteArrayList<UserInfo>();
	
	public alterarpessoaBean(){
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

	public String setUser(UserInfo user,UserInfo velho) throws RemoteException {
		this.user = user;
		return svrmi.AlteraUserInfo(user, velho);
	}
	
	public CopyOnWriteArrayList<DepFacInfo> getDeps() throws RemoteException{
		return svrmi.RetornaDepFac();
	}

	public CopyOnWriteArrayList<UserInfo> getTodos() throws RemoteException {
		return svrmi.RetornaTodosUsers();
	}

	public void setTodos(CopyOnWriteArrayList<UserInfo> todos) {
		this.todos = todos;
	}

}
