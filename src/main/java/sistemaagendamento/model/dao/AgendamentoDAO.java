package sistemaagendamento.model.dao;

import sistemaagendamento.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistemaagendamento.model.Agendamento;

public class AgendamentoDAO {

    public AgendamentoDAO() {
    }

    public void salvar(Agendamento agendamento) {
        String sql = "INSERT INTO agendamento (descricao) VALUES (?)";
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, agendamento.getDescricao());

            statement.execute();
            System.out.println("Agendamento salvo(a) com sucesso");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void excluir(Agendamento agendamento) {
        String sql = "DELETE FROM agendamento WHERE id = ?";
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, agendamento.getId());

            statement.execute();
            System.out.println("Agendamento deletado(a) com sucesso");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void atualizar(Agendamento agendamento) {
        String sql = "UPDATE agendamento SET id = ?, descricao = ? WHERE id = ?";
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, agendamento.getDescricao());
            statement.setInt(2, agendamento.getId());

            statement.execute();
            System.out.println("Agendamento atualizado(a) com sucesso");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Agendamento localizar(int id) {
        String sql = "SELECT * FROM agendamento WHERE id = ?";
        Connection connection = DBConnection.getConnection();

        Agendamento agendamento = null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.first()) {
                agendamento = new Agendamento();

                agendamento.setId(resultSet.getInt("id"));
                agendamento.setDescricao(resultSet.getString("descricao"));

                System.out.println("Agendamento localizado(a) com sucesso");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return agendamento;

    }

    public List<Agendamento> listar() {
        String sql = "SELECT * FROM agendamento";
        Connection connection = DBConnection.getConnection();

        List<Agendamento> agendamentoList = null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                if (agendamentoList == null) {
                    agendamentoList = new ArrayList<Agendamento>();
                }

                Agendamento agendamento = new Agendamento();

                agendamento.setId(resultSet.getInt("id"));
                agendamento.setDescricao(resultSet.getString("descricao"));

                agendamentoList.add(agendamento);

                System.out.println("Registros de agendamento localizados com sucesso");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return agendamentoList;
    }
}
