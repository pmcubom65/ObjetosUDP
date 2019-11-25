import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws Exception {
		DatagramSocket ds=new DatagramSocket();
		InetAddress ia=InetAddress.getLocalHost();
		boolean bucle=true;
		Scanner sc=new Scanner(System.in);
		do{
		System.out.println("Introduzca identificador, son válidos del 51 al 54");
		String valor=sc.next();
		if (valor.equals("*")) {
			System.exit(0);
		}
		String identificador=valor;
		
		DatagramPacket dp=new DatagramPacket(identificador.getBytes(), identificador.getBytes().length, ia, 6679);
		ds.send(dp);
		
		byte[] c=new byte[1024];

		
		DatagramPacket dp1=new DatagramPacket(c, c.length, dp.getAddress(), dp.getPort());
		
		ds.receive(dp1);
		ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(dp1.getData()));
		
		Alumno a=(Alumno) ois.readObject();
		if (a instanceof Alumno) {
	
		System.out.println(a);
		}else {
			System.out.println("Alumno no encontrado");
		}
		} while (bucle);
}
}