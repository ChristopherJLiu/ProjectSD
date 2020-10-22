package Consola;

import RMI.*;
/*try {
	Algum metodo remoto
} catch (Exception e1) {
	// TODO Auto-generated catch block
	System.out.println("Operacao nao registada servidor RMI foi-se");
	Connection();
	return;
} Todos os metodos remotos contem este try catch que avisa ao admin que a operacao que ele estava a fazer nao foi registada porque o SVRMI foi abaixo e volta a tentar conectarse*/



import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

import RMI.DepFacInfo;
import RMI.EleicaoInfo;
import RMI.ListasCandidatas;
import RMI.Mesa_voto;
import RMI.RmiRemoto;
import RMI.UserInfo;
import RMI.Voto;

import java.util.Date;
import java.util.InputMismatchException;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Consola extends UnicastRemoteObject implements ConRemoto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Consola() throws RemoteException{
		super();
	}
	
	static String portNum="12345";
	volatile static RmiRemoto h;
	volatile static ConRemoto callbackObj;
	

	
	
	
//Metodos Remotos////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void Notify_Voto(EleicaoInfo eleicao) {//Consolas de administracao mostram eleitores em tempo real
		System.out.println("Notificacao de Novo Voto");
		System.out.println("Ate agora votam na eleicao:"+eleicao.Titulo+"Com Descricao:"+eleicao.Descricao);
		System.out.println((eleicao.Votos_Eleicao.size()+1)+" Votantes");
	}
	
	@Override
	public void Notify_InicioEleicao(EleicaoInfo eleicao) {//Mostrar que comecou uma eleicao Feito para o grupo
		System.out.println("Notificacao Comeco de Eleicao");
		System.out.println("Comecou :"+eleicao.Titulo+"Com Descricao:"+eleicao.Descricao);
	}
	
	@Override
	public void Notify_FimEleicao(EleicaoInfo eleicao) {//Mostrar que acabou uma eleicao  Feito para o grupo
		System.out.println("Notificacao Fim de Eleicao");
		System.out.println("Acabou :"+eleicao.Titulo+"Com Descricao:"+eleicao.Descricao);
	}
	
	@Override
	public void Notify_Estado_Mesa(Mesa_voto mesa,EleicaoInfo eleicao) {//Consolas de administracao mostram o estado das mesas de voto
		System.out.println("Notificacao Estado Mesa");
		if(mesa.estado) {
			System.out.println("A mesa de voto em "+mesa.local.Nome+" acabou de se ligar na eleicao:" +eleicao.Titulo);
		}
		if(!mesa.estado) {
			System.out.println("A mesa de voto em "+mesa.local.Nome+" acabou de se desligar na eleicao" + eleicao.Titulo);
		}
	}
	
	
	

////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread()//Garante que se a consola for fechada com CTRL-C ou System.exit(0) esta e retirada da lista de callbacks que existe no SVRMI
        {
            @Override
            public void run()
            {
            	try {
					h.unregisterForCallback(callbackObj);//Peido para desregistar
					System.out.println("Foi deregistado de callbacks");
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					return;
				}
            }
        });
		Connection();
		MenuPrincipal();
		
	}
	
	
	
	
	public static void Connection(){//Trata de se connectar com o SV RMI
		try {
				String registryURL = "rmi://localhost:" + portNum + "/callback";
		      h = (RmiRemoto) Naming.lookup(registryURL);
		      System.out.println("Lookup completed " );
		      callbackObj = new Consola();
		      h.registerForCallback(callbackObj);
		      h.HelloRMI();
		}catch (Exception e) {//Caso o Servidor esteja em baixo ele continua a tentar ate um SVRMI se voltar a ligar
			System.out.println("RMI Server Down Tentando de novo");
			Connection();
		}
	}
	
	

//-------------------------------------------------------------------------------------
	
	//Menu principal
	public static void MenuPrincipal() {
		System.out.println("Bem Vindo a consola de Admin");
		int choice = -1;
		while(choice!=12) {
			System.out.println("Escolha uma das opcoes usando um inteiro");
			System.out.println("1-Registar pessoa");
			System.out.println("2-Gerir departamento ou faculdade");
			System.out.println("3-Criar eleicao");
			System.out.println("4-Gerir lista de candidatos a uma eleicao");
			System.out.println("5-Gerir mesas de voto");
			System.out.println("6-Alterar propriedades de uma eleicao");
			System.out.println("7-Saber em que lugar votou os eleitores numa eleicao");
			System.out.println("8-Consultar dados de eleições passadas");
			System.out.println("9-Voto Antecipado");
			System.out.println("10-Alterar dados pessoais");
			System.out.println("11-Gerir membros de cada mesa de voto");
			System.out.println("12-Desligar Consola");
			choice = IntScanner(1,12);
			 if(choice==1)
				Func1(h);//Completo a funcionar
			else if(choice==2)
				Func2(h);//Falta completar de acordo com BD funcoes Alterar e Remover Dep/Fac nao estao a funcionar mas op 1 ta a funcionar
			else if(choice==3)
				Func3(h);//Completo funcionar
			else if(choice==4)
				Func4(h);//Completo a funcionar
			else if(choice==5)
				Func5(h);//Completo a funcionar
			else if(choice==6)
				Func6(h);//Completo a funcionar
			else if(choice==7)
				Func7(h);//Completo a funcionar
			else if(choice==8)
				Func8(h);//Completo nao ta testado
			else if(choice==9)
				Func9(h);//Completo a funcionar
			else if(choice==10)
				Func10(h);//Completo a funcionar
			else if(choice==11)
				Func11(h);//Completo a funcionar
			 
			else if(choice==12) {//Desligar consola e pedido para desregistar dos callbacks
				 try {
					h.unregisterForCallback(callbackObj);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					return;
				}
			 return;
			}
		}
	}
	

	
	
