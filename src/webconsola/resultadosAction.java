package webconsola;

import RMI.*;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class resultadosAction extends ActionSupport implements SessionAware{
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private CopyOnWriteArrayList<EleicaoInfo> eleicoes=new CopyOnWriteArrayList<EleicaoInfo>();
	private CopyOnWriteArrayList<Voto> votos=new CopyOnWriteArrayList<Voto>();
	private EleicaoInfo eleicao;
	private ArrayList<String> res = new ArrayList<String>();
	private int ID;
	private int checker=-1;
	
	@Override
	public String execute() throws Exception {
		setEleicoes(new CopyOnWriteArrayList<EleicaoInfo>());
		if(!this.getresultadosBean().getEleicoes().isEmpty())
			setEleicoes(this.getresultadosBean().getEleicoes());
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
		ArrayList<String> listas = new ArrayList<String>();
		ArrayList<Integer> listas_votos = new ArrayList<Integer>();
		for(int i=0;i<votos.size();i++){
			if(!listas.contains(votos.get(i).lista_votada)){
				listas.add(votos.get(i).lista_votada);
				listas_votos.add(0);
			}
		}
		for(int i=0;i<votos.size();i++){
			if(listas.contains(votos.get(i).lista_votada)){
				listas_votos.set(listas.indexOf(votos.get(i).lista_votada),listas_votos.get(listas.indexOf(votos.get(i).lista_votada))+1);
			}
		}
		for(int i=0;i<listas.size();i++){
			res.add("A lista: "+listas.get(i)+" obteve:"+listas_votos.get(i)+" votos com uma percentagem de:"+(listas_votos.get(i)/votos.size())*100+"% do total dos votos");
		}
		return "true";
	}
	
	
	public resultadosBean getresultadosBean() {
		if(!session.containsKey("resultadosBean"))
			this.setresultadosBean(new resultadosBean());
		return (resultadosBean) session.get("resultadosBean");
	}
	
	public void setresultadosBean(resultadosBean resultadosBean) {
		this.session.put("resultadosBean", resultadosBean);
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


	public ArrayList<String> getRes() {
		return res;
	}


	public void setRes(ArrayList<String> res) {
		this.res = res;
	}

}
