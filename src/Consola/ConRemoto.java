package Consola;
import java.rmi.*;

import RMI.EleicaoInfo;
import RMI.Mesa_voto;

public interface ConRemoto extends Remote {

	void Notify_Voto(EleicaoInfo eleicao) throws RemoteException;

	void Notify_InicioEleicao(EleicaoInfo eleicao)  throws RemoteException;

	void Notify_FimEleicao(EleicaoInfo eleicao) throws RemoteException;

	void Notify_Estado_Mesa(Mesa_voto mesa, EleicaoInfo eleicao) throws RemoteException;
	
}