//--------------------------------------------------------------------------------------
	//Funcoes para as opcoes 1-5 9-18
	
	
	public static void Func1 (RmiRemoto h) {//Registar Pessoas
		DepFacInfo dep = null;
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduza o nome da pessoa");
		String nome = sc.nextLine();
		System.out.print("Escolha se a pessoa é um \n1-Aluno\n2-Docente\n3-Funcionario");
		int tipo = IntScanner(1,3);
		System.out.print("Introduza uma password");
		String ps = sc.nextLine();
		System.out.print("Introduza o Contacto Telefonico");
		double CT = DoubleScanner(9);
		System.out.print("Introduza a morada");
		String morada = sc.nextLine();
		System.out.print("Introduza o número do Bi");
		double BI = DoubleScanner(9);
		System.out.println("Introduza a validade do BI no formato'dd/MM/yyyy HH:mm'\nExemplo 27/12/2030 11:00");
		Date BIVal=DateScanner();
		System.out.print("Insira o nome da faculdade consoante a lista seguinte ATENCAO Caps");
		CopyOnWriteArrayList<DepFacInfo>listafac=null;
		try {
			listafac = h.RetornaDepFac();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Operacao nao registada servidor RMI foi-se");
			Connection();
			return;
		}
		DepReader(listafac);
		String escolha = sc.nextLine();
		dep = DepFinder(escolha,listafac);
		int n_trys=0;
		while(dep==null) {
			if(n_trys==3) {
				System.out.println("Excedeu o numero de tentativas tente mais tarde");
				return;
			}
			System.out.println("Introduza uma faculdade ou departamento que exista");
			escolha = sc.nextLine();
			dep = DepFacFinder2(escolha,listafac);
			n_trys++;
		}
		UserInfo m = new UserInfo(nome,tipo,ps,CT,morada,BI,BIVal,dep);
		boolean sucess=false;
		try {
			sucess=h.CriarUser(m);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Operacao nao registada servidor RMI foi-se");
			Connection();
			return;
		}
		if(sucess)
			System.out.println("User criado com sucesso");
		else
			System.out.println("Ja existe um user com esse BI");
	}
	
	
	
	public static void Func3(RmiRemoto h) {//Criar eleicao
		Scanner sc = new Scanner(System.in);
		DepFacInfo dep = null;
		System.out.print("Introduza o tipo de eleição\n1-Nucleo de estudantes\n2-Conselho Geral\n3-Direcao Departamento\n4-Direcao Faculdade");
		int tipo = IntScanner(1,4);
		System.out.println("Introduza a data do inicio da eleicao no formato'dd/MM/yyyy HH:mm'\nExemplo 27/12/2030 11:00");
		Date inicio=DateScanner();
		System.out.println("Introduza a data do fim da eleicao no formato'dd/MM/yyyy HH:mm'\nExemplo 27/12/2030 11:00");
		Date fim=DateScanner();
		System.out.println("Introduza o titulo da eleicao");
		String tit = sc.nextLine();
		System.out.println("Introduza uma breve descricao");
		String desc = sc.nextLine();
		if(tipo==1 || tipo==3) {//Tipo de eleicao 1=eleicao de nucleo 3-Eleicao
			System.out.print("Insira o nome da departamento onde vai ocorrer consoante a lista seguinte ATENCAO Caps\n");
			CopyOnWriteArrayList<DepFacInfo>listafac=null;
			try {
				listafac = h.RetornaDepFac();//retornar todas os deps e fac existentes
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Operacao nao registada servidor RMI foi-se");
				Connection();
				return;
			}
			DepReader(listafac);
			String escolha = sc.nextLine();
			dep = DepFinder(escolha,listafac);
			int n_trys=0;
			while(dep==null) {
				if(n_trys==3) {//Numero de tentativas que a pessoa tem para escrever um nome correcto de um departamento
					System.out.println("Excedeu o numero de tentativas tente mais tarde");
					return;
				}
				System.out.println("Introduza um departamento que exista");
				escolha = sc.nextLine();
				dep = DepFinder(escolha,listafac);
				n_trys++;
			}
			EleicaoInfo e = new EleicaoInfo(tipo,dep,inicio,fim,tit,desc);
			try {
				System.out.println(h.CriarEleicao(e));//Criar eleicao
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println("Operacao nao registada servidor RMI foi-se");
				Connection();
				return;
			}
		}
		
		else if(tipo==4) {//Tipo 4=Eleicao para direcao de faculdade
			System.out.print("Insira o nome da faculdade onde vai ocorrer consoante a lista seguinte ATENCAO Caps");
			CopyOnWriteArrayList<DepFacInfo>listafac=null;
			try {
				listafac = h.RetornaDepFac();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Operacao nao registada servidor RMI foi-se");
				Connection();
				return;
			}
			FacReader(listafac);
			String escolha = sc.nextLine();
			dep = FacFinder(escolha,listafac);
			int n_trys=0;
			while(dep==null) {
				if(n_trys==3) {
					System.out.println("Excedeu o numero de tentativas tente mais tarde");
					return;
				}
				System.out.println("Introduza uma faculdade ou departamento que exista");
				escolha = sc.nextLine();
				dep = FacFinder(escolha,listafac);
				n_trys++;
			}
			EleicaoInfo e = new EleicaoInfo(tipo,dep,inicio,fim,tit,desc);
			try {
				System.out.println(h.CriarEleicao(e));//Criar eleicao
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println("Operacao nao registada servidor RMI foi-se");
				Connection();
				return;
			}
		}
		else {//Tipo =2 eleicao conselho geral
			EleicaoInfo e = new EleicaoInfo(tipo,null,inicio,fim,tit,desc);
			try {
				System.out.println(h.CriarEleicao(e));//Criar eleicao
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println("Operacao nao registada servidor RMI foi-se");
				Connection();
				return;
			}
		}
		
	}
	
	
	
	public static void Func2(RmiRemoto h) {//Gerir Departamentos e faculdades
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduza o que pretende fazer\n1-Adicionar Faculdade/Departamento\n2-Alterar dados\n3-Remover\n Opcoes 2,3 Estao desabilitadas por enquanto Meta1 SD");
		//int op = IntScanner(1,3); Isto esta para BD mas como a checkList de SD apenas contem Criar as outras opcoes estao desabilitadas
		int op = IntScanner(1,1);
		
		if(op==1) {//Criar Faculdade
			CopyOnWriteArrayList<DepFacInfo> deps= new CopyOnWriteArrayList<DepFacInfo>();
			System.out.print("Introduza o nome da Faculdade");
			String nome = sc.nextLine();
			System.out.println("Indique o numero de departamentos que a faculdade tem");
			int ndep=IntScanner(1,99);
			for(int i=0;i<ndep;i++) {
				System.out.println("Indique o nome do departamento");
				String nomedep=sc.nextLine();
				DepFacInfo depx= new DepFacInfo(2,nomedep,null);
				deps.add(depx);
			}
			DepFacInfo m = new DepFacInfo(1,nome,deps);
			try {
				System.out.println(h.AdicionarDepFac(m));//Adicionar a faculdade
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Operacao nao registada servidor RMI foi-se");
				Connection();
				return;
			}
		}
		
		else if(op==2) {
			System.out.println("Desabilitado");//Perguntar ao stor de Bd que propriedas e que a fac/dep tem que ter
		}
		
		
		else if(op==3) {
			System.out.println("Indique o nome da faculdade ou departamento que prentende remover");
			String nomerem=sc.nextLine();
			System.out.println("Desabilitado");
			/*try {
				System.out.println(h.RemoverDepFac(nomerem));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}	
	}
	
	
	public static void Func4(RmiRemoto h) {//Adicionar e remover Listas candidatas
		System.out.println("Escolha uma opcao das seguintes\n1-Adicionar uma lista candidata a uma eleicao\n2-Remover uma lista candidata a uma eleicao");
		int op = IntScanner(1,2);
		if(op==1) {//Adicionar Listas candidatas
			CopyOnWriteArrayList<EleicaoInfo> eleicoesdisponiveis= new CopyOnWriteArrayList<EleicaoInfo>();
			try {
				eleicoesdisponiveis=h.RetornaEleicoesConsola();//Retorna todas as eleicoes nao iniciadas 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Operacao nao registada servidor RMI foi-se");
				Connection();
				return;
			}
			if(eleicoesdisponiveis.isEmpty())//Caso nao hajam eleicoes criadas
				System.out.println("Nao existem eleicoes disponiveis para adicionar novas listas candidatas");
			else {
				System.out.println("Escolha uma das eleicoes seguintes usando o numero apresentado");
				System.out.println("Tipo de eleicoes 1-Nucleo de estudantes 2-ConselhoGeral 3-Direcao Departamento 4-Direcao Faculdade");
				for(int i=0;i<eleicoesdisponiveis.size();i++) {
					System.out.println(Integer.toString(i)+"-Titulo:"+eleicoesdisponiveis.get(i).Titulo+" Descricao:"+eleicoesdisponiveis.get(i).Descricao+" Tipo de eleicao:" + eleicoesdisponiveis.get(i).Tipo);
				}
				int escolha = IntScanner(0,eleicoesdisponiveis.size()-1);
				EleicaoInfo eleicaoescolhida = eleicoesdisponiveis.get(escolha);
				CopyOnWriteArrayList<UserInfo> possiveis_candidatos = new CopyOnWriteArrayList<UserInfo>();
				int aux=0;//Variavel auxiliar para saber que tipo de lista o admin quer registar
				
				//Todos os ifs e else if daqui em diantes tem como objectivo verificar o tipo da eleicao e retorna uma lista de candidatos adequada a eleicao
				if(eleicaoescolhida.Tipo==2) {
					System.out.println("Introduza o tipo de lista que pretende criar\n1-Estudantes\n2-Docentes\n3-Funcionarios");
					aux=IntScanner(1,3);
					try {
						possiveis_candidatos=h.RetornaPossiveisCandidatos(eleicaoescolhida,aux);//Pede os possiveis candidatos para aquele tipo de lista numa eleicao
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("Operacao nao registada servidor RMI foi-se");
						Connection();
						return;
					}
				}
				else if(eleicaoescolhida.Tipo==1) {
					aux=1;
					try {
						possiveis_candidatos=h.RetornaPossiveisCandidatos(eleicaoescolhida,1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("Operacao nao registada servidor RMI foi-se");
						Connection();
						return;
					}
				}
				else if(eleicaoescolhida.Tipo==3) {
					aux=3;
					try {
						possiveis_candidatos=h.RetornaPossiveisCandidatos(eleicaoescolhida,3);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("Operacao nao registada servidor RMI foi-se");
						Connection();
						return;
					}
				}
				else if(eleicaoescolhida.Tipo==4) {
					aux=3;
					try {
						possiveis_candidatos=h.RetornaPossiveisCandidatos(eleicaoescolhida,3);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("Operacao nao registada servidor RMI foi-se");
						Connection();
						return;
					}
				}
				
				//Caso haja candidatos
				if(possiveis_candidatos.size()!=0) {
					System.out.println("Lista de Possiveis Candidatos a uma lista da eleicao escolhida");
					for(int i=0;i<possiveis_candidatos.size();i++) {
						System.out.println(Integer.toString(i)+"-Nome:"+possiveis_candidatos.get(i).Nome+"Numero Bi:"+(possiveis_candidatos.get(i).BI));
					}
					System.out.println("Introduza quantas pessoas pretende adicionar");
					int n_pessoas=IntScanner(1,possiveis_candidatos.size());
					CopyOnWriteArrayList<UserInfo> Lista_Candidata = new CopyOnWriteArrayList<UserInfo>();
					for(int i=0;i<n_pessoas;i++) {//Adicionar s pessoas
						System.out.println("Introduza o Numero que aparece a frente da pessoa na lista anterior para inserir a pessoa na lista a ser criada");
						int esc = IntScanner(0,possiveis_candidatos.size()-1);
						while(Lista_Candidata.contains(possiveis_candidatos.get(esc))) {
							System.out.println("Pessoa ja existente na lista tente de novo com outro numero\n");
							esc = IntScanner(0,possiveis_candidatos.size()-1);
						}
						Lista_Candidata.add(possiveis_candidatos.get(esc));
					}
					System.out.println("Introduza um nome para a lista");
					Scanner sc = new Scanner(System.in);
					String nome_lista = sc.nextLine();
					int nome_ver=0;
					ListasCandidatas lista = new ListasCandidatas(aux,Lista_Candidata,nome_lista);
					while(nome_ver==0) {
						try {//Enviar a lista para o rmi 
							nome_ver=h.AdicionarListaCand(lista,eleicaoescolhida,aux);//Envia a lista candidata e caso receba 0 o nome da lista esta em uso caso contrario retorna 1
						} catch (Exception e) {
							// TODO Auto-generated catch block
							System.out.println("Operacao nao registada servidor RMI foi-se");
							Connection();
							return;
						}
						if(nome_ver==0) {//caso o nome esteja em caso
							System.out.println("Nome de lista em uso tente outro nome");
							nome_lista=sc.nextLine();
							lista = new ListasCandidatas(aux,Lista_Candidata,nome_lista);
						}
						else if (nome_ver==1)
							System.out.println("Lista criada");
						else {//Caso a eleicao que a escolheu ja teha iniciado ou tenha sido alterado por outra consola
							System.out.println("A eleicao escolhida ja comecou ou foi alterada por outra consola tente de novo");
							return;
						}
					}
					
				}
				else
					System.out.println("Nao exitem Pessoas candidatos possiveis para uma nova lista");
			}
		}
		else if(op==2) {//Remover Lista candidata
			CopyOnWriteArrayList<EleicaoInfo> eleicoesdisponiveis= new CopyOnWriteArrayList<EleicaoInfo>();
			try {
				eleicoesdisponiveis=h.RetornaEleicoesConsola();//Pedido para retornar todas as eleicoes da consola
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Operacao nao registada servidor RMI foi-se");
				Connection();
				return;
			}
			if(eleicoesdisponiveis.isEmpty())
				System.out.println("Nao existem eleicoes disponiveis para remover listas candidatas");
			else {
				System.out.println("Escolha uma das eleicoes seguintes usando o numero apresentado");
				for(int i=0;i<eleicoesdisponiveis.size();i++) {//Apresenta as eleicoes
					System.out.println(Integer.toString(i)+"-Titulo"+eleicoesdisponiveis.get(i).Titulo+" Descricao:"+eleicoesdisponiveis.get(i).Descricao);
				}
				int escolha = IntScanner(0,eleicoesdisponiveis.size()-1);
				System.out.println("Indique a lista que pretende remover indicando o nome desta");
				Scanner sc = new Scanner(System.in);
				String nomerem = sc.nextLine();
				try {//Envia o pedido ao servidor rmi para apagar a lista
					System.out.println(h.RemoverListaCand(eleicoesdisponiveis.get(escolha), nomerem));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Operacao nao registada servidor RMI foi-se");
					Connection();
					return;
				}
			}
		}
	}
	
	public static void Func6(RmiRemoto h) {//Alterar propriedades de uma eleicao
		CopyOnWriteArrayList<EleicaoInfo> eleicoesdisponiveis= new CopyOnWriteArrayList<EleicaoInfo>();
		Scanner sc = new Scanner(System.in);
		try {
			eleicoesdisponiveis=h.RetornaEleicoesConsola();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Operacao nao registada servidor RMI foi-se");
			Connection();
			return;
		}
		if(eleicoesdisponiveis.isEmpty())
			System.out.println("Nao existem eleicoes disponiveis para alterar propriedades");
		else {
			System.out.println("Escolha uma das eleicoes seguintes usando o numero apresentado");
			for(int i=0;i<eleicoesdisponiveis.size();i++) {
				System.out.println(Integer.toString(i)+"-Titulo:"+eleicoesdisponiveis.get(i).Titulo+" Descricao:"+eleicoesdisponiveis.get(i).Descricao);
			}
			int escolha = IntScanner(0,eleicoesdisponiveis.size()-1);
			EleicaoInfo eleicaoescolhida = eleicoesdisponiveis.get(escolha);
			EleicaoInfo velho = eleicaoescolhida.deepClone();//Copia da eleicao antes da alteracao
			System.out.println("Introduza um novo titulo");
			eleicaoescolhida.Titulo=sc.nextLine();
			System.out.println("Introduza uma nova descricao");
			eleicaoescolhida.Descricao=sc.nextLine();
			try {//Envia a nova eleicao com os dados alterados e a velha para o rmi
				System.out.println(h.UpdateEleicao( eleicaoescolhida,velho));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Operacao nao registada servidor RMI foi-se");
				Connection();
				return;
			}
		}
	}
	
	public static void Func7(RmiRemoto h) {//Saber em que local cada eleitor votou numa especifica eleicao
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
		CopyOnWriteArrayList<EleicaoInfo> eleicoesdisponiveis= new CopyOnWriteArrayList<EleicaoInfo>();
		Scanner sc = new Scanner(System.in);
		try {
			eleicoesdisponiveis=h.RetornaEleicoesConsola();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Operacao nao registada servidor RMI foi-se");
			Connection();
			return;
		}
		if(eleicoesdisponiveis.isEmpty())
			System.out.println("Ate ao momento nao existem eleicoes acabadas");
		else {
			System.out.println("Escolha uma das eleicoes seguintes usando o numero apresentado");
			for(int i=0;i<eleicoesdisponiveis.size();i++) {
				System.out.println(Integer.toString(i)+"-Titulo:"+eleicoesdisponiveis.get(i).Titulo+" Descricao:"+eleicoesdisponiveis.get(i).Descricao);
			}
			int escolha = IntScanner(0,eleicoesdisponiveis.size()-1);
			EleicaoInfo eleicaoescolhida = eleicoesdisponiveis.get(escolha);
			for(int i=0;i< eleicaoescolhida.Votos_Eleicao.size();i++ ) {
				System.out.println("Numero do Bi:"+Double.toString(eleicaoescolhida.Votos_Eleicao.get(i).BI)+" Local de voto:"+eleicaoescolhida.Votos_Eleicao.get(i).Mesa.local.Nome+" Data de voto:"+dateFormat.format(eleicaoescolhida.Votos_Eleicao.get(i).Data));
			}
		}
	}
	
	public static void Func9(RmiRemoto h) {//Voto Antecipado
		System.out.println("Introduza o seu n Bi");
		double bi = DoubleScanner(9);
		System.out.println("Introduza a password");
		Scanner sc = new Scanner(System.in);
		String pass = sc.nextLine();
		UserInfo user = null;
		try {
			user=h.AutenticarEleitor(bi, pass);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Operacao nao registada servidor RMI foi-se");
			Connection();
			return;
		}
		if(user==null)
			System.out.println("Credenciais invalidas");
		else {
			System.out.println("Bem vindo ao iVotasAntecipado");
			DepFacInfo dep_consola = new DepFacInfo(2,"Consola de Admin",null);//Dep consola para quando for se averiguar ond votou
			Mesa_voto mesa_consola = new Mesa_voto(dep_consola,1);
			CopyOnWriteArrayList<EleicaoInfo> eleicoesdisponiveis= new CopyOnWriteArrayList<EleicaoInfo>();
			try {
				eleicoesdisponiveis=h.RetornaEleicoesNaoIniciadas(user.BI);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Operacao nao registada servidor RMI foi-se");
				Connection();
				return;
			}
			if(eleicoesdisponiveis.isEmpty())
				System.out.println("De momento nao existem eleicoes ainda nao iniciadas onde voce pode votar");
			else {
				System.out.println("Escolha uma das eleicoes seguintes usando o numero apresentado");
				for(int i=0;i<eleicoesdisponiveis.size();i++) {
					System.out.println(Integer.toString(i)+"-Titulo:"+eleicoesdisponiveis.get(i).Titulo+" Descricao:"+eleicoesdisponiveis.get(i).Descricao);
				}
				int escolha = IntScanner(0,eleicoesdisponiveis.size()-1);
				EleicaoInfo eleicaoescolhida = eleicoesdisponiveis.get(escolha);
				System.out.println("Escolha uma das opcoes introduzindo o nome da lista que pretende votar em:");
				System.out.println("Caso deseje votar em branco ou em nulo introduza 'Branco' ou 'Nulo'");
				System.out.println("Listas candidatas");
				if(user.Tipo==1) {
					for(int i = 0; i<eleicaoescolhida.Listas_CandidatasE.size();i++) {
						System.out.println(eleicaoescolhida.Listas_CandidatasE.get(i).nome);
					}
					String aux = sc.nextLine();
					try {
						Voto voto = new Voto(user.BI,mesa_consola,aux);
						System.out.println(h.VotoAntecipado(voto,eleicaoescolhida));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("Operacao nao registada servidor RMI foi-se");
						Connection();
						return;
					}
				}
				else if(user.Tipo==2) {
					for(int i = 0; i<eleicaoescolhida.Listas_CandidatasD.size();i++) {
						System.out.println(eleicaoescolhida.Listas_CandidatasD.get(i).nome);
					}
					String aux = sc.nextLine();
					try {
						Voto voto = new Voto(user.BI,mesa_consola,aux);
						System.out.println(h.VotoAntecipado(voto,eleicaoescolhida));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("Operacao nao registada servidor RMI foi-se");
						Connection();
						return;
					}
				}
				if(user.Tipo==3) {
					for(int i = 0; i<eleicaoescolhida.Listas_CandidatasF.size();i++) {
						System.out.println(eleicaoescolhida.Listas_CandidatasF.get(i).nome);
					}
					String aux = sc.nextLine();
					try {
						Voto voto = new Voto(user.BI,mesa_consola,aux);
						System.out.println(h.VotoAntecipado(voto,eleicaoescolhida));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("Operacao nao registada servidor RMI foi-se");
						Connection();
						return;
					}
				}
				
			}
		}
		
	}
	
	public static void Func10(RmiRemoto h) {//Alterar dados pessoais
		UserInfo user = null;
		System.out.println("Introduza o numero do BI da pessoa que pretende alterar");
		double bi = DoubleScanner(9);
		try {
			user=h.RetornaUser(bi);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Operacao nao registada servidor RMI foi-se");
			Connection();
			return;
		}
		if(user==null)
			System.out.println("User nao encontrado");
		else {
			DepFacInfo dep = null;
			Scanner sc = new Scanner(System.in);
			System.out.print("Introduza o nome da pessoa");
			String nome = sc.nextLine();
			System.out.print("Escolha se a pessoa é um \n1-Aluno\n2-Docente\n3-Funcionario");
			int tipo = IntScanner(1,3);
			System.out.print("Introduza uma password");
			String ps = sc.nextLine();
			System.out.print("Introduza o Contacto Telefonico");
			double CT = DoubleScanner(9);
			System.out.print("Introduza a morada");
			String morada = sc.nextLine();
			System.out.println("Introduza a validade do BI no formato'dd/MM/yyyy HH:mm:ss a'\nExemplo 27-12-2030 11:00:00 PM");
			Date BIVal=DateScanner();
			System.out.print("Insira o nome da faculdade consoante a lista seguinte ATENCAO Caps");
			CopyOnWriteArrayList<DepFacInfo>listafac=null;
			try {
				listafac = h.RetornaDepFac();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Operacao nao registada servidor RMI foi-se");
				Connection();
				return;
			}
			DepFacReader(listafac);
			String escolha = sc.nextLine();
			dep = DepFacFinder2(escolha,listafac);
			int n_trys=0;
			while(dep==null) {
				if(n_trys==3) {
					System.out.println("Excedeu o numero de tentativas tente mais tarde");
					return;
				}
				System.out.println("Introduza uma faculdade ou departamento que exista");
				escolha = sc.nextLine();
				dep = DepFacFinder2(escolha,listafac);
				n_trys++;
			}
			UserInfo m = new UserInfo(nome,tipo,ps,CT,morada,bi,BIVal,dep);
			try {
				System.out.println(h.AlteraUserInfo(m,user));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Operacao nao registada servidor RMI foi-se");
				Connection();
				return;
			}
		}
		
	}
	
	public static void Func5(RmiRemoto h) {//Gerir Mesas de voto
		System.out.println("Introduza a opcao que pretende:\n1-Adicionar mesas\n2-Remover Mesas");
		int sc = IntScanner(1,2);
		if(sc==1)
			AddMesas(h);
		else
			RemoveMesas(h);
		
	}
	
	
	public static void AddMesas(RmiRemoto h) {//Adicionar Mesas de voto
		CopyOnWriteArrayList<EleicaoInfo> eleicoesdisponiveis= new CopyOnWriteArrayList<EleicaoInfo>();
		Scanner sc = new Scanner(System.in);
		try {
			eleicoesdisponiveis=h.RetornaEleicoesConsola();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Operacao nao registada servidor RMI foi-se");
			Connection();
			return;
		}
		if(eleicoesdisponiveis.isEmpty())
			System.out.println("Nao existem eleicoes disponiveis para adicionar mesas de voto");
		else {
			System.out.println("Escolha uma das eleicoes seguintes usando o numero apresentado");
			for(int i=0;i<eleicoesdisponiveis.size();i++) {
				System.out.println(Integer.toString(i)+"-Titulo"+eleicoesdisponiveis.get(i).Titulo+" Descricao:"+eleicoesdisponiveis.get(i).Descricao);
			}
			int escolha = IntScanner(0,eleicoesdisponiveis.size()-1);
			EleicaoInfo eleicao = eleicoesdisponiveis.get(escolha);
			EleicaoInfo velho = eleicao.deepClone();
			System.out.println("Indique quantas mesas de voto pretende adicionar");
			int n_mesas = IntScanner(1,99);
			CopyOnWriteArrayList<DepFacInfo> listafac = null;
			try {
				listafac = h.RetornaDepFac();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Operacao nao registada servidor RMI foi-se");
				Connection();
				return;
			}
			int counter=0;
				for(int i=0;i<n_mesas;i++) {
					DepReader(listafac);
					System.out.println("Introduza o nome do departamento onde vai estar a mesa");
					String name = sc.nextLine();
					if(DepFinder(name,listafac)!=null) {
						boolean checker = false;
						for(Mesa_voto m : eleicao.Mesas)
							if(m.local.equals(DepFinder(name,listafac)))
								checker=true;
						if(!checker) {
							System.out.println("Introduza o numero de terminais que a mesa vai ter");
							int n_terminais=IntScanner(1,3);
							Mesa_voto mesa = new Mesa_voto(DepFinder(name,listafac), n_terminais);
							eleicao.Mesas.add(mesa);
							counter++;
						}
						else
							System.out.println("Ja existe uma mesa nesse departamento");
					}
					else
						System.out.println("O departamento inserido nao existe");
				}
				if(counter!=0) {
					try {
						System.out.println(h.UpdateEleicao(eleicao, velho));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("Operacao nao registada servidor RMI foi-se");
						Connection();
						return;
					}
				}
		}
	}
	
	public static void RemoveMesas(RmiRemoto h) {//Remover Mesas de voto
		CopyOnWriteArrayList<EleicaoInfo> eleicoesdisponiveis= new CopyOnWriteArrayList<EleicaoInfo>();
		try {
			eleicoesdisponiveis=h.RetornaEleicoesConsola();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Operacao nao registada servidor RMI foi-se");
			Connection();
			return;
		}
		if(eleicoesdisponiveis.isEmpty())
			System.out.println("Nao existem eleicoes disponiveis para remover mesas de voto");
		else {
			System.out.println("Escolha uma das eleicoes seguintes usando o numero apresentado");
			for(int i=0;i<eleicoesdisponiveis.size();i++) {
				System.out.println(Integer.toString(i)+"-Titulo"+eleicoesdisponiveis.get(i).Titulo+" Descricao:"+eleicoesdisponiveis.get(i).Descricao);
			}
			int escolha = IntScanner(0,eleicoesdisponiveis.size()-1);
			EleicaoInfo eleicao = eleicoesdisponiveis.get(escolha);
			EleicaoInfo velho = eleicao.deepClone();
			System.out.println("Indique quantas mesas de voto pretende remover");
			int n_mesas = IntScanner(0,eleicao.Mesas.size());
			
			for(int i=0;i<n_mesas;i++) {
				MesasVotoReader(eleicao.Mesas);
				System.out.println("Introduza o numero apresentado da faculdade/departamento onde pretende remover a mesa");
				int choice=IntScanner(0,eleicao.Mesas.size());
				eleicao.Mesas.remove(eleicao.Mesas.get(choice));
			}
			try {
				System.out.println(h.UpdateEleicao(eleicao,velho));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Operacao nao registada servidor RMI foi-se");
				Connection();
				return;
			}
			
		}
	}
	
	
	public static void Func11(RmiRemoto h){//Gerir membros da mesa de voto
		CopyOnWriteArrayList<EleicaoInfo> eleicoesdisponiveis= new CopyOnWriteArrayList<EleicaoInfo>();
		Scanner sc = new Scanner(System.in);
		try {
			eleicoesdisponiveis=h.RetornaEleicoesConsola();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Operacao nao registada servidor RMI foi-se");
			Connection();
			return;
		}
		if(eleicoesdisponiveis.isEmpty())
			System.out.println("Nao existem eleicoes disponiveis gerir membros da mesa de voto");
		else {
			System.out.println("Escolha uma das eleicoes seguintes usando o numero apresentado");
			for(int i=0;i<eleicoesdisponiveis.size();i++) {
				System.out.println(Integer.toString(i)+"-Titulo"+eleicoesdisponiveis.get(i).Titulo+" Descricao:"+eleicoesdisponiveis.get(i).Descricao);
			}
			int escolha = IntScanner(0,eleicoesdisponiveis.size()-1);
			EleicaoInfo eleicao = eleicoesdisponiveis.get(escolha);
			EleicaoInfo velho = eleicao.deepClone();
			if(eleicao.Mesas.size()!=0) {
				MesasVotoReader(eleicao.Mesas);
				System.out.println("Indique a mesa onde pretende alterar a composicao da mesa de voto da lista seguinte introduzindo o numero apresentado em frente a mesa");
				int mesax= IntScanner(0,eleicao.Mesas.size());
				if(eleicao.Mesas.get(mesax).membros_mesa.size()==3) {
					System.out.println("Esta mesa ja tem 3 pessoas tente usar a funcao 5 para remover e adicionar a mesa e voltar a esta opcao");
					return;
				}
				CopyOnWriteArrayList <UserInfo> listauser = new CopyOnWriteArrayList<UserInfo>();
				try {
					listauser=h.RetornaTodosUsers();
				}catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Operacao nao registada servidor RMI foi-se");
					Connection();
					return;
				}
				CopyOnWriteArrayList <UserInfo> lista_limpa = Checka_Lista_Mesas(listauser,eleicoesdisponiveis);
				if(lista_limpa.size()>=3) {
					int counter = 0;
					CopyOnWriteArrayList<UserInfo> novos_membros_mesa = new CopyOnWriteArrayList<UserInfo>();
					while(counter<3) {
						UsersReader(lista_limpa);
						System.out.println("Escolha um dos users apresentados na lista para adicionar a mesa inserindo o numero a frente do nome ");
						int userx = IntScanner(0,lista_limpa.size()-1);
						if(!novos_membros_mesa.contains(lista_limpa.get(userx))) {
							novos_membros_mesa.add(lista_limpa.get(userx));
							System.out.println("User adicionado");
							counter++;
						}
						else
							System.out.println("Esse user ja esta na lista de membros desta mesa");
					}
					eleicao.Mesas.get(mesax).membros_mesa = novos_membros_mesa;
					try {
						System.out.println(h.UpdateEleicao(eleicao,velho));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("Operacao nao registada servidor RMI foi-se");
						Connection();
						return;
					}
				}
				else
					System.out.println("Nao existem users suficientes para adicionar a mesas");
			}
			else {
				System.out.println("Tente primeiro adicionar mesas de voto antes de adicionar pessoas a mesas de voto");
				return;
			}
		}
		
	}
	
	
	public static CopyOnWriteArrayList<UserInfo> Checka_Lista_Mesas(CopyOnWriteArrayList<UserInfo> listauser,CopyOnWriteArrayList<EleicaoInfo> eleicoesdisponiveis) {//Percorre todas as mesas em todas as eleicoes para ver se os users na lista ja foram colocados em mesas retorna uma lista com os user que inda nao foram colocados em nenhuma mesa de nenhuma eleicao
		CopyOnWriteArrayList<UserInfo> lista_final= new CopyOnWriteArrayList<UserInfo>();
		boolean check;
		for(UserInfo u : listauser) {
			check=false;
			for(EleicaoInfo e: eleicoesdisponiveis) {
				for(Mesa_voto m : e.Mesas)
					if(m.membros_mesa.contains(u))
						check=true;
			}
			if(check==false)
				lista_final.add(u);
			
		}
		return lista_final;
	}
	
	
	public static void Func8(RmiRemoto h) {//Estatisticas
		CopyOnWriteArrayList<EleicaoInfo> eleicoesacabadas= new CopyOnWriteArrayList<EleicaoInfo>();
		try {
			eleicoesacabadas=h.RetornaEleicoesAcabadas();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Operacao nao registada servidor RMI foi-se");
			Connection();
			return;
		}
		if(eleicoesacabadas.isEmpty())
			System.out.println("Nao existem eleicoes acabadas de momento");
		else {
			System.out.println("Escolha uma das eleicoes seguintes usando o numero apresentado");
			for(int i=0;i<eleicoesacabadas.size();i++) {
				System.out.println(Integer.toString(i)+"-Titulo"+eleicoesacabadas.get(i).Titulo+" Descricao:"+eleicoesacabadas.get(i).Descricao);
			}
			int escolha = IntScanner(0,eleicoesacabadas.size()-1);
			EleicaoInfo eleicao = eleicoesacabadas.get(escolha);
			Printer_Estatisticas(eleicao);
		}
	}



	//-------------------------------------------------------------------------------------------
	//Scanners  e Printers
	
	private static void Printer_Estatisticas(EleicaoInfo eleicao) {//Printer para estatisticas da eleicao
		System.out.println(eleicao.Titulo);
		System.out.println(eleicao.Descricao);
		if(eleicao.Votos_Eleicao.isEmpty())
			System.out.println("Nao houve votos registados nesta eleicao");
		else {
			System.out.println("Numero de Votos totais:"+eleicao.Votos_Eleicao.size());
			int n_listas=eleicao.Listas_CandidatasE.size()+eleicao.Listas_CandidatasD.size()+eleicao.Listas_CandidatasF.size();
			int[] contagem = new int[n_listas+2];
			CopyOnWriteArrayList<String> listas = new CopyOnWriteArrayList<String>();
			for(int i=0;i<n_listas+2;i++) {
				contagem[i]=0;
			}
			for(ListasCandidatas l : eleicao.Listas_CandidatasE)
				listas.add(l.nome);
			for(ListasCandidatas l : eleicao.Listas_CandidatasD)
				listas.add(l.nome);
			for(ListasCandidatas l : eleicao.Listas_CandidatasF)
				listas.add(l.nome);
			listas.add("Branco");
			listas.add("Nulo");
			for(Voto v : eleicao.Votos_Eleicao) {
				if(listas.contains(v.lista_votada))
					contagem[listas.indexOf(v.lista_votada)]++;
				else
					contagem[listas.indexOf("Nulo")]++;
			}
			for(int i=0; i<n_listas; i++) {
				System.out.println("Nome:"+listas.get(i)+" Numero de votos:"+Integer.toString(contagem[i])+ "Percentagem:"+Float.valueOf(contagem[i]/eleicao.Votos_Eleicao.size()));
			}
		}
	}
	
	
	
	public static int IntScanner(int lb, int ub) {//Scanner para ints lb= lower bound , ub=upperbound
		int input = -1;
		Scanner sc;
		while(true) {
			try {
				sc = new Scanner(System.in);
				input = sc.nextInt();
				if(input<lb || input> ub)
					System.out.println("Introduza uma das opcoes de "+Integer.toString(lb)+" a "+ Integer.toString(ub));
				else
					return input;
			}catch (InputMismatchException e) { 
			    System.err.println("Introduza um numero");
			}
		}
	}
	
	
	public static double DoubleScanner( int ndigits) {//Scanner para doubles ndigis = n digitos que o numero e suposto ter
		int input = -1;
		Scanner sc;
		while(true) {
			try {
				sc = new Scanner(System.in);
				input = sc.nextInt();
				if(String.valueOf(input).length()!=ndigits)
					System.out.println("Introduza um numero com o maximo de "+Integer.toString(ndigits)+"digitos" );
				else
					return input;
			}catch (InputMismatchException e) { 
			    System.err.println("Introduza um numero");
			}
		}
	}
	
	
	public static Date DateScanner() {//Scanner de data
		Date date2=null;
		while(true) {
			try {
				Scanner sc = new Scanner(System.in);
				String date = sc.nextLine();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");//EXEMPLO:16/10/2017 11:00:00 PM
			    date2 = dateFormat.parse(date);
			    return date2;
			} catch (ParseException e) {
			    // TODO Auto-generated catch block
				System.err.println("Introduza uma data no formato dd/MM/yyyy HH:mm ");
			}
		}
	}
	
	public static void DepFacReader(CopyOnWriteArrayList<DepFacInfo> lista) {//Printer para printar todos as Fac/Dep existentes
		for(DepFacInfo fac : lista) {
			System.out.println(fac.Nome);
			if(!fac.Departamentos.isEmpty()) {
				for(DepFacInfo dep : fac.Departamentos) {
					System.out.println(dep.Nome);
				}
			}
		}
	}
	
	public static void FacReader(CopyOnWriteArrayList<DepFacInfo> lista) {//Printer para printar todos as Fac/Dep existentes
		for(DepFacInfo fac : lista)
			System.out.println(fac.Nome);
	}
	
	public static void DepReader(CopyOnWriteArrayList<DepFacInfo> lista) {//Printer para printar todos Dep existentes
		System.out.println("Todos os departamentos existentes de momento:");
		for(DepFacInfo fac : lista) {
			if(!fac.Departamentos.isEmpty()) {
				for(DepFacInfo dep : fac.Departamentos) {
					System.out.println(dep.Nome);
				}
			}
		}
	}
	
	public static DepFacInfo FacFinder(String name,CopyOnWriteArrayList<DepFacInfo> lista) {//Encontra se a dep name existe na lista e retorna o objecto depfacinfo 
		for(DepFacInfo fac : lista)
			if(name.equals(fac.Nome))
				return fac;
		return null;
	}
	
	public static DepFacInfo DepFinder(String name,CopyOnWriteArrayList<DepFacInfo> lista) {//Encontra se a dep name existe na lista e retorna o objecto depfacinfo 
		for(DepFacInfo fac : lista) {
			if(!fac.Departamentos.isEmpty()) {
				for(DepFacInfo dep : fac.Departamentos) {
					if(name.equals(dep.Nome))
						return dep;
				}
			}
		}
		return null;
	}
	
	public static DepFacInfo DepFacFinder2(String name,CopyOnWriteArrayList<DepFacInfo> lista) {//Encontra se a fac/dep name existe na lista e retorna o objecto depfacinfo 
		for(DepFacInfo fac : lista) {
			if(name.equals(fac.Nome))
				return fac;
			if(!fac.Departamentos.isEmpty()) {
				for(DepFacInfo dep : fac.Departamentos) {
					if(name.equals(dep.Nome))
						return dep;
				}
			}
		}
		return null;
	}
	
	public static void MesasVotoReader(CopyOnWriteArrayList<Mesa_voto> lista) {//Printer para printar todas as mesas existentes e as suas propriedades
		int counter=0;
		for (Mesa_voto m : lista) {
			System.out.println(Integer.toString(counter)+"-Local da mesa:"+m.local.Nome+" Numero de terminais:"+Integer.toString(m.numero_terminais));
			System.out.println("Pessoas na mesa:");
			if(m.membros_mesa.size()!=0) {
				for(UserInfo u: m.membros_mesa)
					System.out.println("Nome:"+u.Nome+"Dep/Fac a que pretence:"+u.DepFac.Nome);
			}
			else
				System.out.println("Esta mesa de momento nao tem ninguem");
			counter++;
		}	
	}
	
	public static void UsersReader(CopyOnWriteArrayList<UserInfo> lista) {//Printer para printar todos os users existentes e suas propriedades
		int counter=0;
		for(UserInfo u : lista) {
			System.out.println(Integer.toString(counter)+"-Nome:"+u.Nome+" Bi:"+Double.toString(u.BI));
			counter++;
		}
	}
	
}
