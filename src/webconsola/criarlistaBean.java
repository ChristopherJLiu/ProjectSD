package webconsola;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.CopyOnWriteArrayList;
import RMI.*;

public class criarlistaBean {
	private EleicaoInfo eleicao;
	private CopyOnWriteArrayList <EleicaoInfo> eleicoes = new CopyOnWriteArrayList<EleicaoInfo>();
	private CopyOnWriteArrayList <DepFacInfo> facdeps = new CopyOnWriteArrayList<DepFacInfo>();
	private CopyOnWriteArrayList <UserInfo> users = new CopyOnWriteArrayList<UserInfo>();
	private static RmiRemoto svrmi;
	static String portNum="12345";
	
	public criarlistaBean(){
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

	public void setEleicao(EleicaoInfo eleicao) {
		this.eleicao = eleicao;
	}

	public CopyOnWriteArrayList <EleicaoInfo> getEleicoes() throws RemoteException {
		this.eleicoes=svrmi.RetornaEleicoesConsola();
		return eleicoes;
	}

	public void setEleicoes(CopyOnWriteArrayList <EleicaoInfo> eleicoes) {
		this.eleicoes = eleicoes;
	}

	public CopyOnWriteArrayList <UserInfo> getUsers() throws RemoteException {
		this.users=svrmi.RetornaTodosUsers();
		return users;
	}

	public void setUsers(CopyOnWriteArrayList <UserInfo> users) {
		this.users = users;
	}
	
	public CopyOnWriteArrayList <DepFacInfo> getFacdeps() throws RemoteException {
		this.facdeps=svrmi.RetornaDepFac();
		return facdeps;
	}

	public void setFacdeps(CopyOnWriteArrayList <DepFacInfo> facdeps) {
		this.facdeps = facdeps;
	}

	public int AddLista(EleicaoInfo eleicao,double BI1,double BI2,double BI3, String nome, int tipo) throws RemoteException{
		getUsers();
		getFacdeps();
		ListasCandidatas lista;
		int counter=0;
		for(UserInfo u : this.users){//Checka se as pessoas existem
			if(u.BI==BI1 || u.BI==BI2 || u.BI==BI3)
				counter++;
		}
		if(counter!=3){//Confirma a anterior
			System.out.println("Alguma dessas pessoas nao existe");
			return-1;
		}
		////////////////////////////////////////////////////////Ve se algum deles ja esta em lista
		for(ListasCandidatas d: eleicao.Listas_CandidatasD){
			for(UserInfo u : d.Lista)
				if(u.BI==BI1 || u.BI==BI2 || u.BI==BI3){
					System.out.println("uma das pessoas ja esta em lista");
					return -1;
				}
		}
		for(ListasCandidatas e: eleicao.Listas_CandidatasE){
			for(UserInfo u : e.Lista)
				if(u.BI==BI1 || u.BI==BI2 || u.BI==BI3){
					System.out.println("uma das pessoas ja esta em lista");
					return -1;
				}
		}
		for(ListasCandidatas f: eleicao.Listas_CandidatasF){
			for(UserInfo u : f.Lista)
				if(u.BI==BI1 || u.BI==BI2 || u.BI==BI3){
					System.out.println("uma das pessoas ja esta em lista");
					return -1;
				}
		}
		//////////////////////////////////////////////////////////
		for(UserInfo u:this.users){//Remove todos os users que nao intressam
			if(u.BI!=BI1 && u.BI!=BI2 && u.BI!=BI3)
				this.users.remove(u);
		}
		//Todos ifs a seguir servem para simplesmente dividir atraves do tipo da eleicao para ver se os candidatos que o admin escolheu pode ser postos nessa lista 
		//NAO ESQUECER QUE OS CHECKS FEITOS ANTERIORMENTE TEM QUE SER FEITOS POIS NO RMI NAO FAZ ISTO
		if(eleicao.Tipo==1||eleicao.Tipo==3){
			if(eleicao.Tipo==1)
				tipo=1;
			if(eleicao.Tipo==3)
				tipo=2;
			for(UserInfo u: this.users){
				if(u.Tipo!=tipo){
					System.out.println("Algum deles nao pode pertencer a esta lista");
					return -1;
				}
			}
			CopyOnWriteArrayList<UserInfo> possiveis=svrmi.RetornaPossiveisCandidatos(eleicao, tipo);
			if(!possiveis.containsAll(this.users))
				return -1;
			lista=new ListasCandidatas(tipo, users, nome);
			return svrmi.AdicionarListaCand(lista, eleicao, tipo);
		}
		else if(eleicao.Tipo==4){
			tipo=2;
			for(UserInfo u: this.users){
				if(u.Tipo!=tipo){
					System.out.println("Algum deles nao pode pertencer a esta lista");
					return -1;
				}
			}
			CopyOnWriteArrayList<UserInfo> possiveis=svrmi.RetornaPossiveisCandidatos(eleicao, tipo);
			if(!possiveis.containsAll(this.users))
				return -1;
			lista=new ListasCandidatas(tipo, users, nome);
			return svrmi.AdicionarListaCand(lista, eleicao, tipo);
		}
		else if(eleicao.Tipo==2){
			for(UserInfo u: this.users){
				if(u.Tipo!=tipo){
					System.out.println("Algum deles nao pode pertencer a esta lista");
					return -1;
				}
			}
			CopyOnWriteArrayList<UserInfo> possiveis=svrmi.RetornaPossiveisCandidatos(eleicao, tipo);
			if(!possiveis.containsAll(this.users))
				return -1;
			lista=new ListasCandidatas(tipo, users, nome);
			return svrmi.AdicionarListaCand(lista, eleicao, tipo);
		}
		return 99;
	}

}
