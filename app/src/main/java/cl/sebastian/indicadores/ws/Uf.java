package cl.sebastian.indicadores.ws;

import cl.sebastian.indicadores.vo.UfVO;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

/**
 *
 * @author seba
 */
@WebService
public interface Uf {

    /**
     *
     * @param fecha Fecha de consulta
     * @return Un objeto con el valor de la UF
     */
    public UfVO consultar(@WebParam(name = "fecha") String fecha);
}
