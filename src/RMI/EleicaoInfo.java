package RMI;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;


@SuppressWarnings("serial")
public class EleicaoInfo implements Serializable {
	/**
	 * 
	 */
	public int ID;
	public int Tipo;// 1-Núcleo de estudantes,2-Conselho Geral,3-Direccao departamento,4-Direcao Faculdade
	public DepFacInfo DepFac;
	public Date DataI;
	public Date DataF;
	public String Titulo;
	public String Descricao;
	public CopyOnWriteArrayList<ListasCandidatas> Listas_CandidatasE;
	public CopyOnWriteArrayList<ListasCandidatas> Listas_CandidatasD;
	public CopyOnWriteArrayList<ListasCandidatas> Listas_CandidatasF;
	public CopyOnWriteArrayList<Voto> Votos_Eleicao;
	public CopyOnWriteArrayList<Mesa_voto> Mesas;
	
	
	public EleicaoInfo(int a, DepFacInfo g, Date inicio, Date fim, String e, String f) {
		Random rnd = new Random();
		this.ID=rnd.nextInt(99999);
		this.Tipo=a;
		if(a==2)
			this.DepFac= new DepFacInfo(2,"Conselho Geral", new CopyOnWriteArrayList<DepFacInfo>());
		else
			this.DepFac=g;
		this.DataI=inicio;
		this.DataF=fim;
		this.Titulo=e;
		this.Descricao=f;
		this.Listas_CandidatasE=new CopyOnWriteArrayList<ListasCandidatas>();
		this.Listas_CandidatasD=new CopyOnWriteArrayList<ListasCandidatas>();
		this.Listas_CandidatasF=new CopyOnWriteArrayList<ListasCandidatas>();
		this.Votos_Eleicao=new CopyOnWriteArrayList<Voto>();
		this.Mesas = new CopyOnWriteArrayList<Mesa_voto>();
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj==null)
			return false;
		if (!EleicaoInfo.class.isAssignableFrom(obj.getClass())) {
	        return false;
	    }
		final EleicaoInfo other = (EleicaoInfo) obj;
		if(this.Tipo!=other.Tipo)
			return false;
		if(this.ID!=other.ID)
			return false;
		if(!this.DepFac.equals(other.DepFac))
			return false;
		if(!this.DataI.equals(other.DataI))
			return false;
		if(!this.DataF.equals(other.DataF))
			return false;
		if(!this.Titulo.equals(other.Titulo))
			return false;
		if(!this.Descricao.equals(other.Descricao))
			return false;
		if(!this.Listas_CandidatasE.containsAll(other.Listas_CandidatasE))
			return false;
		if(!other.Listas_CandidatasE.containsAll(this.Listas_CandidatasE))
			return false;
		if(!this.Listas_CandidatasD.containsAll(other.Listas_CandidatasD))
			return false;
		if(!other.Listas_CandidatasD.containsAll(this.Listas_CandidatasD))
			return false;
		if(!this.Listas_CandidatasF.containsAll(other.Listas_CandidatasF))
			return false;
		if(!other.Listas_CandidatasF.containsAll(this.Listas_CandidatasF))
			return false;
		if(!this.Mesas.containsAll(other.Mesas))
			return false;
		if(!other.Mesas.containsAll(this.Mesas))
			return false;
		return true;
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(Tipo, DepFac, DataI, DataF,Titulo,Descricao,Listas_CandidatasE,Listas_CandidatasD,Listas_CandidatasF,Mesas);
    }
	
	
	public EleicaoInfo deepClone() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (EleicaoInfo) ois.readObject();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	public void printerteste() {
		System.out.println("Eleicao");
		System.out.println(this.Tipo);
		System.out.println(this.DepFac.Nome);
		System.out.println(this.DataI);
		System.out.println(this.DataF);
		System.out.println(this.Titulo);
		System.out.println(this.Descricao);
		for(ListasCandidatas l : this.Listas_CandidatasE) {
			l.printerteste();
		}
		for(ListasCandidatas l : this.Listas_CandidatasD) {
			l.printerteste();
		}
		for(ListasCandidatas l : this.Listas_CandidatasF) {
			l.printerteste();
		}
		for(Mesa_voto l : this.Mesas) {
			l.printerteste();
		}
		
	}
	
}
