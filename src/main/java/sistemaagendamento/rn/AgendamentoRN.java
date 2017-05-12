package sistemaagendamento.rn;

import java.util.List;
import sistemaagendamento.model.Agendamento;

import sistemaagendamento.model.dao.AgendamentoDAO;

public class AgendamentoRN {

    private AgendamentoDAO agendamentoDAO;

    public AgendamentoRN() {
        agendamentoDAO= new AgendamentoDAO();
    }

    public void salvar(Agendamento agendamento) {
        Integer id = agendamento.getAgendamento_id();

        if (id == null) {
            agendamentoDAO.salvar(agendamento);
        } else {
            agendamentoDAO.atualizar(agendamento);
        }
    }

    public void excluir(Agendamento agendamento) {
        agendamentoDAO.excluir(agendamento);
    }

    public Agendamento localizar(int id) {
        return agendamentoDAO.localizar(id);
    }

    public List<Agendamento> listar() {
        return agendamentoDAO.listar();
    }
    
    public Agendamento getLast() {
        return agendamentoDAO.getLast();
    }

}
