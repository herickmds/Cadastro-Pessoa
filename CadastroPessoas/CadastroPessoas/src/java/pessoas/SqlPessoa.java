/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pessoas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import conectionMySql.conection;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Marsal
 */
public class SqlPessoa {

    private static final String SCRIPT_BUSCAR
            = "select p.id_pessoa, p.nome_pessoa, p.cpf, p.email"
            + "  from pessoas p "
            + " where p.id_pessoa = ?";
    private static final String SCRIPT_INSERIR
            = "insert into pessoas"
            + " (id_pessoa, nome_pessoa, cpf, email)"
            + " values (?, ?, ?, ?)";
    private static final String SCRIPT_ALTERAR
            = "update pessoas "
            + "   set nome_pessoa = ?, "
            + "       cpf = ?, "
            + "       email = ? "
            + " where id_pessoa = ?";
    private static final String SCRIPT_EXCLUIR
            = "delete from pessoas where id_pessoa = ?";
    private static final String SCRIPT_GET_LISTA
            = "select p.id_pessoa, p.nome_pessoa, p.cpf, p.email"
            + "  from pessoas p "
            + " order by p.id_pessoa";

    public static Pessoa buscar(int id) {
        try {
            Connection con = conection.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_BUSCAR);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int id_pessoa = rs.getInt(1);
                String nome = rs.getString(2);
                String cpf = rs.getString(3);
                String email = rs.getString(4);
                Pessoa pessoaBuscada = new Pessoa(id_pessoa, nome, cpf, email);
                return pessoaBuscada;

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao buscar registro de pessoa.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public int geraIdPessoa() {
        ArrayList<Pessoa> pessoas = getLista();
        int id = 0;
        if (pessoas.size() == 0) {
            id += 1;
        } else {
            int cont = pessoas.size();
            id = pessoas.get(cont - 1).getId();
            id += 1;
            return id;
        }
        return 1;
    }

    public boolean inserir(Pessoa p) {
        try {
            Connection con = conection.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_INSERIR);

            ps.setInt(1, geraIdPessoa());
            ps.setString(2, p.getNome());
            ps.setString(3, p.getCpf());
            ps.setString(4, p.getEmail());

            int resultado = ps.executeUpdate();

            return resultado == 1;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao inserir registro de pessoa." + " " + e.getLocalizedMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);

        }
        return false;
    }

    public boolean alterar(Pessoa p) {
        try {
            Connection con = conection.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_ALTERAR);

            ps.setString(1, p.getNome());
            ps.setString(2, p.getCpf());
            ps.setString(3, p.getEmail());
            ps.setInt(4, p.getId());

            int resultado = ps.executeUpdate();

            return resultado == 1;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao alterar registro de pessoa." + e.getLocalizedMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public boolean excluir(Pessoa p) {
        try {
            Connection con = conection.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_EXCLUIR);

            ps.setLong(1, p.getId());

            int resultado = ps.executeUpdate();
            return resultado == 1;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao excluir registro de pessoa.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private static ArrayList<Pessoa> listaPessoas = new ArrayList<>();

    public static ArrayList<Pessoa> getLista() {

        if (listaPessoas == null) {
            listaPessoas = new ArrayList<>();
        } else {
            listaPessoas.clear();
        }

        try {
            Connection con = conection.getConnection();
            PreparedStatement ps = con.prepareStatement(SCRIPT_GET_LISTA);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id_pessoa = rs.getInt(1);
                String nome = rs.getString(2);
                String cpf = rs.getString(3);
                String email = rs.getString(4);
                Pessoa pessoaBuscada = new Pessoa(id_pessoa, nome, cpf, email);
                listaPessoas.add(pessoaBuscada);

            }

            return listaPessoas;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao obter registros de pessoas.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return listaPessoas;
    }

    public static boolean validaCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000")
                || CPF.equals("11111111111")
                || CPF.equals("22222222222") || CPF.equals("33333333333")
                || CPF.equals("44444444444") || CPF.equals("55555555555")
                || CPF.equals("66666666666") || CPF.equals("77777777777")
                || CPF.equals("88888888888") || CPF.equals("99999999999")
                || (CPF.length() != 11)) {
            return (false);
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0         
                // (48 eh a posicao de '0' na tabela ASCII)         
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig10 = '0';
            } else {
                dig10 = (char) (r + 48); // converte no respectivo caractere numerico
            }
            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig11 = '0';
            } else {
                dig11 = (char) (r + 48);
            }

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
                return (true);
            } else {
                return (false);
            }
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    public static String imprimeCPF(String CPF) {
        return (CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "."
                + CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
    }

    public static boolean validarEmail(String email) {
        boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }
        return isEmailIdValid;
    }
}
