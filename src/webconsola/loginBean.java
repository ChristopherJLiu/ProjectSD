package webconsola;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.CopyOnWriteArrayList;
import RMI.RmiRemoto;
import RMI.UserInfo;

public class loginBean {
	private static RmiRemoto svrmi;
	static String portNum="12345";
	private CopyOnWriteArrayList<UserInfo> users = new CopyOnWriteArrayList<UserInfo>();
	
	public loginBean(){
		try{
			String registryURL = "rmi://localhost:" + portNum + "/callback";
		     svrmi = (RmiRemoto) Naming.lookup(registryURL);
		     System.out.println("Lookup completed " );
		     svrmi.HelloRMI();
		}catch(IOException |NotBoundException e){
			e.printStackTrace();
		}
	}
	
	
	public boolean check(double BI,String pass) throws RemoteException{
		getUsers();
		System.out.println("ola");
		if(BI==00000 && pass.equals("00000"))
			return true;
		for(UserInfo u : this.users){
			System.out.println("BI:"+u.BI);
			System.out.println("pass:"+u.Password);
			System.out.println(u.Tipo);
			if(u.BI==BI && u.Password.equals(pass) && u.Tipo==4)
				return true;
		}
		return false;
	}
	
	
	public CopyOnWriteArrayList<UserInfo> getUsers() throws RemoteException {
		this.users=svrmi.RetornaTodosUsers();
		return users;
	}
	public void setUsers(CopyOnWriteArrayList<UserInfo> users) {
		this.users = users;
	}





}
