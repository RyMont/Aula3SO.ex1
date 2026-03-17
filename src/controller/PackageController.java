package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;

public class PackageController {

	public PackageController() {
		super();
	}
	
	// mostra tipo do SO
	private String os() {
		return System.getProperty("os.name");
	}
	
	// adaptadores e IPv4
	public void ip() {
	    String sistema = os();
	    String comandoStr = null;

	    try {
	    	
			if (sistema.contains("Win")) {
				comandoStr = "ipconfig";
			} else if (sistema.contains("Linux")) {
				comandoStr = "ip addr";
			} else {
				System.out.println("Sistema Operacional não encotrado");
			}

	        String[] comando = comandoStr.split(" ");
	        Process p = Runtime.getRuntime().exec(comando);

	        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        String linha;
	        String adaptadorAtual = null;

	        while ((linha = reader.readLine()) != null) {
	            linha = linha.trim();

	            if (sistema.contains("Win")) {
	                // nome do adaptador
	                if (linha.contains("Adaptador")) {
	                    adaptadorAtual = linha;
	                }

	                //  IPv4
	                if (linha.contains("IPv4") && adaptadorAtual != null) {
	                    String[] partes = linha.split(":");
	                    if (partes.length > 1) {
	                        System.out.println(adaptadorAtual);             // nome do adaptador
	                        System.out.println("IPv4: " + partes[1].trim()); // IPv4
	                        System.out.println();
	                        adaptadorAtual = null;
	                    }
	                }

	            } else {
	                // Linux
	                // Detecta nome do adaptador (linha que termina com ":")
	                if (linha.contains(":") && !linha.contains("lo")) {
	                    adaptadorAtual = linha.split(":")[1].trim();
	                }

	                // Detecta IPv4
	                if (linha.contains("inet ") && !linha.contains("127.0.0.1") && adaptadorAtual != null) {
	                    String[] partes = linha.split(" ");
	                    System.out.println(adaptadorAtual);       // mostra nome do adaptador
	                    System.out.println("IPv4: " + partes[1]); // mostra IPv4
	                    System.out.println();
	                    adaptadorAtual = null;                     // reseta pra se tiver outro
	                }
	            }
	        }

	    } catch (Exception e) {
	        System.out.println("Erro ao obter IP: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	// ping pong
	public void ping() {
		String comando = "";

		if (os().contains("Windows")) {
			comando = "ping -4 -n 10 www.google.com.br";
		} else if (os().contains("Linux")) {
			comando = "ping -4 -c 10 www.google.com.br";
		} else {
			System.out.println("Sistema Operacional não encotrado");
		}

		try {
			Process p = Runtime.getRuntime().exec(comando);
			InputStream fluxo = p.getInputStream();
			InputStreamReader leitor = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(leitor);

			
			String linha = buffer.readLine();
			while (linha != null) {
				//System.out.println(linha);
			    if (linha.contains(",") && linha.toLowerCase().contains("ms")) {
			        String[] partes = linha.split("=");
			        if (partes.length > 3) {
			            String media = partes[3].trim(); // última parte é a média
			            System.out.println("Tempo médio: " + media);
			        }
			    }

			    if (linha.contains("rtt min/avg/max")) {
			        String[] partes = linha.split("=");
			        if (partes.length > 1) {
			            String[] valores = partes[1].trim().split("/");
			            String media = valores[1]; // posição 1 = avg
			            System.out.println("Tempo médio: " + media + " ms");
			        }
			    }

			    linha = buffer.readLine();
			}

			buffer.close();
			leitor.close();
			fluxo.close();
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
