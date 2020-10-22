package webconsola;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.CopyOnWriteArrayList;

import RMI.DepFacInfo;
import RMI.EleicaoInfo;
import RMI.RmiRemoto;


public class resultadosBean {
	private CopyOnWriteArrayList<EleicaoInfo> eleicoes = new CopyOnWriteArrayList<EleicaoInfo>();
	private static RmiRemoto svrmi;
	static String portNum="12345";
	
	public resultadosBean(){
		try{
			String registryURL = "rmi://localhost:" + portNum + "/callback";
		     svrmi = (RmiRemoto) Naming.lookup(registryURL);
		     System.out.println("Lookup completed " );
		     svrmi.HelloRMI();
		}catch(IOException |NotBoundException e){
			e.printStackTrace();
		}
	}
	
	public CopyOnWriteArrayList<EleicaoInfo> getEleicoes() throws RemoteException {
		setEleicoes(new CopyOnWriteArrayList<EleicaoInfo>());
		if(!svrmi.RetornaEleicoesAcabadas().isEmpty())
			eleicoes.addAll(svrmi.RetornaEleicoesAcabadas());
		return eleicoes;
	}
	public void setEleicoes(CopyOnWriteArrayList<EleicaoInfo> eleicoes) {
		this.eleicoes = eleicoes;
	}
}
