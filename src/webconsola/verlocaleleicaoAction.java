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

public class verlocaleleicaoAction extends ActionSupport implements SessionAware{
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private CopyOnWriteArrayList<EleicaoInfo> eleicoes=new CopyOnWriteArrayList<EleicaoInfo>();
	private CopyOnWriteArrayList<Voto> votos=new CopyOnWriteArrayList<Voto>();
	private EleicaoInfo eleicao;
	private int ID;
	private int checker=-1;
	
	@Override
	public String execute() throws Exception {
		setEleicoes(new CopyOnWriteArrayList<EleicaoInfo>());
		if(!this.getverlocaleleicaoBean().getEleicoes().isEmpty())
			setEleicoes(this.getverlocaleleicaoBean().getEleicoes());
		for(EleicaoInfo e : eleicoes)
			if(e.ID==this.ID){
				eleicao=e;
				checker=0;
			}
		if(checker==-1){
			System.out.println("ID errado"+this.ID);
			return "false";
		}
		setVotos(eleicao.Votos_Eleicao);
		
		return "true";
	}
	
	
	public verlocaleleicaoBean getverlocaleleicaoBean() {
		if(!session.containsKey("verlocaleleicaoBean"))
			this.setverlocaleleicaoBean(new verlocaleleicaoBean());
		return (verlocaleleicaoBean) session.get("verlocaleleicaoBean");
	}
	
	public void setverlocaleleicaoBean(verlocaleleicaoBean verlocaleleicaoBean) {
		this.session.put("verlocaleleicaoBean", verlocaleleicaoBean);
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

	public CopyOnWriteArrayList<Voto> getVotos() {
		return votos;
	}

	public void setVotos(CopyOnWriteArrayList<Voto> votos) {
		this.votos = votos;
	}


	public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
	}

}
