package RMI;

import java.rmi.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

import Consola.ConRemoto;

public interface RmiRemoto extends Remote {
	
	//---------------------------------------------------------------------------------------------------------------------------
		//Variaveis globais
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");//EXEMPLO:16/10/2017 11:00:00 PM
		
		static CopyOnWriteArrayList<UserInfo> Lista_de_users = new  CopyOnWriteArrayList<UserInfo>();
		
		 static CopyOnWriteArrayList<UserInfo> Lista_de_logged_users  = new CopyOnWriteArrayList<UserInfo>();
		
		static CopyOnWriteArrayList<EleicaoInfo> Lista_de_eleicoes_nao_iniciadas = new CopyOnWriteArrayList<EleicaoInfo>();
		
		static CopyOnWriteArrayList<EleicaoInfo> Lista_de_eleicoes_acorrer =new CopyOnWriteArrayList<EleicaoInfo>();
		
		static CopyOnWriteArrayList<EleicaoInfo> Lista_de_eleicoes_acabadas = new CopyOnWriteArrayList<EleicaoInfo>();
		
		static CopyOnWriteArrayList<DepFacInfo> Lista_de_depfac = new CopyOnWriteArrayList<DepFacInfo>();
		
		
		 CopyOnWriteArrayList<ConRemoto> ConnectedList = new CopyOnWriteArrayList<ConRemoto>();
		
	
	
	//Metodos Remotos Consola---------------------------------------------------------------------------------------------------------------------------

	public boolean CriarUser(UserInfo user) throws RemoteException;//Argumentos- user = user a inserir// retorna false se existir ja uma pessoa com esse bi registado, retorna true se for bem sucedido
	
	public String AdicionarDepFac(DepFacInfo dep) throws RemoteException;//Argumentos- dep = a inserir//retorna uma string a dizer que foi bem sucedido
	
	//public String AlterarDepFac() throws RemoteException;
	
	//public String RemoverDepFac(String nomedepfac) throws RemoteException;//ATENCAO a funcao devera checkar os departamentos da faculdade(A arraylist departamentos) para ver se encontra //Argumentos- nomedepfac=nome do dep/fac a remover //Retorna uma string a dizer se foi bem sucedido ou nao consoante se a fac/dep existe ou nao
	
	public String CriarEleicao(EleicaoInfo eleicao) throws RemoteException;//Argumentos- eleicao e do tipo eleicaoInfo que contem todos os dados relativos a eleicao//Retorna uma string a dizer que foi bem sucedido
	
	public int AdicionarListaCand(ListasCandidatas lista, EleicaoInfo eleicaoescolhida, int aux) throws RemoteException;//Argumentos-lista que contem os elementos dessa lista e o nome da lista,indice= indice em que esta a eleicao na arraylist,aux=  para saber de que tipo e a lista//retorna 1 se o nome da lista nao esta em uso caso contrario retorna 0
	
	public String RemoverListaCand(EleicaoInfo eleicao,String nomelistarem) throws RemoteException;//Argumento-indiceeleicao para saber onde se encontra a eleicao escolhida, nomelistarem = nome da lista a remover// retorna uma string a dizer se foi bem sucedido ou nao consoante se a lista com o nomerem existe

	public CopyOnWriteArrayList<DepFacInfo> RetornaDepFac() throws RemoteException; //Retorna a lista de dep/fac existentes

	public CopyOnWriteArrayList<EleicaoInfo> RetornaEleicoesConsola() throws RemoteException;//Retorna a lista de eleicoes que nao tenham ainda iniciado 
	
	public CopyOnWriteArrayList<UserInfo> RetornaPossiveisCandidatos(EleicaoInfo eleicao, int tipo_de_lista) throws RemoteException;//ATENCAO:Retornar uma lista de candidatos que inda nao tenham sido usados noutra lista 1 pessoas nao pode tar em 2 listas//Argumentos-eleicao= eleicao que o admin escolheu,//tipo= 0 para eleicoes que nao sejam para conselho geral caso contrario 1,2,3 para saber que tipo de lista e que se quer criar (estudantes,funcionarios,docentes) //Retorna a lista de pessoas que podem ser usadas para criar uma nova lista

