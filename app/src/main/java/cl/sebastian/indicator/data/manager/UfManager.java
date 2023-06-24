package cl.sebastian.indicator.data.manager;

import cl.sebastian.indicator.data.model.Uf;
import cl.sebastian.indicator.data.repository.UfRepository;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UfManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private transient UfRepository ufRepository;

    public Uf getUf(final LocalDate localDate) {
        Uf uf = null;
        if (localDate != null) {
            uf = ufRepository.findByDate(localDate);
        }
        return uf;
    }

    @Transactional
    public void create(final LocalDate localDate, BigDecimal value) {
        if (localDate != null && value != null) {
            Uf uf = ufRepository.findByDate(localDate);
            if (uf == null) {
                uf = new Uf();
                uf.setDate(localDate);
            }
            uf.setValue(value);
            ufRepository.saveAndFlush(uf);
        }
    }
}
