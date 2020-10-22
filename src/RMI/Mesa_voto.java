package RMI;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class Mesa_voto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public DepFacInfo local;
	public CopyOnWriteArrayList<UserInfo> membros_mesa;
	public int numero_terminais;
	public boolean estado;
	
	public Mesa_voto(DepFacInfo a, int b){
		this.local=a;
		this.membros_mesa = new CopyOnWriteArrayList<UserInfo>();
		this.numero_terminais=b;
		this.estado=false; //False=Dsligado True =Ligado
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj==null)
			return false;
		if (!Mesa_voto.class.isAssignableFrom(obj.getClass())) {
	        return false;
	    }
		final Mesa_voto other = (Mesa_voto) obj;
		
		if(!this.local.equals(other.local))
			return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
        return Objects.hash(local, membros_mesa, numero_terminais);
    }
	
	public Mesa_voto deepClone() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (Mesa_voto) ois.readObject();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	
	public void printerteste() {
		System.out.println("Mesa");
		System.out.println(this.local.Nome);
		System.out.println(this.numero_terminais);
		System.out.println("Membros da mesa");
		for(UserInfo u : this.membros_mesa)
			u.printerteste();
	}

}
