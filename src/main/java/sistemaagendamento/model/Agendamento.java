package sistemaagendamento.model;

public class Agendamento {

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
    
    

    @Override
    public String toString() {
        return this.diasemana + this.horaInicial + this.horaFinal;
    }

}
