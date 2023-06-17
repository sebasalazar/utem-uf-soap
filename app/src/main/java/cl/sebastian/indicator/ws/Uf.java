package cl.sebastian.indicator.ws;

import cl.sebastian.indicator.vo.CredentialVO;
import cl.sebastian.indicator.vo.UfVO;
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
     * @param credential Objeto con los datos de credencial
     * @param dateStr Fecha de consulta
     * @return Un objeto con el valor de la UF
     */
    public UfVO consultar(@WebParam(name = "credential") CredentialVO credential,
            @WebParam(name = "fecha") String dateStr);
}
