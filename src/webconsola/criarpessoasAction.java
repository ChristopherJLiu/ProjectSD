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


public class criarpessoasAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private UserInfo user;
	private String nome,password,morada;
	private int tipo;
	private double contato,bi;
	private String datavalbi;
	private CopyOnWriteArrayList<DepFacInfo> facs = new CopyOnWriteArrayList<DepFacInfo>();
	private String dep;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");//EXEMPLO:16/10/2017 11:00:00 PM
	
	@Override
	public String execute() throws Exception {
		facs=this.getcriarpessoasBean().getDeps();
		user = new UserInfo(this.nome,this.tipo,this.password,this.contato,this.morada,this.bi,dateFormat.parse(this.datavalbi),null);
		for(DepFacInfo f : facs)
			for(DepFacInfo d: f.Departamentos)
				if(d.Nome.equals(this.dep))
					user.DepFac=d;
		System.out.println(this.nome+this.tipo+this.password+this.contato+this.morada+this.bi+dateFormat.parse(this.datavalbi)+this.dep);
		if(user.DepFac==null){
			System.out.println("Faltadep");
			return "false";
		}
		if(this.getcriarpessoasBean().setUser(user)){
			System.out.println("sucesso");
			return "true";
		}
		else
			return "false";
	}
	
	
	
	
	public criarpessoasBean getcriarpessoasBean() {
		if(!session.containsKey("criarpessoasBean"))
			this.setcriarpessoasBean(new criarpessoasBean());
		return (criarpessoasBean) session.get("criarpessoasBean");
	}
	
	public void setcriarpessoasBean(criarpessoasBean criarpessoasBean) {
		this.session.put("criarpessoasBean", criarpessoasBean);
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
}