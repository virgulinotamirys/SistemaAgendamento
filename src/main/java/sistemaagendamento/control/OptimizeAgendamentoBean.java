package sistemaagendamento.control;

import sistemaagendamento.model.OptimizeTable;
import sistemaagendamento.model.dao.AgendamentoDAO;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class OptimizeAgendamentoBean {

    private List<OptimizeTable> list;
    private boolean optimized;

    public void optimize() {
        if (list == null) {
            AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
            list = agendamentoDAO.optimize();
            optimized = true;
        }
    }

    public List<OptimizeTable> getList() {
        return list;
    }

}
