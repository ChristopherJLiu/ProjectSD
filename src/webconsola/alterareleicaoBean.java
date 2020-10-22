package webconsola;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.CopyOnWriteArrayList;
import RMI.*;

public class alterareleicaoBean {
	private EleicaoInfo eleicao;
	private static RmiRemoto svrmi;
	static String portNum="12345";
	
	public alterareleicaoBean(){
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

	public String setEleicao(EleicaoInfo eleicao,String titulo, String descricao) throws RemoteException {
		this.eleicao = eleicao;
		EleicaoInfo novo = this.eleicao.deepClone();
		novo.Titulo=titulo;
		novo.Descricao=descricao;
		return svrmi.UpdateEleicao(novo,eleicao);
	}
	
	
	public CopyOnWriteArrayList<EleicaoInfo> geteleicoes() throws RemoteException{
		return svrmi.RetornaEleicoesConsola();
	}

}
