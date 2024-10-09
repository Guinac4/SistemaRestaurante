package restaurante.controller;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import restaurante.jdbc.ConnectionFactory;

public class Usuarios {
	public static String criptografarSenha(String senha) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(senha.getBytes());
			StringBuilder hexString = new StringBuilder();
			for(byte b: hash) {
				hexString.append(String.format("%02x", b));
			}
			return hexString.toString();		
		} catch (Exception e) {
			throw new RuntimeException(e);	
		}	
	}
	
	
	public static boolean loginUsuarios () throws SQLException{
		//Import the classes of connection to the database
		Connection conexao = ConnectionFactory.createConnection();
		
		String username = JOptionPane.showInputDialog("Nome de Usuario");
		String senha = JOptionPane.showInputDialog("Senha");
		
		String senhaHash = criptografarSenha(senha);
		
		String sql = "select * from usuarios where username = ? and senha_hash = ?;";
		
		PreparedStatement cmd = conexao.prepareStatement(sql);
		
		cmd.setString(1, username);
		cmd.setString(2, senhaHash);
		
		ResultSet rs = cmd.executeQuery();
		
		if(rs.next()) {
			JOptionPane.showMessageDialog(null, "Login Efetuado com Sucesso. Bem Vindo " + username);
			return true;	
			
		} else {
			JOptionPane.showMessageDialog(null, "Usuario ou Senha Incorretos!");
			return false;
		}	
	}	
}
