package restaurante.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import restaurante.jdbc.ConnectionFactory;
import restaurante.model.Pratos;

public class CrudPratos {
	
	//Cadastrar Pratos
		public static void cadastrarPrato()throws SQLException{
			Pratos prato = new Pratos();
			Connection conexao = ConnectionFactory.createConnection();
			
			prato.setNome(JOptionPane.showInputDialog("Nome do Prato:"));
			prato.setDescricao(JOptionPane.showInputDialog("Descrição do Prato:"));
			String StringPreco  = JOptionPane.showInputDialog("Preço do Prato");
					
			String[] TipoPratos = {"Entrada", "Principal", "Sobremesa", "Cafe", "Bebidas", "Outro"}; //Categorias Pratos
			
			String pratoSelecionado = (String) JOptionPane.showInputDialog(null, 
					"Selecione a categoria do prato:", "Escolha a Categoria",
					JOptionPane.QUESTION_MESSAGE, null, TipoPratos, TipoPratos[0]);
			
			prato.setCategoria(pratoSelecionado);
			
			if(prato.getNome().isEmpty() || StringPreco.isEmpty() || 
					prato.getCategoria().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Não foi possivel inserir (Campo vazio).");
			}else {
				
				prato.setPreco(Double.parseDouble(StringPreco));
				String sql = "insert into pratos (nome, descricao, preco, categoria) "
						+ "values (?,?,?,?);";
				
				PreparedStatement cmd = conexao.prepareStatement(sql);
				cmd.setString(1, prato.getNome());
				cmd.setString(2, prato.getDescricao());
				cmd.setDouble(3, prato.getPreco());
				cmd.setString(4, prato.getCategoria());
				cmd.execute();
				
				JOptionPane.showMessageDialog(null, "Prato inserido com sucesso.");
				cmd.close();
			}
		}
		
		//Listar todos os Pratos
		public static void listarPratos() throws SQLException{
			Pratos pratos = new Pratos();
			Connection conexao = ConnectionFactory.createConnection();
			
			String sql = "select * from pratos order by nome asc;";
			
			PreparedStatement cmd = conexao.prepareStatement(sql);
			
			ResultSet resultado = cmd.executeQuery();
			
			//variavel para receber os dados
			String ListaPratos; 
			ListaPratos = "---- Pratos encontrados----\n";
			
			while(resultado.next()) {
				ListaPratos += "Nome: " + resultado.getString("nome") 	
							  +"  Preço: " + resultado.getDouble("preco")
							  +"  Categoria: " + resultado.getString("categoria")
							  +"  ID: " + resultado.getInt("id")
							  +"\nDescrição: " + resultado.getString("descricao")
							  +"\n\n";
							  	
			}
			
			JOptionPane.showMessageDialog(null, ListaPratos);
			conexao.close();		
		}
		
		public static void ListarPorCategoriaPratos() throws SQLException{
			Pratos pratos = new Pratos();
			Connection conexao = ConnectionFactory.createConnection();
			
			String[] TipoPratos = {"Entrada", "Principal", "Sobremesa", "Cafe", "Bebidas", "Outro"}; //Categorias Pratos
			
			String pratoSelecionado = (String) JOptionPane.showInputDialog(null, 
					"Selecione a categoria do prato:", "Escolha a Categoria",
					JOptionPane.QUESTION_MESSAGE, null, TipoPratos, TipoPratos[0]);
			
			pratos.setCategoria(pratoSelecionado);
				
			String sql = "select * from pratos where categoria = ? order by nome asc;";
			
			PreparedStatement cmd = conexao.prepareStatement(sql);
			cmd.setString(1, pratos.getCategoria());
			
			ResultSet resultado = cmd.executeQuery();
			
			String ListaPratos; //variavel para receber os dados
			ListaPratos = "---- Pratos encontrados----\n";
			
			while(resultado.next()) {
				ListaPratos += "Nome: " + resultado.getString("nome") 	
							  +"  Preço: " + resultado.getDouble("preco")
							  +"  Categoria: " + resultado.getString("categoria")
							  +"  ID: " + resultado.getInt("id")
							  +"\nDescrição: " + resultado.getString("descricao")
							  +"\n\n";		  	
			}
			JOptionPane.showMessageDialog(null, ListaPratos);
			conexao.close();
		}	
		
