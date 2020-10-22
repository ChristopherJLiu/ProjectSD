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

public class criarlistaAction extends ActionSupport implements SessionAware{
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private CopyOnWriteArrayList<EleicaoInfo> eleicoes=new CopyOnWriteArrayList<EleicaoInfo>();
	private EleicaoInfo eleicao;
	private int ID, checker=-1;
	private double BI1,BI2,BI3;
	private String nomelista;
	private int tipo;
	
	
	
	@Override
	public String execute() throws Exception {
		setEleicoes(this.getcriarlistaBean().getEleicoes());
		for(EleicaoInfo e : eleicoes)
			if(e.ID==this.ID){
				eleicao=e;
				checker=0;
			}
		if(checker==-1){
			System.out.println("ID errado"+this.ID);
			return "false";
		}
		int res=this.getcriarlistaBean().AddLista(eleicao, this.BI1, this.BI2, this.BI3, this.nomelista, this.tipo);
		System.out.println(res);
		if(res==1)
			return "true";
		return "false";
		
	}
	
	
	public criarlistaBean getcriarlistaBean() {
		if(!session.containsKey("criarlistaBean"))
			this.setcriarlistaBean(new criarlistaBean());
		return (criarlistaBean) session.get("criarlistaBean");
	}
	
	public void setcriarlistaBean(criarlistaBean criarlistaBean) {
		this.session.put("criarlistaBean", criarlistaBean);
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

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public EleicaoInfo getEleicao() {
		return eleicao;
	}

	public void setEleicao(EleicaoInfo eleicao) {
		this.eleicao = eleicao;
	}


	public int getTipo() {
		return tipo;
	}


	public void setTipo(int tipo) {
		this.tipo = tipo;
	}


	public String getNomelista() {
		return nomelista;
	}


	public void setNomelista(String nomelista) {
		this.nomelista = nomelista;
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
