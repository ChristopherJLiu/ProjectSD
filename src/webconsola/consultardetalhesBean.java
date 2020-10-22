package webconsola;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.CopyOnWriteArrayList;

import RMI.DepFacInfo;
import RMI.EleicaoInfo;
import RMI.RmiRemoto;

public class consultardetalhesBean {
	
	private CopyOnWriteArrayList<EleicaoInfo> eleicao = new CopyOnWriteArrayList<EleicaoInfo>();
	private static RmiRemoto svrmi;
	static String portNum="12345";
	
	public consultardetalhesBean(){
		try{
			String registryURL = "rmi://localhost:" + portNum + "/callback";
		     svrmi = (RmiRemoto) Naming.lookup(registryURL);
		     System.out.println("Lookup completed " );
		     svrmi.HelloRMI();
		}catch(IOException |NotBoundException e){
			e.printStackTrace();
		}
	}

	public CopyOnWriteArrayList<EleicaoInfo> getEleicao() throws RemoteException {
		setEleicao(new CopyOnWriteArrayList<EleicaoInfo>());
		if(!svrmi.RetornaEleicoesConsola().isEmpty())
			eleicao.addAll(svrmi.RetornaEleicoesConsola());
		if(!svrmi.RetornaEleicoesacorrer().isEmpty())
			eleicao.addAll(svrmi.RetornaEleicoesacorrer());
		if(!svrmi.RetornaEleicoesAcabadas().isEmpty())
			eleicao.addAll(svrmi.RetornaEleicoesAcabadas());
		return eleicao;
	}

	public void setEleicao(CopyOnWriteArrayList<EleicaoInfo> eleicao) {
		this.eleicao = eleicao;
	}

}
