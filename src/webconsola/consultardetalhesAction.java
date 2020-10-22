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

public class consultardetalhesAction extends ActionSupport implements SessionAware{
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private CopyOnWriteArrayList<EleicaoInfo> eleicoes;
	
	@Override
	public String execute() throws Exception {
		setEleicoes(new CopyOnWriteArrayList<EleicaoInfo>());
		if(!this.getconsultardetalhesBean().getEleicao().isEmpty())
			setEleicoes(this.getconsultardetalhesBean().getEleicao());
		
		return "true";
	}
	
	
	public consultardetalhesBean getconsultardetalhesBean() {
		if(!session.containsKey("consultardetalhesBean"))
			this.setconsultardetalhesBean(new consultardetalhesBean());
		return (consultardetalhesBean) session.get("consultardetalhesBean");
	}
	
	public void setconsultardetalhesBean(consultardetalhesBean consultardetalhesBean) {
		this.session.put("consultardetalhesBean", consultardetalhesBean);
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

}