		public static void UpdatePratos() throws SQLException{
			Pratos pratos = new Pratos();
			Connection conexao = ConnectionFactory.createConnection();
			
			String sql = "select id, nome from pratos order by nome asc;";
			
			PreparedStatement cmd = conexao.prepareStatement(sql);
			
			ResultSet resultado = cmd.executeQuery();
			
			Map<String, Integer> mapaPratos = new HashMap<>();
			ArrayList<String> listaPratos = new ArrayList<>();
			
			while(resultado.next()) {
				int pratoId = resultado.getInt("id");
				String prato = resultado.getString("nome");
				
				listaPratos.add(prato);
				mapaPratos.put(prato, pratoId);
			
			}
			
			String[] pratosArray = listaPratos.toArray(new String[0]);
			String pratoSelecionado = (String) JOptionPane.showInputDialog(
					null,
					"Selecione o prato: ",
					"Seleção de Prato",
					JOptionPane.QUESTION_MESSAGE,
					null,
					pratosArray,
					pratosArray[0]
			);
					
			if(pratoSelecionado != null) {
				int pratoIdSelecionado = mapaPratos.get(pratoSelecionado);
							
				pratos.setDescricao(JOptionPane.showInputDialog("Descrição do Prato:"));
				String StringPreco  = JOptionPane.showInputDialog("Preço do Prato");
						
				String[] TipoPratos = {"Entrada", "Principal", "Sobremesa", "Cafe", "Bebidas", "Outro"}; //Categorias Pratos
				
				String pratoSelecionadoUpdate = (String) JOptionPane.showInputDialog(null, 
						"Selecione a categoria do prato:", "Escolha a Categoria",
						JOptionPane.QUESTION_MESSAGE, null, TipoPratos, TipoPratos[0]);
				
				pratos.setCategoria(pratoSelecionadoUpdate);
				
				if(StringPreco.isEmpty() || 
						pratos.getCategoria().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Não foi possivel inserir (Campo vazio).");
				}else {
					
					pratos.setPreco(Double.parseDouble(StringPreco));
					String sqlupdate = "UPDATE pratos SET descricao = ?, preco = ?, categoria = ? WHERE id = ?;";
					
					PreparedStatement cmdupdate = conexao.prepareStatement(sqlupdate);
					cmdupdate.setString(1, pratos.getDescricao());
					cmdupdate.setDouble(2, pratos.getPreco());
					cmdupdate.setString(3, pratos.getCategoria());
					cmdupdate.setInt(4, pratoIdSelecionado);
					
					cmdupdate.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Prato alterado com sucesso.");
					cmdupdate.close();
				}
			}else {
				JOptionPane.showMessageDialog(null, "Nenhum prato selecionado.");
			}
		}
		
		public static void DeletePratos() throws SQLException{
			Pratos pratos = new Pratos();
			Connection conexao = ConnectionFactory.createConnection();
			
			String sql = "select id, nome from pratos order by nome asc;";
			
			PreparedStatement cmd = conexao.prepareStatement(sql);
			
			ResultSet resultado = cmd.executeQuery();
			
			Map<String, Integer> mapaPratos = new HashMap<>();
			ArrayList<String> listaPratos = new ArrayList<>();
			
			while(resultado.next()) {
				int pratoId = resultado.getInt("id");
				String prato = resultado.getString("nome");
				
				listaPratos.add(prato);
				mapaPratos.put(prato, pratoId);
			
			}
			
			String[] pratosArray = listaPratos.toArray(new String[0]);
			String pratoSelecionado = (String) JOptionPane.showInputDialog(
					null,
					"Selecione o prato: ",
					"Seleção de Prato",
					JOptionPane.QUESTION_MESSAGE,
					null,
					pratosArray,
					pratosArray[0]
			);
					
			if(pratoSelecionado != null) {
				int pratoIdSelecionado = mapaPratos.get(pratoSelecionado);
				
				if(
						JOptionPane.showConfirmDialog(
						null, 
						"Deletar Prato",
						"Deseja deletar este prato?",
						JOptionPane.YES_NO_OPTION) 
						
					== JOptionPane.YES_OPTION) {
					
					String sqldelete = "DELETE FROM pratos WHERE id = ?;";
					
					PreparedStatement cmddelete = conexao.prepareStatement(sqldelete);
					cmddelete.setInt(1, pratoIdSelecionado);
					
					cmddelete.execute();
					
					JOptionPane.showMessageDialog(null, "Registro deletado com sucesso.");
					cmddelete.close();
				}else {
					
				}
			}else {
				JOptionPane.showMessageDialog(null, "Nenhum prato selecionado.");
			}
		}
		
	}

	//System.out.println(prato.getNome() + "\n" +
	//prato.getDescricao() + "\n" +
	//prato.getPreco() + "\n +" +
	//prato.getCategoria());
