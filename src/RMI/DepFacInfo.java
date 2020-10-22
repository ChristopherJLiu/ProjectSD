package RMI;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;


@SuppressWarnings("serial")
public class DepFacInfo implements Serializable {
	
	//Capaz de faltar coisas?
	/**
	 * 
	 */

	public int Tipo;// 1-Faculdade,2-Departamento;
	public String Nome;
	public CopyOnWriteArrayList<DepFacInfo> Departamentos;
	
	
	public DepFacInfo(int a, String b, CopyOnWriteArrayList<DepFacInfo> deps) {
		this.Tipo=a;
		this.Nome=b;
		if(a!=1)
			this.Departamentos=new CopyOnWriteArrayList<DepFacInfo>();
		else
			this.Departamentos=deps;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj==null)
			return false;
		if (!DepFacInfo.class.isAssignableFrom(obj.getClass())) {
	        return false;
	    }
		final DepFacInfo other = (DepFacInfo) obj;
		if(this.Tipo!=other.Tipo)
			return false;
		if (!this.Nome.equals(other.Nome)) 
	        return false;
		if(!this.Departamentos.containsAll(other.Departamentos))
			return false;
		if(!other.Departamentos.containsAll(this.Departamentos))
			return false;
		return true;
	}
	

	@Override
    public int hashCode() {
        return Objects.hash(Tipo, Nome, Departamentos);
    }
	
	
	public DepFacInfo deepClone() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (DepFacInfo) ois.readObject();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	
	public void printerteste() {
		System.out.println("DEPFAC");
		System.out.println(this.Nome);
		System.out.println(this.Tipo);
		if(!this.Departamentos.isEmpty()) {
			for(DepFacInfo d : this.Departamentos)
				d.printerteste();
		}
	}
	
	
}
