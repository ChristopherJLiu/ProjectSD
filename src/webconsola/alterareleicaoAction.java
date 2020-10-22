package webconsola;

import RMI.*;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class alterareleicaoAction extends ActionSupport implements SessionAware{
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private CopyOnWriteArrayList<EleicaoInfo> eleicoes=new CopyOnWriteArrayList<EleicaoInfo>();
	private EleicaoInfo eleicao;
	private int ID;
	private int checker=-1;
	private String titulo, descricao;
	

	@Override
	public String execute() throws Exception {
		
		setEleicoes(this.getalterareleicaoBean().geteleicoes());
		for(EleicaoInfo e : eleicoes)
			if(e.ID==this.ID){
				eleicao=e;
				checker=0;
			}
		if(checker==-1){
			System.out.println("ID errado"+this.ID);
			return "false";
		}
		if(!this.getalterareleicaoBean().setEleicao(eleicao, this.titulo, this.descricao).equals("Eleicao Actualizada com os novos dados")){
			System.out.println(this.getalterareleicaoBean().setEleicao(eleicao, this.titulo, this.descricao));
			return "false";
		}
		return "true";
	}
	
	public alterareleicaoBean getalterareleicaoBean() {
		if(!session.containsKey("alterareleicaoBean"))
			this.setalterareleicaoBean(new alterareleicaoBean());
		return (alterareleicaoBean) session.get("alterareleicaoBean");
	}
	
	public void setalterareleicaoBean(alterareleicaoBean alterareleicaoBean) {
		this.session.put("alterareleicaoBean", alterareleicaoBean);
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public EleicaoInfo getEleicao() {
		return eleicao;
	}

	public void setEleicao(EleicaoInfo eleicao) {
		this.eleicao = eleicao;
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	

}
