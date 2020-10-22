package webconsola;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.CopyOnWriteArrayList;
import RMI.DepFacInfo;
import RMI.EleicaoInfo;
import RMI.Mesa_voto;
import RMI.RmiRemoto;
import RMI.UserInfo;

public class criarmesaBean {
	private EleicaoInfo eleicao;
	private static RmiRemoto svrmi;
	static String portNum="12345";
	private CopyOnWriteArrayList<UserInfo> todos = new CopyOnWriteArrayList<UserInfo>();
	
	public criarmesaBean(){
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

	public String setEleicao(EleicaoInfo eleicao,int nterminais,DepFacInfo local,double BI1,double BI2, double BI3) throws RemoteException {
		getTodos();
		this.eleicao = eleicao;
		int counter=0;
		for(UserInfo u : this.todos){//Checka se as pessoas existem
			if(u.BI==BI1 || u.BI==BI2 || u.BI==BI3)
				counter++;
		}
		if(counter!=3){//Confirma a anterior
			System.out.println("Alguma dessas pessoas nao existe");
			return "Alguma das pessoas nao existe";
		}
		for(UserInfo u:this.todos){//Remove todos os users que nao intressam
			if(u.BI!=BI1 && u.BI!=BI2 && u.BI!=BI3){
				this.todos.remove(u);
			}
		}
		
		EleicaoInfo novo = this.eleicao.deepClone();
		Mesa_voto mesa = new Mesa_voto(local,nterminais);
		for(Mesa_voto m : eleicao.Mesas)
			for(UserInfo u : m.membros_mesa)
				if(u.BI==BI1 || u.BI==BI2 || u.BI==BI3)
					return "Uma ou mais pessoas ja estao numa das mesas desta eleicao";
		mesa.membros_mesa=this.todos;
		System.out.println(this.todos.size());
		if(!novo.Mesas.contains(mesa))
			novo.Mesas.add(mesa);
		else
			return "Ja existe uma mesa nesse local";
		return svrmi.UpdateEleicao(novo,eleicao);
	}
	
	public CopyOnWriteArrayList<DepFacInfo> getDeps() throws RemoteException{
		return svrmi.RetornaDepFac();
	}
	
	public CopyOnWriteArrayList<EleicaoInfo> geteleicoes() throws RemoteException{
		return svrmi.RetornaEleicoesConsola();
	}

	public CopyOnWriteArrayList<UserInfo> getTodos() throws RemoteException {
		this.todos=svrmi.RetornaTodosUsers();
		return todos;
	}

	public void setTodos(CopyOnWriteArrayList<UserInfo> todos) {
		this.todos = todos;
	}
	
}
