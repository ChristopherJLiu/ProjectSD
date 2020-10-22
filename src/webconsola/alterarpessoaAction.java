package webconsola;

import com.opensymphony.xwork2.ActionSupport;

import RMI.*;

import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class alterarpessoaAction extends ActionSupport implements SessionAware{
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private UserInfo user;
	private UserInfo velho;
	private String nome,password,morada;
	private int tipo;
	private double contato,bi;
	private String datavalbi;
	private CopyOnWriteArrayList<DepFacInfo> facs = new CopyOnWriteArrayList<DepFacInfo>();
	private CopyOnWriteArrayList<UserInfo> todos = new CopyOnWriteArrayList<UserInfo>();
	private String dep;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");//EXEMPLO:16/10/2017 11:00:00 PM
	private int checker=-1;
	
	@Override
	public String execute() throws Exception {
		facs=this.getalterarpessoaBean().getDeps();
		todos=this.getalterarpessoaBean().getTodos();
		user = new UserInfo(this.nome,this.tipo,this.password,this.contato,this.morada,this.bi,dateFormat.parse(this.datavalbi),null);
		for(DepFacInfo f : facs)
			for(DepFacInfo d: f.Departamentos)
				if(d.Nome.equals(this.dep))
					user.DepFac=d;
		if(user.DepFac==null){
			System.out.println("Faltadep");
			return "false";
		}
		for(UserInfo u: todos)
			if(u.BI==this.bi){
				velho=u;
				checker=0;
			}
		if(checker!=0){
			System.out.println("Bi nao encontrado");
			return "false";
		}
		String teste=this.getalterarpessoaBean().setUser(user, velho);
		if(!teste.equals("UserAlterado")){
			System.out.println(teste);
			return"false";
		}
		else{
			System.out.println(teste);
			return "true";
		}
	}
	
	
	
	
	public alterarpessoaBean getalterarpessoaBean() {
		if(!session.containsKey("alterarpessoaBean"))
			this.setalterarpessoaBean(new alterarpessoaBean());
		return (alterarpessoaBean) session.get("alterarpessoaBean");
	}
	
	public void setalterarpessoaBean(alterarpessoaBean alterarpessoaBean) {
		this.session.put("alterarpessoaBean", alterarpessoaBean);
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	
	
	
	public UserInfo getUser() {
		return user;
	}
	public void setUser(UserInfo user) {
		this.user = user;
	}
	public UserInfo getVelho() {
		return velho;
	}
	public void setVelho(UserInfo velho) {
		this.velho = velho;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMorada() {
		return morada;
	}
	public void setMorada(String morada) {
		this.morada = morada;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public double getContato() {
		return contato;
	}
	public void setContato(double contato) {
		this.contato = contato;
	}
	public double getBi() {
		return bi;
	}
	public void setBi(double bi) {
		this.bi = bi;
	}
	public String getDatavalbi() {
		return datavalbi;
	}
	public void setDatavalbi(String datavalbi) {
		this.datavalbi = datavalbi;
	}
	public String getDep() {
		return dep;
	}
	public void setDep(String dep) {
		this.dep = dep;
	}
	public int getChecker() {
		return checker;
	}
	public void setChecker(int checker) {
		this.checker = checker;
	}
	public CopyOnWriteArrayList<UserInfo> getTodos() {
		return todos;
	}
	public void setTodos(CopyOnWriteArrayList<UserInfo> todos) {
		this.todos = todos;
	}
	public CopyOnWriteArrayList<DepFacInfo> getFacs() {
		return facs;
	}
	public void setFacs(CopyOnWriteArrayList<DepFacInfo> facs) {
		this.facs = facs;
	}
	



}
