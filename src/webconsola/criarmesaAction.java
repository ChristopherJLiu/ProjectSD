package webconsola;

import RMI.*;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class criarmesaAction extends ActionSupport implements SessionAware{
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private CopyOnWriteArrayList<EleicaoInfo> eleicoes=new CopyOnWriteArrayList<EleicaoInfo>();
	private int terminais;
	private String local;
	private CopyOnWriteArrayList<DepFacInfo> facs = new CopyOnWriteArrayList<DepFacInfo>();
	private EleicaoInfo eleicao;
	private int ID;
	private int checker=-1;
	private DepFacInfo dep = new DepFacInfo(-1,"",new CopyOnWriteArrayList<DepFacInfo>());
	private double BI1,BI2,BI3;
	
	
	
	@Override
	public String execute() throws Exception {
		facs=this.getcriarmesaBean().getDeps();
		setEleicoes(this.getcriarmesaBean().geteleicoes());
		for(DepFacInfo f : facs)
			for(DepFacInfo d: f.Departamentos)
				if(d.Nome.equals(this.local))
					dep=d;
		if(dep.Tipo==-1){
			System.out.println("Falta dep");
			return "false";
		}
		for(EleicaoInfo e : eleicoes)
			if(e.ID==this.ID){
				eleicao=e;
				checker=0;
			}
		if(checker==-1){
			System.out.println("ID errado"+this.ID);
			return "false";
		}
		String res=this.getcriarmesaBean().setEleicao(eleicao, this.terminais, this.dep,this.BI1,this.BI2,this.BI3);
		if(!res.equals("Eleicao Actualizada com os novos dados")){
			System.out.println(res);
			return "false";
		}
		return "true";
	}
	
	
	public criarmesaBean getcriarmesaBean() {
		if(!session.containsKey("criarmesaBean"))
			this.setcriarmesaBean(new criarmesaBean());
		return (criarmesaBean) session.get("criarmesaBean");
	}
	
	public void setcriarmesaBean(criarmesaBean criarmesaBean) {
		this.session.put("criarmesaBean", criarmesaBean);
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public CopyOnWriteArrayList<EleicaoInfo> getEleicoes() {
		return eleicoes;
	}

	public void setEleicoes(CopyOnWriteArrayList<EleicaoInfo> eleicoes) {
		this.eleicoes = eleicoes;
	}

	public int getTerminais() {
		return terminais;
	}

	public void setTerminais(int terminais) {
		this.terminais = terminais;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public CopyOnWriteArrayList<DepFacInfo> getFacs() {
		return facs;
	}

	public void setFacs(CopyOnWriteArrayList<DepFacInfo> facs) {
		this.facs = facs;
	}

	public EleicaoInfo getEleicao() {
		return eleicao;
	}

	public void setEleicao(EleicaoInfo eleicao) {
		this.eleicao=eleicao.deepClone();
	}


	public DepFacInfo getDep() {
		return dep;
	}


	public void setDep(DepFacInfo dep) {
		this.dep = dep;
	}


	public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
	}


	public double getBI1() {
		return BI1;
	}


	public void setBI1(double bI1) {
		BI1 = bI1;
	}


	public double getBI2() {
		return BI2;
	}


	public void setBI2(double bI2) {
		BI2 = bI2;
	}


	public double getBI3() {
		return BI3;
	}


	public void setBI3(double bI3) {
		BI3 = bI3;
	}

}
