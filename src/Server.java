import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;

public class Server {

	public static void main(String[] args) throws Exception {
		DatagramSocket ds=new DatagramSocket(6679);
		InetAddress ia=InetAddress.getLocalHost();
		Curso c=new Curso("DAM", "informatica");
		Alumno[] lista=new Alumno[4];
		lista[0]=new Alumno("54","Paco", c,5);
		lista[1]=new Alumno("51","Pepe", c,5);
		lista[2]=new Alumno("52","Lolo", c,5);
		lista[3]=new Alumno("53","Manu", c,5);
	
	
		
		while (true) {
			byte[] identificador=new byte[1024];
			DatagramPacket dp=new DatagramPacket(identificador, identificador.length, ia, 6679 );
			ds.receive(dp);
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			ObjectOutputStream oos=new ObjectOutputStream(bos);
			
			String s=new String(dp.getData());
			System.out.println("El servidor ha leido "+s);
			int contador=0;
			for (int i=0; i<lista.length; i++) {
				if (lista[i].getIdalumno().equals(s.trim())) {
					oos.writeObject(lista[i]);
					DatagramPacket dp1=new DatagramPacket(bos.toByteArray(), bos.toByteArray().length, dp.getAddress(), dp.getPort() );
					ds.send(dp1);
					contador++;
					break;
				}
			}
			if (contador==0) {
				oos.writeObject(null);
				DatagramPacket dp1=new DatagramPacket(bos.toByteArray(), bos.toByteArray().length, dp.getAddress(), dp.getPort() );
				ds.send(dp1);
			}
			contador=0;
		}
		
		
		
	}
}
