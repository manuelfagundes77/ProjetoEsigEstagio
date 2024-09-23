package com.teste;

import java.sql.Connection;
import java.sql.SQLException;

public class TestarConexao {
    public static void main(String[] args) {
        Connection conn = null;
        conn = Conectar.getConnection(); // Usa o método de Conectar.java
		if (conn != null) {
		    System.out.println("Conexão bem-sucedida!");
		} else {
		    System.out.println("Conexão falhou.");
		}
    }
}

