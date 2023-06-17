package cl.sebastian.indicator.vo.cmf;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *
 * @author seba
 */
public class CMFUfs {

    @JsonProperty("UFs")
    private List<CMFUf> uFs = null;

    public List<CMFUf> getuFs() {
        return uFs;
    }

    public void setuFs(List<CMFUf> uFs) {
        this.uFs = uFs;
    }
}
