package RMI;
import java.io.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

import Consola.ConRemoto;

import java.net.*;
import java.net.UnknownHostException;
import java.util.Properties;

public class remoteImpl extends UnicastRemoteObject implements RmiRemoto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static String portNum, registryURL;
	static int RMIPortNum;
	static String addr;
	static int udpport = 10000;
	// Threads para mandar e receber
	// pings-------------------------------------------------------------------------------

	public class PingReciver extends Thread {

		DatagramSocket socket;
		int port;

		public PingReciver() {
			try {
				port = udpport;
				socket = new DatagramSocket(port);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				System.out.println("Falha ao abrir socket" + e);
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
				try {
					socket.receive(request);
					InetAddress clientHost = request.getAddress();
					int clientPort = request.getPort();
					byte[] buf = request.getData();
					DatagramPacket reply = new DatagramPacket(buf, buf.length, clientHost, clientPort);
					socket.send(reply);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	public static void deletfi(String choose) {
		File file = new File(choose);
		ObjectOutputStream out = null;
		if (file.exists()) {
			try {
				file.delete();
				file.createNewFile();
				out = new ObjectOutputStream(new FileOutputStream(choose));
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void ReadFile_users() {
		UserInfo user;
		ObjectInputStream inp = null;
		ObjectOutputStream out = null;
		FileInputStream aux;
		File file = new File("Users.dat");
		try {
			if (!file.exists()) {
				file.createNewFile();
				out = new ObjectOutputStream(new FileOutputStream("Users.dat"));
				System.out.println("O ficheiro Users.dat nao existe");
				out.close();
				return;
			}
			aux = new FileInputStream("Users.dat");
			inp = new ObjectInputStream(aux);
			while (aux.available() > 0) {
				user = (UserInfo) inp.readObject();
				if (user == null) {
					System.out.println("O ficheiro Users.dat esta vazio");
					inp.close();
				}
				if (user != null)
					Lista_de_users.add(user);
			}
			inp.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			return;
		}
	}

	public static void ReadFile_userslogged() {
		UserInfo user;
		ObjectInputStream inp = null;
		ObjectOutputStream out = null;
		FileInputStream aux;
		File file6 = new File("UsersLogged.dat");
		try {
			if (!file6.exists()) {
				file6.createNewFile();
				out = new ObjectOutputStream(new FileOutputStream("UsersLogged.dat"));
				System.out.println("O ficheiro UsersLogged.dat nao existe");
				out.close();
				return;
			}
			aux = new FileInputStream("UsersLogged.dat");
			inp = new ObjectInputStream(aux);
			while (aux.available() > 0) {
				user = (UserInfo) inp.readObject();
				if (user == null) {
					System.out.println("O ficheiro UsersLogged.dat esta vazio");
					inp.close();
				}
				if (user != null)
					Lista_de_logged_users.add(user);
			}
			inp.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			return;
		}
	}

	public static void ReadFile_eleicoesagendadas() {
		EleicaoInfo elei = null;
		ObjectInputStream inp = null;
		ObjectOutputStream out = null;
		FileInputStream aux;
		File file6 = new File("EleicoesAgendadas.dat");
		try {
			if (!file6.exists()) {
				file6.createNewFile();
				out = new ObjectOutputStream(new FileOutputStream("EleicoesAgendadas.dat"));
				System.out.println("O ficheiro EleicoesAgendadas.dat nao existe");
				out.close();
				return;
			}
			aux = new FileInputStream("EleicoesAgendadas.dat");

			while (aux.available() > 0 && (inp = new ObjectInputStream(aux)) != null) {
				elei = (EleicaoInfo) inp.readObject();
				if (elei == null) {
					System.out.println("O ficheiro EleicoesAgendadas.dat esta vazio");
					inp.close();
				}
				if (elei != null)
					Lista_de_eleicoes_nao_iniciadas.add(elei);
			}
			inp.close();
		} catch (EOFException e) {
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			return;
		}
	}

	public static void ReadFile_eleicoesacabadas() {
		EleicaoInfo elei;
		ObjectInputStream inp = null;
		ObjectOutputStream out = null;
		FileInputStream aux;
		File file6 = new File("EleicoesAcabadas.dat");
		try {
			if (!file6.exists()) {
				file6.createNewFile();
				out = new ObjectOutputStream(new FileOutputStream("EleicoesAcabadas.dat"));
				System.out.println("O ficheiro EleicoesAcabadas.dat nao existe");
				out.close();
				return;
			}
			aux = new FileInputStream("EleicoesAcabadas.dat");
			inp = new ObjectInputStream(aux);
			while (aux.available() > 0) {
				elei = (EleicaoInfo) inp.readObject();
				if (elei == null) {
					System.out.println("O ficheiro EleicoesAcabadas.dat esta vazio");
					inp.close();
				}
				if (elei != null)
					Lista_de_eleicoes_acabadas.add(elei);
			}
			inp.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			return;
		}
	}

	public static void ReadFile_eleicoesdecorrer() {
		EleicaoInfo elei;
		ObjectInputStream inp = null;
		ObjectOutputStream out = null;
		FileInputStream aux;
		File file6 = new File("EleicoesDecorrer.dat");
		try {
			if (!file6.exists()) {
				file6.createNewFile();
				out = new ObjectOutputStream(new FileOutputStream("EleicoesDecorrer.dat"));
				System.out.println("O ficheiro EleicoesDecorrer.dat nao existe");
				out.close();
				return;
			}
			aux = new FileInputStream("EleicoesDecorrer.dat");
			while (aux.available() > 0 && (inp = new ObjectInputStream(aux)) != null) {
				elei = (EleicaoInfo) inp.readObject();
				if (elei == null) {
					System.out.println("O ficheiro EleicoesDecorrer.dat esta vazio");
					inp.close();
				}
				if (elei != null)
					Lista_de_eleicoes_acorrer.add(elei);
			}
			inp.close();
		} catch (EOFException e) {
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			return;
		}
	}

	public static void ReadFile_depfac() {
		DepFacInfo dfi;
		ObjectInputStream inp = null;
		ObjectOutputStream out = null;
		FileInputStream aux;
		File file6 = new File("DepFac.dat");
		try {
			if (!file6.exists()) {
				file6.createNewFile();
				out = new ObjectOutputStream(new FileOutputStream("DepFac.dat"));
				System.out.println("O ficheiro DepFac.dat nao existe");
				out.close();
				return;
			}
			aux = new FileInputStream("DepFac.dat");
			inp = new ObjectInputStream(aux);
			while (aux.available() > 0) {
				dfi = (DepFacInfo) inp.readObject();
				if (dfi == null) {
					System.out.println("O ficheiro DepFac.dat esta vazio");
					inp.close();
				}
				if (dfi != null)
					Lista_de_depfac.add(dfi);
			}
			inp.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void update_files(int choose) throws IOException {
		switch (choose) {
		case 1:
			if (Lista_de_users.isEmpty() == false) {
				ObjectOutputStream out;
				File file = new File("Users.dat");
				if (!file.exists()) {
					file.createNewFile();
					out = new ObjectOutputStream(new FileOutputStream("Users.dat"));
				} else
					out = new ObjectOutputStream(new FileOutputStream("Users.dat"));
				for (int i = 0; i < Lista_de_users.size(); i++) {
					out.writeObject(Lista_de_users.get(i));
				}
				out.close();
			}
			break;
		case 2:
			if (Lista_de_logged_users.isEmpty() == false) {
				ObjectOutputStream out;
				File file = new File("UsersLogged.dat");
				if (!file.exists()) {
					file.createNewFile();
					out = new ObjectOutputStream(new FileOutputStream("UsersLogged.dat"));
				} else
					out = new ObjectOutputStream(new FileOutputStream("UsersLogged.dat"));
				for (int i = 0; i < Lista_de_logged_users.size(); i++) {
					out.writeObject(Lista_de_logged_users.get(i));
				}
				out.close();
			}
			break;
		case 3:
			if (Lista_de_eleicoes_nao_iniciadas.isEmpty() == false) {
				ObjectOutputStream out;
				File file = new File("EleicoesAgendadas.dat");
				if (!file.exists()) {
					file.createNewFile();
					out = new ObjectOutputStream(new FileOutputStream("EleicoesAgendadas.dat"));
				} else
					out = new ObjectOutputStream(new FileOutputStream("EleicoesAgendadas.dat"));
				for (int i = 0; i < Lista_de_eleicoes_nao_iniciadas.size(); i++) {
					out.writeObject(Lista_de_eleicoes_nao_iniciadas.get(i));
				}
				out.close();
			}
			break;
		case 4:
			if (Lista_de_eleicoes_acorrer.isEmpty() == false) {
				ObjectOutputStream out;
				File file = new File("EleicoesDecorrer.dat");
				if (!file.exists()) {
					file.createNewFile();
					out = new ObjectOutputStream(new FileOutputStream("EleicoesDecorrer.dat"));
				} else
					out = new ObjectOutputStream(new FileOutputStream("EleicoesDecorrer.dat"));
				for (int i = 0; i < Lista_de_eleicoes_acorrer.size(); i++) {
					out.writeObject(Lista_de_eleicoes_acorrer.get(i));
				}
				out.close();
			}
			break;
		case 5:
			if (Lista_de_eleicoes_acabadas.isEmpty() == false) {
				ObjectOutputStream out;
				File file = new File("EleicoesAcabadas.dat");
				if (!file.exists()) {
					file.createNewFile();
					out = new ObjectOutputStream(new FileOutputStream("EleicoesAcabadas.dat"));
				} else
					out = new ObjectOutputStream(new FileOutputStream("EleicoesAcabadas.dat"));
				for (int i = 0; i < Lista_de_eleicoes_acabadas.size(); i++) {
					out.writeObject(Lista_de_eleicoes_acabadas.get(i));
				}
				out.close();
			}
			break;
		case 6:
			if (Lista_de_depfac.isEmpty() == false) {
				ObjectOutputStream out;
				File file = new File("DepFac.dat");
				if (!file.exists()) {
					file.createNewFile();
					out = new ObjectOutputStream(new FileOutputStream("DepFac.dat"));
				} else
					out = new ObjectOutputStream(new FileOutputStream("DepFac.dat"));
				for (int i = 0; i < Lista_de_depfac.size(); i++) {
					out.writeObject(Lista_de_depfac.get(i));
				}
				out.close();
			}
			break;
		default:
			System.out.println("Opcao invalida");
			break;
		}
	}

	public class PingSender extends Thread {

		String ServerName;
		int port;
		DatagramSocket socket;
		InetAddress IPAddress;
		int counter = 0;

		public PingSender() {
			ServerName = addr;
			port = udpport;
			try {
				socket = new DatagramSocket();
				System.out.println("Criei o socket");
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				System.out.println("Erro ao criar socket" + e);
			}
			try {
				IPAddress = InetAddress.getByName(ServerName);
				System.out.println("Encontrei addr");
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				System.out.println("Erro ao tentar encontrar addr" + e);
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			int i = 0;
			while (true) {
				long SendTime = System.currentTimeMillis();
				String Message = "Ping " + i + " " + SendTime + "\n";
				i++;
				DatagramPacket request = new DatagramPacket(Message.getBytes(), Message.length(), IPAddress, port);
				try {
					socket.send(request);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				DatagramPacket reply = new DatagramPacket(new byte[1024], 1024);

				try {
					socket.setSoTimeout(1000);
				} catch (SocketException e1) {
					// TODO Auto-generated catch block
					System.out.println("IoException no socket.setSoTimeout" + e1);
				}

				try {
					socket.receive(reply);
				} catch (IOException E) {
					counter++;
					System.out.println(counter);
					if (counter == 5) {
						remoteImpl outer = null;
						try {
							outer = new remoteImpl();
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						outer.RmiBackupStarter();
						Thread.currentThread().interrupt();
						return;
					}
				}
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					System.out.println("Erro no sleep" + e);
				}
			}
		}

	}

	// --------------------------------------------------------------------------------------------

	protected remoteImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	// Main------------------------------------------------------------

	public static void main(String args[]) {
		if (PropLoader())
			try {
				registryURL = "rmi://" + addr + ":" + portNum + "/callback";
				System.out.println(registryURL);
				Registry registry = LocateRegistry.createRegistry(RMIPortNum);
				remoteImpl exportedObj = new remoteImpl();
				Naming.rebind(registryURL, exportedObj);
				ReadFile_users();
				ReadFile_userslogged();
				ReadFile_eleicoesagendadas();
				ReadFile_eleicoesacabadas();
				ReadFile_eleicoesdecorrer();
				ReadFile_depfac();
				System.out.println(Lista_de_users.size());
				System.out.println(Lista_de_logged_users.size());
				System.out.println(Lista_de_eleicoes_nao_iniciadas.size());
				System.out.println(Lista_de_eleicoes_acorrer.size());
				System.out.println(Lista_de_eleicoes_acabadas.size());
				new Thread() {
					public void run() {
						System.out.println("Inicou-se um EleicaoManager");
						EleicaoManager();
					}
				}.start();
				remoteImpl outer = new remoteImpl();
				PingReciver recive = outer.new PingReciver();
				recive.start();
				System.out.println("Server ready.");
			} catch (ConnectIOException e) {
				System.out.println("ConnectionIOException tente usar localhost" + e);
			} catch (Exception e) {
				try {
					remoteImpl outer = new remoteImpl();
					PingSender send = outer.new PingSender();
					send.start();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
				}
			}
	}

	// -------------------------------------------------------------------------------------------------------------------
	public void RmiBackupStarter() {
		try {
			registryURL = "rmi://" + addr + ":" + portNum + "/callback";
			Registry registry = LocateRegistry.createRegistry(RMIPortNum);
			remoteImpl exportedObj = new remoteImpl();
			System.out.println(registryURL);
			Naming.rebind(registryURL, exportedObj);
			ReadFile_users();
			ReadFile_userslogged();
			ReadFile_eleicoesagendadas();
			ReadFile_eleicoesacabadas();
			ReadFile_eleicoesdecorrer();
			ReadFile_depfac();
			new Thread() {
				public void run() {
					System.out.println("Inicou-se um EleicaoManager");
					EleicaoManager();
				}
			}.start();
			System.out.println("Backup Server ready.");
			remoteImpl outer = new remoteImpl();
			PingReciver recive = outer.new PingReciver();
			recive.start();
		} catch (ConnectIOException e) {
			System.out.println("ConnectionIOException tente usar localhost" + e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Funcao para Manage das eleicoes
	// -------------------------------------------------------------------------------------------------------
	public static void EleicaoManager() {
		int aux=0;
		while (true) {
			CopyOnWriteArrayList<EleicaoInfo> auxi = Lista_de_eleicoes_nao_iniciadas;
			CopyOnWriteArrayList<EleicaoInfo> auxc = Lista_de_eleicoes_acorrer;
			for (int i = 0; i < auxi.size(); i++) {
				if (new Date().equals(auxi.get(i).DataI) || new Date().after(auxi.get(i).DataI)) {
					Lista_de_eleicoes_acorrer.add(Lista_de_eleicoes_nao_iniciadas.get(i));
					System.out.println(
							"Foi metida a correr a eleicao com titulo:" + Lista_de_eleicoes_nao_iniciadas.get(i).Titulo
									+ " Notificando a consola e os Servidores tcp");

					for (int y = 0; y < ConnectedList.size(); y++) {
						try {
							((ConRemoto) ConnectedList.get(y))
									.Notify_InicioEleicao(Lista_de_eleicoes_nao_iniciadas.get(i));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							System.out.println(
									"Ocorreu um erro a enviar callback, cliente provavelmente desligado, vou remover o cliente da lista de callbacks");
							ConnectedList.remove(y);
						}
					}
					Lista_de_eleicoes_nao_iniciadas.remove(i);
					aux=1;
				}
			}
			if(aux==1) {
				try {
					deletfi("EleicoesAgendadas.dat");
					update_files(3);
					update_files(4);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			aux=0;
			for (int i = 0; i < auxc.size(); i++) {
				if (new Date().equals(auxc.get(i).DataF) || new Date().after(auxc.get(i).DataF)) {
					Lista_de_eleicoes_acabadas.add(Lista_de_eleicoes_acorrer.get(i));
					System.out.println("Acabou a  eleicao com titulo:" + Lista_de_eleicoes_acorrer.get(i).Titulo
							+ " Notificando a consola e os Servidores tcp");

					for (int y = 0; y < ConnectedList.size(); y++) {
						try {
							((ConRemoto) ConnectedList.get(y)).Notify_FimEleicao(Lista_de_eleicoes_acorrer.get(i));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							System.out.println(
									"Ocorreu um erro a enviar callback, cliente provavelmente desligado, vou remover o cliente da lista de callbacks");
							ConnectedList.remove(y);
						}
					}

					Lista_de_eleicoes_acorrer.remove(i);
					aux=1;
				}
			}
			if(aux==1) {
				try {
					deletfi("EleicoesDecorrer.dat");
					update_files(4);
					update_files(5);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// Registar e desregistar as consolas para
	// callback---------------------------------------------------------------------------------
	@Override
	public synchronized void registerForCallback(ConRemoto callbackClientObject) throws RemoteException {// Adiciona a
																											// consola
																											// para o
																											// Vector
																											// para
																											// receber o
																											// callback
		if (!(ConnectedList.contains(callbackClientObject))) {
			ConnectedList.add(callbackClientObject);
			System.out.println("Registered new client ");
		}
	}

	@Override
	public synchronized void unregisterForCallback(ConRemoto callbackClientObject) throws RemoteException {
		if (ConnectedList.remove(callbackClientObject))
			System.out.println("Unregistered client ");
		else
			System.out.println("unregister: clientwasn't registered.");
	}

	// --------------------------------------------------------------------------------
	public static boolean PropLoader() {
		Properties prop = new Properties();
		InputStream input = null;
		try {

			input = new FileInputStream("config.properties");
			prop.load(input);
			addr = prop.getProperty("endereco");
			portNum = prop.getProperty("porta");
			RMIPortNum = Integer.parseInt(portNum);
		} catch (IOException ex) {
			System.out.println("Ficheiro config.properties em falta");
			return false;
		} finally {
			if (input != null) {
				try {
					input.close();
					return true;
				} catch (IOException e) {
					System.out.println("Ocorreu um IoException no PropertiesLoader ao fechar o ficheiro");
					return false;
				}
			}
		}
		return true;
	}

	// Metodos remotods paras as funcoes 1-5
	// 9-18---------------------------------------------------------------------

	@Override
	public synchronized boolean CriarUser(UserInfo user) {
		CopyOnWriteArrayList<UserInfo> aux = Lista_de_users;
		for (UserInfo u : aux) {
			if (u.BI == user.BI)
				return false;
		}
		Lista_de_users.add(user);
		try {
			update_files(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printerListaUser();

		return true;

	}

	@Override
	public synchronized String AdicionarDepFac(DepFacInfo dep) throws RemoteException {
		// TODO Auto-generated method stub
		Lista_de_depfac.add(dep);
		try {
			update_files(6);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printerListafacdep();// Comentar antes da entrega

		return "Faculdade adicionada";
	}

	@Override
	public synchronized String CriarEleicao(EleicaoInfo eleicao) throws RemoteException {
		Lista_de_eleicoes_nao_iniciadas.add(eleicao);
		// TODO Auto-generated method stub
		try {
			update_files(3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printerlistaeleicoes();// Comentar antes da entrega

		return "Eleicao criada";
	}

	@Override
	public synchronized int AdicionarListaCand(ListasCandidatas lista, EleicaoInfo eleicao, int tip_lista)
			throws RemoteException {
		// TODO Auto-generated method stub
		for (ListasCandidatas e : eleicao.Listas_CandidatasE)
			if (e.nome.equals(lista.nome))
				return 0;
		for (ListasCandidatas d : eleicao.Listas_CandidatasD)
			if (d.nome.equals(lista.nome))
				return 0;
		for (ListasCandidatas f : eleicao.Listas_CandidatasF)
			if (f.nome.equals(lista.nome))
				return 0;
		if (Lista_de_eleicoes_nao_iniciadas.contains(eleicao)) {
			int ind = Lista_de_eleicoes_nao_iniciadas.indexOf(eleicao);
			if (tip_lista == 1)
				Lista_de_eleicoes_nao_iniciadas.get(ind).Listas_CandidatasE.add(lista);
			else if (tip_lista == 2)
				Lista_de_eleicoes_nao_iniciadas.get(ind).Listas_CandidatasD.add(lista);
			else if (tip_lista == 3)
				Lista_de_eleicoes_nao_iniciadas.get(ind).Listas_CandidatasF.add(lista);
			try {
				update_files(3);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			printerlistaeleicoes();// Comentar antes da entrega

			return 1;
		} else
			return -1;
	}

	@Override
	public synchronized String RemoverListaCand(EleicaoInfo eleicao, String nomelistarem) throws RemoteException {
		// TODO Auto-generated method stub
		if (Lista_de_eleicoes_nao_iniciadas.contains(eleicao)) {
			int ind = Lista_de_eleicoes_nao_iniciadas.indexOf(eleicao);
			for (int i = 0; i < eleicao.Listas_CandidatasE.size(); i++) {
				if (eleicao.Listas_CandidatasE.get(i).nome.equals(nomelistarem)) {
					eleicao.Listas_CandidatasE.remove(i);
					try {
						Lista_de_eleicoes_nao_iniciadas.set(ind, eleicao);
					} catch (NullPointerException e) {
						System.out.println("Array alterado nao se encontra no mesmo sitio");
						return "A eleicao escolhida ja comecou ou foi alterada por outra consola tente de novo";
					}
					printerlistaeleicoes();// Comentar antes da entrega;
					try {
						update_files(3);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return "Lista removida";
				}
			}
			for (int i = 0; i < eleicao.Listas_CandidatasF.size(); i++) {
				if (eleicao.Listas_CandidatasF.get(i).nome.equals(nomelistarem)) {
					eleicao.Listas_CandidatasF.remove(i);
					try {
						Lista_de_eleicoes_nao_iniciadas.set(ind, eleicao);
					} catch (NullPointerException e) {
						System.out.println("Array alterado nao se encontra no mesmo sitio");
						return "A eleicao escolhida ja comecou ou foi alterada por outra consola tente de novo";
					}

					printerlistaeleicoes();// Comentar antes da entrega;
					try {
						update_files(3);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return "Lista removida";
				}
			}
			for (int i = 0; i < eleicao.Listas_CandidatasD.size(); i++) {
				if (eleicao.Listas_CandidatasD.get(i).nome.equals(nomelistarem)) {
					eleicao.Listas_CandidatasD.remove(i);
					try {
						Lista_de_eleicoes_nao_iniciadas.set(ind, eleicao);
					} catch (NullPointerException e) {
						System.out.println("Array alterado nao se encontra no mesmo sitio");
						return "A eleicao escolhida ja comecou ou foi alterada por outra consola tente de novo";
					}

					printerlistaeleicoes();// Comentar antes da entrega;
					try {
						update_files(3);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return "Lista removida";
				}
			}
			return "Lista com o nome inserido nao existe";
		}
		return "A eleicao escolhida ja comecou ou foi alterada por outra consola tente de novo";
	}

	@Override
	public synchronized CopyOnWriteArrayList<DepFacInfo> RetornaDepFac() throws RemoteException {
		// TODO Auto-generated method stub
		return Lista_de_depfac;
	}

	@Override
	public synchronized CopyOnWriteArrayList<EleicaoInfo> RetornaEleicoesConsola() throws RemoteException {
		// TODO Auto-generated method stub
		return Lista_de_eleicoes_nao_iniciadas;
	}

	@Override
	public synchronized CopyOnWriteArrayList<UserInfo> RetornaPossiveisCandidatos(EleicaoInfo eleicao,
			int tipo_de_lista) throws RemoteException {
		// TODO Auto-generated method stub
		CopyOnWriteArrayList<UserInfo> possiveis = new CopyOnWriteArrayList<UserInfo>();
		CopyOnWriteArrayList<UserInfo> todos_users = Lista_de_users;
		if (eleicao.Tipo == 2) {
			if (tipo_de_lista == 1) {
				for (UserInfo e : todos_users) {
					if (eleicao.Listas_CandidatasE.isEmpty())
						if (e.Tipo == tipo_de_lista)
							possiveis.add(e);
						else {
							for (ListasCandidatas l : eleicao.Listas_CandidatasE) {
								if (!l.Lista.contains(e) && e.Tipo == tipo_de_lista)
									possiveis.add(e);
							}
						}
				}
			}
			if (tipo_de_lista == 2) {
				for (UserInfo e : todos_users) {
					if (eleicao.Listas_CandidatasD.isEmpty())
						if (e.Tipo == tipo_de_lista)
							possiveis.add(e);
						else {
							for (ListasCandidatas l : eleicao.Listas_CandidatasD) {
								if (!l.Lista.contains(e) && e.Tipo == tipo_de_lista)
									possiveis.add(e);
							}
						}
				}
			}
			if (tipo_de_lista == 3) {
				for (UserInfo e : todos_users) {
					if (eleicao.Listas_CandidatasF.isEmpty())
						if (e.Tipo == tipo_de_lista)
							possiveis.add(e);
						else {
							for (ListasCandidatas l : eleicao.Listas_CandidatasF) {
								if (!l.Lista.contains(e) && e.Tipo == tipo_de_lista)
									possiveis.add(e);
							}
						}
				}
			}
		} else if (eleicao.Tipo == 1) {
			for (UserInfo e : todos_users) {
				if (eleicao.Listas_CandidatasE.isEmpty()) {
					if (e.Tipo == tipo_de_lista && eleicao.DepFac.equals(e.DepFac))
						possiveis.add(e);
				} else {
					for (ListasCandidatas l : eleicao.Listas_CandidatasE) {
						if (!l.Lista.contains(e) && e.Tipo == tipo_de_lista && eleicao.DepFac.equals(e.DepFac))
							possiveis.add(e);
					}
				}
			}
		} else if (eleicao.Tipo == 3) {
			for (UserInfo e : todos_users) {
				if (eleicao.Listas_CandidatasD.isEmpty())
					if (e.Tipo == tipo_de_lista && eleicao.DepFac.equals(e.DepFac))
						possiveis.add(e);
					else {
						for (ListasCandidatas l : eleicao.Listas_CandidatasD) {
							if (!l.Lista.contains(e) && e.Tipo == tipo_de_lista && eleicao.DepFac.equals(e.DepFac))
								possiveis.add(e);
						}
					}
			}
		} else if (eleicao.Tipo == 4) {
			for (UserInfo e : todos_users) {
				if (eleicao.Listas_CandidatasD.isEmpty())
					if (e.Tipo == tipo_de_lista && eleicao.DepFac.Departamentos.contains(e.DepFac))
						possiveis.add(e);
					else {
						for (ListasCandidatas l : eleicao.Listas_CandidatasD) {
							if (!l.Lista.contains(e) && e.Tipo == tipo_de_lista
									&& eleicao.DepFac.Departamentos.contains(e.DepFac))
								possiveis.add(e);
						}
					}
			}
		}
		return possiveis;
	}

	@Override
	public synchronized CopyOnWriteArrayList<EleicaoInfo> RetornaEleicoesNaoIniciadas(double bi)
			throws RemoteException {
		// TODO Auto-generated method stub
		CopyOnWriteArrayList<UserInfo> auxu = Lista_de_users;
		CopyOnWriteArrayList<EleicaoInfo> auxe = Lista_de_eleicoes_nao_iniciadas;
		CopyOnWriteArrayList<EleicaoInfo> lista_final = new CopyOnWriteArrayList<EleicaoInfo>();
		UserInfo user = null;
		for (UserInfo u : auxu) {
			if (u.BI == bi)
				user = u;
		}
		for (EleicaoInfo e : auxe) {
			if (e.Tipo == 2)
				lista_final.add(e);

			else if (e.Tipo == 1) {
				if (user.Tipo == 1 && user.DepFac.equals(e.DepFac))
					lista_final.add(e);
			} else if (e.Tipo == 3) {
				if (user.Tipo == 3 && user.DepFac.equals(e.DepFac))
					lista_final.add(e);
			} else if (e.Tipo == 4) {
				if (user.Tipo == 3 && e.DepFac.Departamentos.contains(user.DepFac))
					lista_final.add(e);
			}
		}
		return lista_final;

	}

	@Override
	public synchronized String VotoAntecipado(Voto v, EleicaoInfo eleicao) throws RemoteException {
		// TODO Auto-generated method stub
		int ind = -1;
		if (Lista_de_eleicoes_nao_iniciadas.contains(eleicao)) {
			ind = Lista_de_eleicoes_nao_iniciadas.indexOf(eleicao);
			CopyOnWriteArrayList<Voto> todos_votos = eleicao.Votos_Eleicao;
			boolean test = false;
			for (Voto x : todos_votos) {
				if (x.BI == v.BI)
					test = true;
			}
			if (!test) {
				v.Data = new Date();
				Lista_de_eleicoes_nao_iniciadas.get(ind).Votos_Eleicao.add(v);
				try {
					update_files(3);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (int i = 0; i < ConnectedList.size(); i++) {
					try {
						((ConRemoto) ConnectedList.get(i)).Notify_Voto(eleicao);
					} catch (Exception e) {
						System.out.println(
								"Ocorreu um erro a enviar callback cliente provavelmente desligado, vou remover o cliente da lista de callbacks");
						ConnectedList.remove(i);
					}
				}

				CopyOnWriteArrayList<UserInfo> aux = Lista_de_logged_users;
				for (int i = 0; i < aux.size(); i++) {
					if (aux.get(i).BI == v.BI)
						Lista_de_logged_users.remove(i);
				}
				try {
					deletfi("UsersLogged.dat");
					update_files(2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "Voto bem sucedido";
			} else
				return "Ja votou nesta eleicao";
		}
		return "A eleicao ja nao esta disponivel para voto antecipado";
	}

	@Override
	public synchronized UserInfo AutenticarEleitor(double BI, String pass) throws RemoteException {
		// TODO Auto-generated method stub
		CopyOnWriteArrayList<UserInfo> aux = Lista_de_users;
		for (UserInfo u : aux) {
			if (u.BI == BI && u.Password.equals(pass)) {
				try {
					update_files(2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return u;
			}
		}
		return null;
	}

	@Override
	public synchronized UserInfo RetornaUser(double BI) throws RemoteException {
		// TODO Auto-generated method stub
		CopyOnWriteArrayList<UserInfo> aux = Lista_de_users;
		for (UserInfo u : aux)
			if (u.BI == BI)
				return u;
		return null;
	}

	@Override
	public synchronized String AlteraUserInfo(UserInfo novo, UserInfo velho) throws RemoteException {
		// TODO Auto-generated method stub
		if (Lista_de_users.contains(velho)) {
			int ind = Lista_de_users.indexOf(velho);
			try {
				Lista_de_users.set(ind, novo);
			} catch (NullPointerException e) {
				System.out.println("Array alterado nao se encontra no mesmo sitio");
				return "User nao encontrado ao alterar os dados";
			}
			printerListaUser();// Comentar antes da entrega;
			try {
				update_files(1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "UserAlterado";
		}
		return "User nao encontrado ao alterar os dados";
	}

	@Override
	public synchronized String UpdateEleicao(EleicaoInfo novo, EleicaoInfo velho) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println(novo.equals(velho));
		if (Lista_de_eleicoes_nao_iniciadas.contains(velho)) {
			int ind = Lista_de_eleicoes_nao_iniciadas.indexOf(velho);
			try {
				Lista_de_eleicoes_nao_iniciadas.set(ind, novo);
			} catch (NullPointerException e) {
				System.out.println("Array alterado nao se encontra no mesmo sitio");
				return "A eleicao escolhida ja comecou ou foi alterada por outra consola tente de novo";
			}
			printerlistaeleicoes();// Comentar antes da entrega
			try {
				update_files(3);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "Eleicao Actualizada com os novos dados";
		}
		return "Eleicao que tentou alterar ja foi alterada por outra consola ou ja comecou";
	}

	@Override
	public synchronized CopyOnWriteArrayList<UserInfo> RetornaTodosUsers() {
		// TODO Auto-generated method stub
		return Lista_de_users;
	}

	@Override
	public synchronized CopyOnWriteArrayList<UserInfo> RetornaUsersLogados() throws RemoteException {
		// TODO Auto-generated method stub
		return Lista_de_logged_users;
	}

	@Override
	public synchronized CopyOnWriteArrayList<EleicaoInfo> RetornaEleicoesAcabadas() throws RemoteException {
		// TODO Auto-generated method stub
		return Lista_de_eleicoes_acabadas;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Printers para testes

	public void printerListaUser() {
		for (UserInfo u : Lista_de_users)
			u.printerteste();
		System.out.println("/////////////////////////////////////");
	}

	public void printerListafacdep() {
		for (DepFacInfo u : Lista_de_depfac)
			u.printerteste();
		System.out.println("/////////////////////////////////////");
	}

	public void printerlistaeleicoes() {
		for (EleicaoInfo u : Lista_de_eleicoes_nao_iniciadas)
			u.printerteste();
		System.out.println("/////////////////////////////////////");
	}

	// FuncoesRemotas para SVTCP
	// -------------------------------------------------------------------------------------------------

	@Override
	public synchronized CopyOnWriteArrayList<EleicaoInfo> RetornaEleicoesacorrer() throws RemoteException {
		// TODO Auto-generated method stub
		return Lista_de_eleicoes_acorrer;
	}
	
	@Override
	public Date getdate() {
		Date agora = new Date();
		return agora;
	}

	@Override
	public synchronized boolean Logar(UserInfo user) throws RemoteException {
		// TODO Auto-generated method stub
		if (Lista_de_logged_users.contains(user))
			return true;
		else
			return false;
	}

	@Override
	public synchronized UserInfo Identificar(double bi, EleicaoInfo eleicao) throws RemoteException {
		// TODO Auto-generated method stub
		CopyOnWriteArrayList<UserInfo> aux = Lista_de_users;
		for (UserInfo u : aux) {
			if (u.BI == bi) {
				System.out.println(CheckaVotos(eleicao, u.BI));
				System.out.println(CheckaTipo(eleicao, u));
				if (CheckaTipo(eleicao, u) && CheckaVotos(eleicao, u.BI))
					return u;
			}
		}
		return null;
	}

	public static boolean CheckaTipo(EleicaoInfo eleicao, UserInfo user) {
		if (eleicao.Tipo == 1) {
			if (user.Tipo == 1 && user.DepFac.equals(eleicao.DepFac))
				return true;
		} else if (eleicao.Tipo == 3) {
			if (user.Tipo == 2 && user.DepFac.equals(eleicao.DepFac))
				return true;
		} else if (eleicao.Tipo == 4) {
			if (user.Tipo == 2) {
				CopyOnWriteArrayList<DepFacInfo> aux = Lista_de_depfac;
				for (DepFacInfo df : aux)
					if (df.Departamentos.contains(user.DepFac))
						return true;
			}
		} else if (eleicao.Tipo == 2)
			return true;
		return false;
	}

	public static boolean CheckaVotos(EleicaoInfo eleicao, double bi) {
		CopyOnWriteArrayList<EleicaoInfo> aux = Lista_de_eleicoes_acorrer;
		if (aux.contains(eleicao)) {
			EleicaoInfo auxe = aux.get(aux.indexOf(eleicao));
			for (Voto v : auxe.Votos_Eleicao)
				if (v.BI == bi)
					return false;
		} else
			return false;
		return true;

	}
	
	
	@Override
	public synchronized boolean Votar(Voto umvoto, EleicaoInfo eleicao) throws RemoteException {
		// TODO Auto-generated method stub
		umvoto.Data = new Date();
		if (Lista_de_eleicoes_acorrer.contains(eleicao)) {
			Lista_de_eleicoes_acorrer.get(Lista_de_eleicoes_acorrer.indexOf(eleicao)).Votos_Eleicao.add(umvoto);
			//System.out.println("NUMBARO "+Lista_de_eleicoes_acorrer.get(Lista_de_eleicoes_acorrer.indexOf(eleicao)).Votos_Eleicao.size());
			for (int i = 0; i < ConnectedList.size(); i++) {
				try {
					((ConRemoto) ConnectedList.get(i)).Notify_Voto(eleicao);
				} catch (Exception e) {
					System.out.println(
							"Ocorreu um erro a enviar callback cliente provavelmente desligado, vou remover o cliente da lista de callbacks");
					ConnectedList.remove(i);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public synchronized boolean Mesa_Updater(EleicaoInfo eleicao, Mesa_voto mesa) throws RemoteException {// Update a
																											// eleicao
																											// com a
																											// mesa que
																											// o tcp
																											// escolheu
																											// a true
		CopyOnWriteArrayList<EleicaoInfo> aux = Lista_de_eleicoes_acorrer;
		if (aux.contains(eleicao)) {
			Lista_de_eleicoes_acorrer.set(aux.indexOf(eleicao), eleicao);
			for (int y = 0; y < ConnectedList.size(); y++) {
				try {
					((ConRemoto) ConnectedList.get(y)).Notify_Estado_Mesa(mesa, eleicao);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(
							"Ocorreu um erro a enviar callback, cliente provavelmente desligado, vou remover o cliente da lista de callbacks");
					ConnectedList.remove(y);
				}
			}
			return true;
		}
		return false;

	}

	@Override
	public void HelloRMI() throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Someone wants to say hello");
	}

}
