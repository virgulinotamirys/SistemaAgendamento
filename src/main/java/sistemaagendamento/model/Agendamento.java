package sistemaagendamento.model;

import java.io.Serializable;

public class Agendamento implements Serializable{
    
    private static final long serialVersionUID = -3682557083920024750L;

    private int agendamento_id;
    private String diasemana;
    private String horaInicial;
    private String horaFinal;

    public int getAgendamento_id() {
        return agendamento_id;
    }

    public void setAgendamento_id(int agendamento_id) {
        this.agendamento_id = agendamento_id;
    }

    public String getDiasemana() {
        return diasemana;
    }

    public void setDiasemana(String diasemana) {
        this.diasemana = diasemana;
    }

    public String getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(String horaInicial) {
        this.horaInicial = horaInicial;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }
   
}
