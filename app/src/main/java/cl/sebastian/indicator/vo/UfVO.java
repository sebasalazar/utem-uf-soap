package cl.sebastian.indicator.vo;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author seba
 */
@XmlRootElement
public class UfVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date fecha = null;
    private BigDecimal valor = null;

    public UfVO() {
        this.fecha = new Date();
        this.valor = BigDecimal.ZERO;
    }

    public UfVO(LocalDate fecha, BigDecimal valor) {
        this.fecha = Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.valor = valor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.fecha);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UfVO other = (UfVO) obj;
        return Objects.equals(this.fecha, other.fecha);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
