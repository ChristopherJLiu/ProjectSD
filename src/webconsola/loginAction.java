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


public class loginAction  extends ActionSupport implements SessionAware{
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private double BI;
	private String pass;
	
	
	@Override
	public String execute() throws Exception
	{
		if(this.getloginBean().check(this.BI, this.pass)){
			session.put("BI",this.BI);
			return "true";
		}
		return "false";
	}
	
	
	
	public loginBean getloginBean() {
		if(!session.containsKey("loginBean"))
			this.setloginBean(new loginBean());
		return (loginBean) session.get("loginBean");
	}
	
	public void setloginBean(loginBean loginBean) {
		this.session.put("loginBean", loginBean);
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public double getBI() {
		return BI;
	}
	public void setBI(double bI) {
		BI = bI;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}

}
