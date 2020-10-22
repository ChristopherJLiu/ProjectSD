package RMI;
import java.io.*;
import java.util.Date;
import java.util.Objects;

@SuppressWarnings("serial")
public class UserInfo implements Serializable {
	/**
	 * 
	 */
	public String Nome;
	public int Tipo; //1-Aluno,2-Docente,3-Funcionario 4-Browser Admin
	public String Password;
	public double ContactoTel;
	public String Morada;
	public double BI;
	public Date BIval;
	public DepFacInfo DepFac;
	
	public UserInfo(String a, int b, String c, double d, String e, double f, Date bIVal2 ,DepFacInfo h) {
		this.Nome = a;
		this.Tipo = b;
		this.Password = c;
		this.ContactoTel =d;
		this.Morada = e;
		this.BI=f;
		this.BIval=bIVal2;
		this.DepFac=h;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj==null)
			return false;
		if (!UserInfo.class.isAssignableFrom(obj.getClass())) {
	        return false;
	    }
		
		final UserInfo other = (UserInfo) obj;	
		if(this.BI!=other.BI)
			return false;
		return true;
		
	}
		
	
	@Override
	public int hashCode() {
        return Objects.hash(Nome, Tipo, Password, ContactoTel, Morada, BI, BIval, DepFac);
    }
	
	public UserInfo deepClone() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (UserInfo) ois.readObject();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	
	public void printerteste() {
		System.out.println("User");
		System.out.println(this.Nome);
		
	}
}


