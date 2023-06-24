package cl.sebastian.indicator.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sebastián Salazar Molina.
 */
public class OkHttpUtils implements Serializable {

    private static final long serialVersionUID = 3L;

    /**
     * Cantidad máxima de clientes HTTP
     */
    private static final Integer MAX_REQUESTS = 256;

    /**
     * Cantidad máxima de peticiones para un mismo HOST
     */
    private static final Integer MAX_REQUESTS_PER_HOST = 128;

    /**
     * Usamos un userAgent específico para que sea fácil reconocer las
     * peticiones
     */
    public static final String USER_AGENT = "SEBA/17.11.13";

    /**
     * Tipo MIME para JSON
     */
    public static final String JSON_MIME = "application/json; charset=utf-8";

    /**
     * Media type usada por Okhttp
     */
    public static final MediaType JSON_MEDIA_TYPE = MediaType.parse(JSON_MIME);

    private static final Logger LOGGER = LoggerFactory.getLogger(OkHttpUtils.class);

    private OkHttpUtils() {
        throw new IllegalStateException();
    }

    public static class OkHeaders {

        /**
         * Cabecera de JWT
         */
        public static final String JWT = "jwt";

        /**
         * Cabecera de agente de usuario
         */
        public static final String USER_AGENT = "User-Agent";

        /**
         * Cabecera de autorización.
         */
        public static final String AUTHORIZATION = "Authorization";

        /**
         * Cabecera de mimes aceptados.
         */
        public static final String ACCEPT = "Accept";

        /**
         * Cabecera para el tipo de contenido
         */
        public static final String CONTENT_TYPE = "Content-Type";

        /**
         * Cabecera de Cookies
         */
        public static final String COOKIE = "Cookie";

    }

    public static final ObjectMapper MAPPER = new ObjectMapper();
    
    
    /**
     *
     * @param connectTimeout tiempo de expiración para conectarse en segundos.
     * @param readTimeout tiempo de espera en lectura medido en segundos.
     * @return Un cliente http
     */
    public static OkHttpClient makeClient(final long connectTimeout, final long readTimeout) {
        return makeClient(connectTimeout, readTimeout, LOGGER.isDebugEnabled());
    }

    /**
     *
     * @param connectTimeout tiempo de expiración para conectarse en segundos.
     * @param readTimeout tiempo de espera en lectura medido en segundos.
     * @param debug Indica si se debe o no loguear las peticiones
     * @return Un cliente http
     */
    public static OkHttpClient makeClient(final long connectTimeout, final long readTimeout, boolean debug) {
        /**
         * Pool de conexiones
         */
        ConnectionPool connectionPool = new ConnectionPool(MAX_REQUESTS_PER_HOST, (17 * readTimeout), TimeUnit.SECONDS);
        LOGGER.trace("Pool de conexiones {} hilos posibles", MAX_REQUESTS_PER_HOST);

        /**
         * Controlodor de peticiones por HOST
         */
        Dispatcher dispatcher = new Dispatcher(Executors.newFixedThreadPool(MAX_REQUESTS_PER_HOST));
        dispatcher.setMaxRequests(MAX_REQUESTS);
        dispatcher.setMaxRequestsPerHost(MAX_REQUESTS_PER_HOST);
        LOGGER.trace("Máximo de conexiones {} hilos posibles", MAX_REQUESTS);

        /**
         * Interceptor del logger
         */
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(LOGGER::info);

        if (debug) {
            loggingInterceptor.setLevel(Level.BODY);
        } else {
            loggingInterceptor.setLevel(Level.BASIC);
        }
        loggingInterceptor.redactHeader(OkHeaders.AUTHORIZATION);
        loggingInterceptor.redactHeader(OkHeaders.COOKIE);
        loggingInterceptor.redactHeader(OkHeaders.JWT);

        return new OkHttpClient.Builder()
                .connectionPool(connectionPool)
                .dispatcher(dispatcher)
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(loggingInterceptor)
                .build();
    }
}
