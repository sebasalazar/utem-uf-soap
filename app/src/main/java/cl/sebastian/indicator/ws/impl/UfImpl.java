package cl.sebastian.indicator.ws.impl;

import cl.sebastian.indicator.data.manager.UfManager;
import cl.sebastian.indicator.data.manager.UserManager;
import cl.sebastian.indicator.data.model.User;
import cl.sebastian.indicator.exception.IndicatorException;
import cl.sebastian.indicator.service.cmfService;
import cl.sebastian.indicator.vo.CredentialVO;
import cl.sebastian.indicator.vo.UfVO;
import cl.sebastian.indicator.ws.Uf;
import jakarta.jws.WebService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author seba
 */
@Service
@WebService(endpointInterface = "cl.sebastian.indicator.ws.Uf")
public class UfImpl implements Uf, Serializable {

    private static final long serialVersionUID = 2L;

    @Autowired
    private transient UserManager userManager;

    @Autowired
    private transient cmfService cmfService;

    @Autowired
    private transient UfManager ufManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(UfImpl.class);

    @Override
    public UfVO consultar(CredentialVO credential, String dateStr) {
        LOGGER.info("=== Se está consultando la UF ===");

        User user = userManager.getUser(credential.getUsername(), credential.getPassword());
        if (user == null) {
            LOGGER.error("Credenciales inválidas");
            throw new IndicatorException("Credenciales inválidas");
        }

        final LocalDate localDate = LocalDate.parse(dateStr);
        if (localDate == null) {
            LOGGER.error("El parámetro Fecha no puede ser nulo");
            throw new IndicatorException("No ha sido posible consulta la uf sin una fecha válida");
        }

        BigDecimal valor;
        cl.sebastian.indicator.data.model.Uf uf = ufManager.getUf(localDate);
        if (uf != null) {
            valor = uf.getValue();
        } else {
            // Acá sabemos que la fecha es válida, falta hacer la consulta contra la CMF
            valor = cmfService.getUfValue(localDate);
            if (valor == null) {
                LOGGER.error("No hay valorización de la UF");
                throw new IndicatorException("No hay un valor de UF para la fecha consultada");
            } else {
                ufManager.create(localDate, valor);
            }
        }

        // Si llegase a este punto, tendría tanto la fecha como el valor
        return new UfVO(localDate, valor);
    }
}
