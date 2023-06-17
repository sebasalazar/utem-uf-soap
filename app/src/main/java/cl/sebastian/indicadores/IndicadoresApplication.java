package cl.sebastian.indicadores;

import cl.sebastian.indicadores.ws.impl.UfImpl;
import jakarta.xml.ws.Endpoint;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IndicadoresApplication {

    @Autowired
    private SpringBus bus;

    @Autowired
    private UfImpl ufImpl;

    public static void main(String[] args) {
        SpringApplication.run(IndicadoresApplication.class, args);
    }

    @Bean
    public Endpoint ufEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, ufImpl);
        endpoint.publish("/uf");
        return endpoint;
    }
}
