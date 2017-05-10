package sistemaagendamento.control;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import sistemaagendamento.model.Agendamento;
import sistemaagendamento.rn.AgendamentoRN;

@ManagedBean
@RequestScoped
public class AgendamentoBean {

    private Agendamento agendamento = new Agendamento();
    private List<Agendamento> lista;

    public String novo() {
        agendamento = new Agendamento();
        return "/agendamento/new";
    }

    public String editar() {
        return "/agendamento/edit";
    }

    public String exibir() {
        return "/agendamento/show";
    }

    public String salvar() {
        FacesContext context = FacesContext.getCurrentInstance();

        AgendamentoRN agendamentoRN = new AgendamentoRN();
        agendamentoRN.salvar(agendamento);

        return "index";
    }

    public String excluir() {
        FacesContext context = FacesContext.getCurrentInstance();

        AgendamentoRN agendamentoRN = new AgendamentoRN();
        agendamentoRN.excluir(agendamento);
        lista = null;

        return null;
    }

    public List<Agendamento> getLista() {
        if (lista == null) {
            AgendamentoRN agendamentoRN = new AgendamentoRN();
            lista = agendamentoRN.listar();
        }

        return lista;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }

}
