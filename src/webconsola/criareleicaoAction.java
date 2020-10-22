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


public class criareleicaoAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private int tipo;
	private String depfac;
	private String datai, dataf;
	private String titulo, descricao;
	private CopyOnWriteArrayList<DepFacInfo> facs = new CopyOnWriteArrayList<DepFacInfo>();
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");//EXEMPLO:16/10/2017 11:00:00 PM
	EleicaoInfo eleicao;
	
	@Override
	public String execute() throws Exception {
		facs=this.getcriareleicaoBean().getDeps();
		eleicao = new EleicaoInfo(this.tipo,null,dateFormat.parse(this.datai), dateFormat.parse(this.dataf),this.titulo,this.descricao);
		if(this.tipo==3 || this.tipo==1){
		for(DepFacInfo f : facs)
			for(DepFacInfo d: f.Departamentos)
				if(d.Nome.equals(this.depfac))
					eleicao.DepFac=d;
		}
		else if(this.tipo==4)
			for(DepFacInfo f : facs)
				if(f.Nome.equals(this.depfac))
					eleicao.DepFac=f;
		
		if(eleicao.DepFac==null && this.tipo!=2){
			System.out.println("Faltadep");
			return "false";
		}
		this.getcriareleicaoBean().setEleicao(eleicao);
		System.out.println("sucesso");
		return "true";
	}
	
	
	
	public criareleicaoBean getcriareleicaoBean() {
		if(!session.containsKey("criareleicaoBean"))
			this.setcriareleicaoBean(new criareleicaoBean());
		return (criareleicaoBean) session.get("criareleicaoBean");
	}
	
	public void setcriareleicaoBean(criareleicaoBean criareleicaoBean) {
		this.session.put("criareleicaoBean", criareleicaoBean);
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getDepfac() {
		return depfac;
	}

	public void setDepfac(String depfac) {
		this.depfac = depfac;
	}

	public String getDatai() {
		return datai;
	}

	public void setDatai(String datai) {
		this.datai = datai;
	}

	public String getDataf() {
		return dataf;
	}

	public void setDataf(String dataf) {
		this.dataf = dataf;
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
