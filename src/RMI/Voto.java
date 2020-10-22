package RMI;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@SuppressWarnings("serial")
public class Voto implements Serializable {
	
	/**
	 * 
	 */
	public double BI;
	public Mesa_voto Mesa;
	public Date Data;
	public String lista_votada;
	
	public Voto(double a,Mesa_voto b, String d) {
		this.BI = a;
		this.Mesa = b;
		this.Data = null;
		this.lista_votada = d;
		
	}
	
	
	@Override
	public boolean equals(Object obj){
		if(obj==null)
			return false;
		if (!Voto.class.isAssignableFrom(obj.getClass())) {
	        return false;
	    }
		final Voto other = (Voto) obj;
		
		if(this.BI!=other.BI)
			return false;
		
		if(!this.Mesa.equals(other.Mesa))
			return false;
		
		if(this.Data!=other.Data)
			return false;
		
		if(!this.lista_votada.equals(other.lista_votada))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
        return Objects.hash(BI, Mesa, Data, lista_votada);
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
	
	
}
