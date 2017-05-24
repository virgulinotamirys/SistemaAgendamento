package sistemaagendamento.model.dao;

import sistemaagendamento.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistemaagendamento.model.Agendamento;
import sistemaagendamento.model.OptimizeTable;

public class AgendamentoDAO {

    public AgendamentoDAO() {
    }

    public void salvar(Agendamento agendamento) {
        String sql = "CALL insert_agendamento(?, ?, ?)";
//        String sql = "INSERT INTO agendamento (diasemana, horaInicial, horaFinal) VALUES (?, ?, ?)";;
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, agendamento.getDiasemana());
            statement.setString(2, agendamento.getHoraInicial());
            statement.setString(3, agendamento.getHoraFinal());
            
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
        String sql = "DELETE FROM agendamento WHERE agendamento_id = ?";
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, agendamento.getAgendamento_id());

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
        String sql = "UPDATE agendamento SET diasemana = ?, horaInicial = ?, horaFinal = ? WHERE agendamento_id = ?";
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, agendamento.getDiasemana());
            statement.setString(2, agendamento.getHoraInicial());
            statement.setString(3, agendamento.getHoraFinal());
            statement.setInt(4, agendamento.getAgendamento_id().intValue());

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
        String sql = "SELECT * FROM agendamento WHERE agendamento_id = ?";
        Connection connection = DBConnection.getConnection();

        Agendamento agendamento = null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.first()) {
                agendamento = new Agendamento();

                agendamento.setAgendamento_id(resultSet.getInt("agendamento_id"));
                agendamento.setDiasemana(resultSet.getString("diasemana"));
                agendamento.setHoraInicial(resultSet.getString("horaInicial"));
                agendamento.setHoraFinal(resultSet.getString("horaFinal"));
                
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
    
    public Agendamento getLast() {
        String sql = "SELECT * FROM agendamento ORDER BY agendamento_id DESC LIMIT 1";
        Connection connection = DBConnection.getConnection();

        Agendamento agendamento = null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.first()) {
                agendamento = new Agendamento();

                agendamento.setAgendamento_id(resultSet.getInt("agendamento_id"));
                agendamento.setDiasemana(resultSet.getString("diasemana"));
                agendamento.setHoraInicial(resultSet.getString("horaInicial"));
                agendamento.setHoraFinal(resultSet.getString("horaFinal"));

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
        String sql = "SELECT * FROM vw_agendamento";
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

                agendamento.setAgendamento_id(Integer.valueOf(resultSet.getInt("agendamento_id")));
                agendamento.setDiasemana(resultSet.getString("diasemana"));
                agendamento.setHoraInicial(resultSet.getString("horaInicial"));
                agendamento.setHoraFinal(resultSet.getString("horaFinal"));

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
     public List<OptimizeTable> optimize() {
        String sql = "OPTIMIZE TABLE curso";
        Connection connection = DBConnection.getConnection();

        List<OptimizeTable> list = new ArrayList<OptimizeTable>();
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                OptimizeTable optimizeTable = new OptimizeTable();
                
                optimizeTable.setTable(resultSet.getString("table"));
                optimizeTable.setOp(resultSet.getString("Op"));
                optimizeTable.setMsgType(resultSet.getString("Msg_type"));
                optimizeTable.setMsgText(resultSet.getString("Msg_text"));
                
                list.add(optimizeTable);
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

        return list;
    }
    
}