	public CopyOnWriteArrayList<EleicaoInfo> RetornaEleicoesNaoIniciadas(double bi) throws RemoteException;//ATENCAO tem que apenas retornar as eleicoes onde o user pode votar // Retorna todas as eleicoes inda nao comecadas para se poder fazer o voto antecipado//Argumentos bi= bi do user para saber quem e e obter os dados
	
	public String VotoAntecipado(Voto v, EleicaoInfo eleicaoescolhida) throws RemoteException ;//Argumentos-voto= objecto voto,eleicao=eleicao onde o user votou,Retorna uma string a dizer que foi bem sucedido
	
	public UserInfo AutenticarEleitor(double BI,String pass) throws RemoteException;//Argumentos- Bi numero de bi para se identificar, pass = password,Retorna NULL se as credenciais nao corresponderem caso contrario retorna o user
	
	public UserInfo RetornaUser(double BI) throws RemoteException;//Retorna o user de acordo com o bi

	public String AlteraUserInfo(UserInfo novo,UserInfo velho) throws RemoteException;//Argumento-m= Como se fosse um novo user so que a funcao em vez de criar um novo simplesmente vai encontrar o user com o determinado bi e fazer set()//Retorna uma string a dizer que foi bem sucedido

	public String UpdateEleicao(EleicaoInfo novo,EleicaoInfo velho) throws RemoteException;//Argumentos eleicao = eleicao com as propriedades alteradas axtualizadas// retorna uma string a dizer se foi bem sucedido ou nao consoante o tempo que falta para a eleicao

	public CopyOnWriteArrayList<UserInfo> RetornaTodosUsers() throws RemoteException;//Retorna todos Users
	
	public CopyOnWriteArrayList<UserInfo> RetornaUsersLogados() throws RemoteException;//RetornaUserLogados
	
	public CopyOnWriteArrayList<EleicaoInfo> RetornaEleicoesAcabadas() throws RemoteException;//Retorna Eleicoes acabadas

	public void registerForCallback(ConRemoto callbackClientObject) throws RemoteException;//Regista a consola para callbacks

	void unregisterForCallback(ConRemoto callbackClientObject) throws RemoteException;//Desregisa a consola para callbacks

	//Metodos Remotos SvTCP---------------------------------------------------------------------------------------------------------------------------------------------------
	public CopyOnWriteArrayList<EleicaoInfo> RetornaEleicoesacorrer() throws RemoteException;//Argumentos-dep=departamento em que o svtcp esta retorna todas as eleicoes 
	
	public boolean Logar(UserInfo user) throws RemoteException; //Argumentos-user=objecto do tipo UserInfo a passar para a lista de pessoas logadas//Retorna true se a pessoa nao se escontra loggada caso contrario false
	
	public UserInfo Identificar(double bi, EleicaoInfo eleicao) throws RemoteException;//Argumentos-Bi=bi da pessoa para ver se existe,Eleicao=eleicao que a mesa escolheu//Retorna null se o user nao existe caso contrario retorna o user
	
	
	public boolean Votar(Voto umvoto,EleicaoInfo eleicao) throws RemoteException;//Argumentos-umvoto= voto da pessoa,eleicao=eleicao para onde o voto vai ser adicionado//Retorna true caso a eleicao ainda esteja aberta, false caso a eleicao tenha fechado
	
	public boolean Mesa_Updater(EleicaoInfo eleicao,Mesa_voto mesa) throws RemoteException;
	
	public Date getdate() throws RemoteException;
	
	//------------------------------------------------------------------------------------------------------------------------------
	
	public void HelloRMI() throws RemoteException;
}

