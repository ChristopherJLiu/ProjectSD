package TCPclient;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

public class TCPClient {
	
	static Socket s = null;
	static int serversocket = 6000;
	static boolean logine = false;
	static String terminal;
   
	public static void main(String args[]) {
	// args[0] <- hostname of destination
	//if (args.length == 0) {
	    //System.out.println("java TCPClient hostname");
	    //System.exit(0);
	//}

	
	try { 
		//TCPClient cliente= new TCPClient();
	    // 1o passo
	    s = new Socket("localhost", serversocket);

	    //System.out.println("SOCKET=" + s);
	    // 2o passo
	   
	    //System.out.println("Introduza texto:");
	    init(s);


	} catch (UnknownHostException e) {
	    System.out.println("Sock:" + e.getMessage());
	} catch (EOFException e) {
	    System.out.println("EOF:" + e.getMessage());
	} catch (IOException e) {
	    System.out.println("IO:" + e.getMessage());
	} finally {
	    if (s != null)
		try {
		    s.close();
		} catch (IOException e) {
		    System.out.println("close:" + e.getMessage());
		}
	}
}
   
	public static void unlock(Socket s, BufferedReader in, BufferedWriter out) {
		String data;
		try {
			data=in.readLine();
			while(data.equalsIgnoreCase("block")){
			data= in.readLine();
			}
			if(data.equalsIgnoreCase("unlock")) {
				System.out.flush();
				System.out.println("Unlocked");
				System.out.println(terminal);
				
				logine=false;
				loguser(s,in,out);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	
	public static void init(Socket s) {
		BufferedWriter out = null;
	    BufferedReader in=null;
	    int tnumber=-1;
		 try {
	        	in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				out= new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		 
		 	//System.out.println("antes da data");
		 	String data = null;
			try {
				data = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}
			//System.out.println("depois de ler");
			terminal=data;
			System.out.println(data);
			
			
			unlock(s,in,out);
				

		  
			 
		 
	}
	
	
   public static void vota(Socket s,BufferedReader in, BufferedWriter out) {
	   
	    
	    int counter=0,i=0,save=-1,saveinit=0;
	    InputStreamReader input=null;
	    BufferedReader reader=null;
	    String data = null,request,escolhido;
		int escolha;
		
		   
		   
		    input = new InputStreamReader(System.in);
		    reader = new BufferedReader(input);
			System.out.println("tentar request");
		    request = "type|requestlist";////PEDE LISTA AO SERVER
		    
		    try {
				out.write(request);
				out.newLine(); //para enviar
	            out.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//System.out.println(request);
		
            try {
				data = in.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            System.out.println("resposta ao request - "+data);
            data.toString();
            String[] result = data.split(";|\\|");
            if(result[0].equalsIgnoreCase("type")&& result[1].equalsIgnoreCase("item_list")) {
            	for (int x=1; x<result.length; x++) {
   	    	 
            		if(result[x].equalsIgnoreCase("item_count")) {
            			//System.out.println("entrei");
            			counter=Integer.parseInt(result[x+1]);
            			System.out.println("numero de itens"+counter);
            			save=x+2;
            			
            		}
            	}
            }
            else {
            	System.out.println("erro a verificar listas func vota");
            }
            saveinit=save;
            int j=0;
            String[] aux = new String[counter];
            System.out.println("escolha uma lista para votar");
   	     while (j<counter) {
   	    	 System.out.println(j+":  lista - "+result[save]+" candidato - "+result[save+1]);
   	    	 aux[j]=result[save];
   	    	 save=save+2;
   	    	 j++;
   	     }
			escolha=IntScanner(0,counter);
			escolhido=aux[escolha];
			request="type|vote;lista|"+escolhido;
			request.toString();
			try {
				out.write(request);
				out.newLine(); //para enviar
	            out.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
           
			try {
				data = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}
			System.out.println(data);
            unlock(s,in,out);
			//System.out.println(request);
		    
		
	  
			
   }

	public static void loguser(Socket s,BufferedReader in, BufferedWriter out) {
    	String texto = "";
	    String texto2="";
	    InputStreamReader input=null;
	    BufferedReader reader=null;
	    
       
	    
    	while (true && logine==false) {
    		try {
    			

    		    
    		    input = new InputStreamReader(System.in);
    		    reader = new BufferedReader(input);
    			
    		    
    		    System.out.println("Numero do BI: ");
    		    texto = reader.readLine();
    		    while(texto.contains(";")||texto.contains("|")||texto.contains("\n")) {
    		    	System.out.println("Username Invalido \nUsername: ");
    		    	texto = reader.readLine();
    		    }
    		    
    		    System.out.println("Password: ");
    		    texto2=reader.readLine();
    		    while(texto.contains(";")||texto.contains("|")||texto.contains("\n")) {
    		    	System.out.println("Password Invalida \nPassword ");
    		    	texto2 = reader.readLine();
    		    }
    		} catch (Exception e) {
    			  System.out.println("erro a criar ins e outs" + e.getMessage());
    		}

    		String login="";
    		login= "type|login;"+"Username|"+texto +";"+"Password|"+texto2;
    		// WRITE INTO THE SOCKET
    		
    		try {
				out.write(login);
				out.newLine(); //HERE!!!!!!
                out.flush();
				System.out.println(login);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
			}

    		// READ FROM SOCKET
    		String data = null;
			try {
				data = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}

    		// DISPLAY WHAT WAS READ
    		System.out.println("Received: " + data);
    		String[] result = data.split(";|\\|");
    		if(result[0].equalsIgnoreCase("type")&&result[1].equalsIgnoreCase("status")) {
    	     for (int x=1; x<result.length; x++) {
    	    	 
    	    	 //System.out.println("prita results"+result[x]);
    	    	 if(result[x].equalsIgnoreCase("logged")) {//MUDAR PARA LOGGED
    	    		 if(result[x+1].equalsIgnoreCase("on")) {
    	    			 //System.out.println("Login Succefuloso");
    	    			 logine=true;
    	    			 vota(s,in,out);
    	    		 }
    	    			 
    	    	 
    	    		 else if(result[x].equalsIgnoreCase("off")) {
    	    			 System.out.println("login invalido");
    	    			 unlock(s,in,out);
    	    		 }
    	    	 }
    	         //System.out.println(result[x]);
    	     }
    		}
    		else {
    			System.out.println("erro no login func loguser");
    			unlock(s,in,out);
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
    
}