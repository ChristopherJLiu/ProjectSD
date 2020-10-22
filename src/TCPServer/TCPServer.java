package TCPServer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import Consola.ConRemoto;
import RMI.EleicaoInfo;
import RMI.ListasCandidatas;
import RMI.Mesa_voto;
import RMI.RmiRemoto;
import RMI.UserInfo;
import RMI.Voto;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Date;

public class TCPServer {
	
	
	static BlockingQueue<UserInfo> queue = new LinkedBlockingQueue<UserInfo>();
	static BlockingQueue<String> recupera  = new LinkedBlockingQueue<String>();
	volatile static RmiRemoto h;
	volatile static ConRemoto callbackObj;
	static String portNum="12345",registryURL,portNum2="34567";
	static int RMIPortNum,nterminais;
	static String addr="192.168.0.2", addr2="192.168.0.1";
	static EleicaoInfo elect;
	static CopyOnWriteArrayList<UserInfo> tuse;
	static Mesa_voto auxmesa;
	static int mesaindex;
	private final Object lock= new Object();
	
	
	
public static void Connectionrmi(){//Trata de se connectar com o SV RMI
		try {
		      String registryURL = "rmi://"+ addr +":" + portNum + "/callback";
		      h = (RmiRemoto) Naming.lookup(registryURL);
		      System.out.println("Lookup completed " );
		      h.HelloRMI();
		      
		}catch (Exception e) {//Caso o Servidor esteja em baixo ele continua a tentar ate um SVRMI se voltar a ligar
			System.out.println("RMI Server Down Tentando de novo");
			Connectionrmi2();
		}
	}

public static void Connectionrmi2(){//Trata de se connectar com o SV RMI
	try {
	      String registryURL = "rmi://"+ addr2 +":" + portNum2 + "/callback";
	      h = (RmiRemoto) Naming.lookup(registryURL);
	      System.out.println("Lookup completed " );
	      h.HelloRMI();
	      
	}catch (Exception e) {//Caso o Servidor esteja em baixo ele continua a tentar ate um SVRMI se voltar a ligar
		System.out.println("RMI Server Down Tentando de novo");
		Connectionrmi();
	}
}


public static UserInfo verificaeleitor() {
	
	double bi;
	UserInfo user;
	System.out.println("BI do utilizador: ");
	
	bi = DoubleScanner(9);
	
	try {
		user= h.Identificar(bi, elect);
		if(user!=null) {
			return user;
		}
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
	
	
}

public static void MenuPrincipal(UserInfo user) {
	System.out.println("Bem Vindo");
	int choice = -1;
	while(choice!=12) {
		System.out.println("Escolha uma das opcoes usando um inteiro");
		System.out.println("1-Verificar user");
		System.out.println("2-Fechar a mesa");
		choice = IntScanner(1,2);
		try {
			if( h.getdate().after(elect.DataF)) {
				System.out.println("A eleição acabou");
				
				elect.Mesas.get(mesaindex).estado=false;
				
				h.Mesa_Updater(elect,auxmesa);
				}
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			Connectionrmi();
		}
		 if(choice==1) {
			 	//System.out.println("verifiquei a data");
			 
				user=verificaeleitor();//retorna user se existir
				if (user==null) {
					System.out.println("não pode votar");
					return;
				}
				else {
					queue.add(user);
					
				}
			 
		 }
		else if(choice==2) {
			elect.Mesas.get(mesaindex).estado=false;
			
			try {
				h.Mesa_Updater(elect,auxmesa);
				System.exit(0);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				
				Connectionrmi();
			}
		}
			
		 return;
		
	}
}

public static String ListtoString(EleicaoInfo eleicao, UserInfo user) {
	int i=0;
	CopyOnWriteArrayList<ListasCandidatas> aux;
	String tosend="type|item_list";
	String aux2;
	int intaux;
	
	if(eleicao.Tipo==1) {
		if(user.Tipo==1) {
			aux = eleicao.Listas_CandidatasE;
			//System.out.println("TAMANHO DA LISTA"+aux.size());
			tosend = tosend +";item_count|"+aux.size();
			
			//System.out.println("TO SEND "+tosend);
			while(i<aux.size()) {
				tosend=tosend+(";"+aux.get(i).nome+"|"+aux.get(i).Lista.get(0).Nome);
				tosend.toString();
				i++;
			}
			i=0;
			return tosend;
		}
	}
	else if(eleicao.Tipo==3) {
		if(user.Tipo==2) {
			aux = eleicao.Listas_CandidatasD;
			tosend= tosend+(";item_count|"+aux.size());
			while(i<aux.size()) {
				tosend=tosend+(";"+aux.get(i).nome+"|"+aux.get(i).Lista.get(0).Nome);
				tosend.toString();
				i++;
			}
			i=0;
			return tosend;
			
		}
			
	}
	else if(eleicao.Tipo==4) {
		if(user.Tipo==2) {
			aux = eleicao.Listas_CandidatasD;
			tosend=tosend+(";item_count|"+aux.size());
			while(i<aux.size()) {
				tosend=tosend+(";"+aux.get(i).nome+"|"+aux.get(i).Lista.get(0).Nome);
				tosend.toString();
				i++;
			}
			i=0;
			return tosend;
		}	
	}
	else if(eleicao.Tipo==2)
		if(user.Tipo==1) {
			aux = eleicao.Listas_CandidatasE;
			tosend=tosend+(";item_count|"+aux.size());
			while(i<aux.size()) {
				tosend=tosend+(";"+aux.get(i).nome+"|"+aux.get(i).Lista.get(0).Nome);
				i++;
			}
			i=0;
			return tosend;
		}
		else if(user.Tipo==2) {
			aux = eleicao.Listas_CandidatasD;
			tosend=tosend+(";item_count|"+aux.size());
			while(i<aux.size()) {
				tosend=tosend+(";"+aux.get(i).nome+"|"+aux.get(i).Lista.get(0).Nome);
				i++;
			}
			i=0;
			return tosend;
			
		}
		else if(user.Tipo==3) {
			aux = eleicao.Listas_CandidatasF;
			tosend=tosend+(";item_count|"+aux.size());
			while(i<aux.size()) {
				tosend=tosend+(";"+aux.get(i).nome+"|"+aux.get(i).Lista.get(0).Nome);
				i++;
			}
			i=0;
			return tosend;
		}
	return tosend;
}

public synchronized static void checktype(String data,UserInfo user,BufferedReader in,BufferedWriter out) {//PAra seleccionar o que fazer no server
	 String[] result = null;
	 result = data.split(";|\\|");
	 String aux;
	 int trade;
	 Voto umvoto=null;
	 String dataaux = null;
	 CopyOnWriteArrayList<EleicaoInfo> update= new CopyOnWriteArrayList<EleicaoInfo>();
	
	 if(result[1].equalsIgnoreCase("login")) {
		 //verificar login
		trade=Integer.parseInt(result[3]);
		if(trade==user.BI) {
			if(result[5].equals(user.Password)) {
				
				aux="type|status;logged|on";
				
				try {
					out.write(aux);
					out.newLine();
					out.flush();
					h.Logar(user);
					dataaux=in.readLine();
					checktype(dataaux,user,in,out);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					
					Connectionrmi();
				}
			}
			else {
				aux="type|status;logged|off";
				
				try {
					out.write(aux);
					out.newLine();
					out.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
				
			}
		}

		
	 }
	 else if(result[1].equalsIgnoreCase("requestlist")) {
		 aux=ListtoString(elect,user);
		 //System.out.println(" LISTA PO USER - "+aux);
		 try {
			out.write(aux);
			out.newLine();
			out.flush();
			dataaux= in.readLine();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("catch do requestlist");
			
		}
		 checktype(dataaux,user,in,out);
	 }
	 else if(result[1].equalsIgnoreCase("vote")) {
		 
		 umvoto = voteconversor(umvoto,data,user);
		 try {
			//System.out.println("BI DO VOTO: "+umvoto.BI+" Mesa DO VOTO: "+umvoto.Mesa.local.Nome+" LISTA VOTADA: " +umvoto.lista_votada+" Titulo DA ELECT: "+elect.Titulo);
			h.Votar(umvoto, elect);
			update = h.RetornaEleicoesacorrer();
			elect = update.get(update.indexOf(elect));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			
			Connectionrmi();
		}
		 try {
			out.write("obrigado pelo voto");
			out.newLine();
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		
		}
	 }
	 
	
}

public static Voto voteconversor(Voto umvoto,String data,UserInfo user) {
	
     
     String[] result = data.split(";|\\|");
     int j=0;
   
     if(result[2].equalsIgnoreCase("lista")) {
    	 umvoto = new Voto(user.BI,auxmesa,result[3]);
    	 //umvoto.BI=user.BI;
    	 //umvoto.Mesa=auxmesa;
    	 //umvoto.lista_votada=result[3];
    	 //System.out.println("BI DO VOTO: "+umvoto.BI+" Mesa DO VOTO: "+umvoto.Mesa.local.Nome+" LISTA VOTADA: " +umvoto.lista_votada);
    	 return umvoto;
    	 
     }
	
	
	return null;
	
}

public synchronized static int inicia()  {
	int i=0,x=-1,mesa =-1;
	EleicaoInfo aux2;
	
	CopyOnWriteArrayList<EleicaoInfo> aux= new CopyOnWriteArrayList<EleicaoInfo>();
	try {
		aux = h.RetornaEleicoesacorrer();
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		
		Connectionrmi();
	}
	if(aux.isEmpty()==false) {
	System.out.println("Escolha uma eleição: "+aux.size()+" - para sair");

	while(i<aux.size()) {
		aux2 = aux.get(i);
		System.out.println(i+" - "+aux2.Titulo);
		i++;
	}
		x=IntScanner(0,(aux.size()));
				if(x==aux.size()) {
					return 0;
				}
		
		elect = aux.get(x);
		if(elect.Mesas.isEmpty()) {
			System.out.println("Esta eleição não tem mesas disponiveis");
			return 0;
		}
		System.out.println("Selecione uma mesa de voto: ");
		
		for(int y = 0; y<elect.Mesas.size();y++) {
			//IF DA MESA FALSA
			auxmesa=elect.Mesas.get(y);
			System.out.println(y+" - "+auxmesa.local.Nome);
			//Deixa escolher a mesa usando int 
		}
		mesa=IntScanner(0,(elect.Mesas.size()-1));
		//guarda o index
		elect.Mesas.get(mesa).estado=true;
		mesaindex=mesa;
		auxmesa=elect.Mesas.get(mesa);
		nterminais = elect.Mesas.get(mesa).numero_terminais;
		//h.update2
		System.out.println("escolheu a eleição: "+elect.Titulo + " Mesa: "+elect.Mesas.get(mesa).local.Nome);
		try {
			//System.out.println("vou fazer o Mesa updater");
			h.Mesa_Updater(elect,auxmesa);
			//System.out.println("esta feito");
			return 1;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			
			Connectionrmi();
		}
		return 1;
		
	}
	else {
		System.out.println("Não há eleições a decorrer");
	}
	return 0;
	
	
}

public static void main(String args[]){
	
		UserInfo user= null;
		boolean ver = false;
		int esta = 0;
		Connectionrmi();
		
		esta=inicia();
		while(esta==0) {
			System.out.println("retrying... press enter to continue");
			Scanner sc= new Scanner(System.in);
			sc.nextLine();
			esta=inicia();
		}
		TCPServer pouter = new TCPServer();
		pouter.new Criapool().start();
		//criapool();
		//System.out.println("antes da pool e a data é"+elect.DataF);

		//System.out.println("depois da pool");
		try {
			while(ver==false) {
				MenuPrincipal(user);
				//System.out.println("entrei1 e a data é"+elect.DataF);
				if( h.getdate().after(elect.DataF)) {
					System.out.println("A eleição acabou");
					
					elect.Mesas.get(mesaindex).estado=false;
					
					h.Mesa_Updater(elect,auxmesa);
					
					inicia();
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			
			Connectionrmi();
		}
		
		
		
				
	}
	
public void reconnect(ThreadPoolExecutor executor,int numero,ServerSocket listenSocket) {

	 
	System.out.println("Start Client n"+(numero+1));
	Socket clientSocket = null;
	try {
		clientSocket = listenSocket.accept();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		
	} // BLOQUEANTE
	//System.out.println("CLIENT_SOCKET (created at accept())="+clientSocket);
	TCPServer outer = new TCPServer();
	Conn conexao = outer.new Conn(clientSocket,numero);
	//System.out.println("Antes do executor");
	executor.execute(conexao);
	//System.out.println("Executei as threads");
}

public class Criapool extends Thread {
	int numero=0;
	String recover="gofirst";
	public Criapool() {
		
	}
	
	public synchronized void run() {
	try{
        int serverPort = 6000;
        //System.out.println("A Escuta no Porto");
        ServerSocket listenSocket = new ServerSocket(serverPort);
        //System.out.println("LISTEN SOCKET="+listenSocket);
        
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        
        while(numero<nterminais) {
        	System.out.println("Start Client n"+(numero+1));
        	Socket clientSocket = listenSocket.accept(); // BLOQUEANTE
        	//System.out.println("CLIENT_SOCKET (created at accept())="+clientSocket);
        	TCPServer outer = new TCPServer();
        	Conn conexao = outer.new Conn(clientSocket,numero);
        	//System.out.println("Antes do executor");
        	executor.execute(conexao);
        	//System.out.println("Executei as threads");
        	numero ++;
        }
     
		try {
			while(true) {
				while(recupera.isEmpty()) {
					
				}
			recover = recupera.take();
			String[] result = null;
			result = recover.split(";");
			System.out.println("AQUELE PRINT - "+result[1]);
			numero=Integer.parseInt(result[1]);
			reconnect(executor,numero,listenSocket);
			}
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		
			
		}
	}catch(IOException e)
	{System.out.println("Listen:" + e.getMessage());
	
	}
	}
}          
	
public class Conn implements Runnable {
		  
	    private int thread_number;
	    Socket clientSocket;
	    BufferedReader in;
	    BufferedWriter out;
	    String resposta;
	    String[] result;
	    String data;
	    UserInfo aux;
	    
	    public Conn (Socket aClientSocket, int numero) {
	        thread_number = numero;
	        try{
	            clientSocket = aClientSocket;
	            in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));
	            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
	        }catch(IOException e){System.out.println("Connection:" + e.getMessage());}
	    }
		@Override
		public synchronized void run() {
			 //System.out.println("sou a thread"+thread_number);
			try {
				out.write("terminal - "+(thread_number+1));
				out.newLine();
                out.flush();
                //System.out.println("escrevi para o cliente");
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
			
		            while(true){
		          
		                //an echo server
		            	//Blocked queue
		                //String data = in.readLine();
		                
		                //String[] result = data.split(";|\\|");
		            	
		       
		                try {
		                	while(queue.isEmpty()==true) {
		                		out.write("block");
		                		out.newLine();
			                	out.flush();
		                		wait(10);
		                	}
		                	synchronized (lock) {
								aux = queue.take();
							}
		                	System.out.println("Vota no terminal - "+(thread_number+1));
		                	out.write("unlock");
		                	out.newLine();
		                	out.flush();
		                	
							data = in.readLine();
							
							
							checktype(data,aux,in,out);
						} catch (IOException | InterruptedException e1) {
							// TODO Auto-generated catch block
							recupera.add("foiabaixo;"+thread_number);
							return;
							
						}
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
	
}


