package cl.sebastian.indicadores.ws.impl;

import cl.sebastian.indicadores.exception.IndicatorException;
import cl.sebastian.indicadores.service.ServicioCmf;
import cl.sebastian.indicadores.vo.UfVO;
import cl.sebastian.indicadores.ws.Uf;
import jakarta.jws.WebService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author seba
 */
@Service
@WebService(endpointInterface = "cl.sebastian.indicadores.ws.Uf")
public class UfImpl implements Uf, Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private transient ServicioCmf servicioCmf;

    private static final Logger LOGGER = LoggerFactory.getLogger(UfImpl.class);

    @Override
    public UfVO consultar(String fecha) {
        LOGGER.info("=== Se está consultando la UF ===");
        LocalDate fechaLocal = LocalDate.now();
        if (StringUtils.isNotBlank(fecha)) {
            fechaLocal = LocalDate.parse(fecha);
        }

        if (fechaLocal == null) {
            LOGGER.error("El parámetro Fecha no puede ser nulo");
            throw new IndicatorException("No ha sido posible consulta la uf sin una fecha válida");
        }

        // Acá sabemos que la fecha es válida, falta hacer la consulta contra la CMF
        BigDecimal valor = servicioCmf.obtenerValorUf(fechaLocal);
        if (valor == null) {
            LOGGER.error("No hay valorización de la UF");
            throw new IndicatorException("No hay un valor de UF para la fecha consultada");
        }

        // Si llegase a este punto, tendría tanto la fecha como el valor
        return new UfVO(fechaLocal, valor);
    }
}
