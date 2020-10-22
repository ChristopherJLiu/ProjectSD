package webconsola;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.CopyOnWriteArrayList;

import RMI.DepFacInfo;
import RMI.EleicaoInfo;
import RMI.RmiRemoto;

public class criareleicaoBean {
	private EleicaoInfo eleicao;
	private static RmiRemoto svrmi;
	static String portNum="12345";
	
	public criareleicaoBean(){
		try{
			String registryURL = "rmi://localhost:" + portNum + "/callback";
		     svrmi = (RmiRemoto) Naming.lookup(registryURL);
		     System.out.println("Lookup completed " );
		     svrmi.HelloRMI();
		}catch(IOException |NotBoundException e){
			e.printStackTrace();
		}
	}

	public EleicaoInfo getEleicao() {
		return eleicao;
	}

	public void setEleicao(EleicaoInfo eleicao) throws RemoteException {
		this.eleicao = eleicao;
		svrmi.CriarEleicao(eleicao);
	}
	
	public CopyOnWriteArrayList<DepFacInfo> getDeps() throws RemoteException{
		return svrmi.RetornaDepFac();
	}
}
