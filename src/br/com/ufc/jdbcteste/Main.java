package br.com.ufc.jdbcteste;

import java.util.ArrayList;
import java.util.Scanner;

import br.com.ufc.jdbcteste.pojo.*;
import br.com.ufc.jdbcteste.dao.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		UsuarioDAO userDAO = new UsuarioDAO();
		LivroDAO livroDAO = new LivroDAO();
		AlocacaoDAO alocDAO = new AlocacaoDAO();

		int option;
		Scanner scanner = new Scanner(System.in);
		boolean end = false;

		while(!end) {
			String app_name = "     ___           ___         ___                  ___           ___           ___           ___ \n"; 
			app_name += "    /\\  \\         /\\  \\       /\\  \\                /\\  \\         /\\  \\         /\\  \\         /\\__\\    \n";
			app_name += "   /::\\  \\       /::\\  \\     /::\\  \\               \\:\\  \\       /::\\  \\       |::\\  \\       /:/ _/_   \n";
			app_name += "  /:/\\:\\  \\     /:/\\:\\__\\   /:/\\:\\__\\               \\:\\  \\     /:/\\:\\  \\      |:|:\\  \\     /:/ /\\__\\  \n";
			app_name += " /:/ /::\\  \\   /:/ /:/  /  /:/ /:/  /           _____\\:\\  \\   /:/ /::\\  \\   __|:|\\:\\  \\   /:/ /:/ _/_ \n";
			app_name += "/:/_/:/\\:\\__\\ /:/_/:/  /  /:/_/:/  /           /::::::::\\__\\ /:/_/:/\\:\\__\\ /::::|_\\:\\__\\ /:/_/:/ /\\__\\ \n";
			app_name += "\\:\\/:/  \\/__/ \\:\\/:/  /   \\:\\/:/  /            \\:\\~~\\~~\\/__/ \\:\\/:/  \\/__/ \\:\\~~\\  \\/__/ \\:\\/:/ /:/  / \n";
			app_name += " \\::/__/       \\::/__/     \\::/__/              \\:\\  \\        \\::/__/       \\:\\  \\        \\::/_/:/  / \n";
			app_name += "  \\:\\  \\        \\:\\  \\      \\:\\  \\               \\:\\  \\        \\:\\  \\        \\:\\  \\        \\:\\/:/  /  \n";
			app_name += "   \\:\\__\\        \\:\\__\\      \\:\\__\\               \\:\\__\\        \\:\\__\\        \\:\\__\\        \\::/  /   \n";
			app_name += "    \\/__/         \\/__/       \\/__/                \\/__/         \\/__/         \\/__/         \\/__/ \n";    
			System.out.println(app_name);
			System.out.println("| 1 | Cadastrar usuário");
			System.out.println("| 2 | Listar usuários");
			System.out.println("| 3 | Apagar um usuário");
			System.out.println("| 4 | Cadastrar livro");
			System.out.println("| 5 | Alocar um livro a um usuário");
			System.out.println("| 6 | Listar alocações");
			System.out.println("| 0 | Sair");

			option = scanner.nextInt();
			scanner.nextLine();

			switch (option){
			case 1:{
				String nome, email, endereco;
				System.out.println("Digite o nome do usuario:");
				nome = scanner.nextLine();
				System.out.println("Digite a email do usuario:");
				email = scanner.nextLine();
				System.out.println("Digite o endereço do usuario:");
				endereco = scanner.nextLine();

				Usuario user = new Usuario(nome, email, endereco);
				if(userDAO.addUser(user)) {
					System.out.println("Inserido com sucesso!");
				}else {
					System.err.println("Erro ao inserir o usuário.");
				}
				break;
			}case 2:{
				ArrayList<Usuario> userList = userDAO.getListUser();
				for(Usuario user : userList){
					System.out.println(user.toString());
				}
				break;
			}case 3:{
				System.out.println("Digite o ID do usuário que deseja apagar:");
				int id = scanner.nextInt();
				if(userDAO.deleteUser(id)) {
					System.out.println("Deletado com sucesso!");
				}else {
					System.err.println("Erro ao deletar o usuário.");
				}
			}case 4:{
				String titulo, autor, editora;
				System.out.println("Digite o titulo do livro:");
				titulo = scanner.nextLine();
				System.out.println("Digite a editora do livro:");
				editora = scanner.nextLine();
				System.out.println("Digite o autor:");
				autor = scanner.nextLine();
				
				Livro livro = new Livro(titulo, editora, autor);
				
				livroDAO.addLivro(livro);
				break;
			}case 5:{
				int idLivro, idUsuario;
				System.out.println("Digite o ID do usuário:");
				idUsuario = scanner.nextInt();
				System.out.println("Digite o ID do livro:");
				idLivro = scanner.nextInt();
				
				Usuario user = userDAO.getUserById(idUsuario);
				Livro livro = livroDAO.getLivroById(idLivro);
				
				alocDAO.alocar(livro, user);
				
				break;
			}case 6:{
				ArrayList<Alocacao> listAloc = alocDAO.getListAlocacao();
				for(Alocacao aloc : listAloc){
					System.out.println(aloc.getUsuario().getNome() + " alocou o livro " + aloc.getLivro().getTitulo());
				}
				break;
			}default:
				end = true;
				break;
			}
		}
	}

}
