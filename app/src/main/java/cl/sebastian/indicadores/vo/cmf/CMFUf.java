package cl.sebastian.indicadores.vo.cmf;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author seba
 */
public class CMFUf {

    @JsonProperty("Valor")
    private String valor;
    @JsonProperty("Fecha")
    private String fecha;

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
