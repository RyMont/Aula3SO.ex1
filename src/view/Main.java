package view;

import controller.PackageController;
import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		PackageController packageController = new PackageController();
		int opc;
		
		do {			
			opc = Integer.parseInt(JOptionPane.showInputDialog(
				"MENU\n"
				+ "1 - Configuração IP\n"
				+ "2 - Teste Ping\n"
				+ "9 - Finalizar"));
		
			switch (opc) {
				case 1:
					packageController.ip();
					break;
				case 2:
					packageController.ping();
					break;
				case 9:
					JOptionPane.showMessageDialog(null, "Fim!");
					break;
				default:
					JOptionPane.showMessageDialog(null, "Opção inválida!");
			}
		} while (opc!=9);
	}
}
