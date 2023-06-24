package cl.sebastian.indicator.service;

import cl.sebastian.indicator.utils.OkHttpUtils;
import cl.sebastian.indicator.vo.cmf.CMFUfs;
import jakarta.annotation.PostConstruct;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author seba
 */
@Service
public class cmfService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Value("${cmf.api.key}")
    private String apiKey;

    private OkHttpClient clienteWeb = null;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(cmfService.class);

    @PostConstruct
    public void constructor() {
        this.clienteWeb = OkHttpUtils.makeClient(13, 17);
    }

    /**
     *
     * @param queryDate Fecha de consulta
     * @return Un bigDecimal con el valor de la UF en el día o nulo en caso de
     * cualquier error.
     */
    public BigDecimal getUfValue(LocalDate queryDate) {
        BigDecimal val = null;
        try {
            if (queryDate != null) {
                final String url = String.format("http://api.cmfchile.cl/api-sbifv3/recursos_api/uf/%d/%d/dias/%d?apikey=%s&formato=json",
                        queryDate.getYear(), queryDate.getMonthValue(), queryDate.getDayOfMonth(), apiKey);

                LOGGER.info("URL de consulta: '{}'", url);
                if (UrlValidator.getInstance().isValid(url)) {

                    Request peticion = new Request.Builder()
                            .url(url)
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Accept", "application/json")
                            .build();

                    Call llamadaAlApi = clienteWeb.newCall(peticion);
                    try (Response respuesta = llamadaAlApi.execute()) {
                        if (respuesta.isSuccessful()) {
                            try (ResponseBody cuerpoRespuesta = respuesta.body()) {
                                
                                    final String json = StringUtils.trimToEmpty(cuerpoRespuesta.string());
                                    LOGGER.info("Respuesta: '{}'", json);
                                    
                                    
                                    CMFUfs ufs = OkHttpUtils.MAPPER.readValue(json, CMFUfs.class);
                                    String monto = ufs.getuFs().get(0).getValor();
                                    LOGGER.info("Valor de la UF desde el servicio: {}", monto); // 30.287,15
                                    
                                    final String montoSinPunto = StringUtils.remove(monto, "."); // 30287,15
                                    LOGGER.info("Valor de la UF sin punto: {}", montoSinPunto);
                                    
                                    final String montoSinComa = StringUtils.remove(montoSinPunto, ","); // 3028715
                                    LOGGER.info("Valor de la UF sin punto ni coma: {}", montoSinComa);
                                    
                                    int montoAumentado = NumberUtils.toInt(montoSinComa); // 3028715 -> Entero
                                    LOGGER.info("Valor de la UF como entero: {}", montoAumentado);
                                    
                                    double montoDecimal = montoAumentado / 100.0;
                                    LOGGER.info("Valor de la UF como decimal: {}", montoDecimal);
                                    
                                    val = BigDecimal.valueOf(montoDecimal).setScale(2, RoundingMode.HALF_UP);
                                
                            }
                        }
                    }
                } else {
                    LOGGER.error("La url '{}' NO es válida", url);
                }
            }
        } catch (Exception e) {
            val = null;
            LOGGER.error("Error al obtener la UF desde la CMF: {}", e.getMessage());
            LOGGER.error("Error al obtener la UF desde la CMF: {}", e.getMessage(), e);
        }
        return val;
    }
}
