package cl.sebastian.indicator.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "access", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_fk", "uf_fk"})})
public class Access extends Seba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    private Long id = null;

    @JoinColumn(name = "user_fk", referencedColumnName = "pk", nullable = false)
    @ManyToOne
    private User user = null;

    @JoinColumn(name = "uf_fk", referencedColumnName = "pk", nullable = false)
    @ManyToOne
    private Uf uf = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, unique = true)
    private Date created = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Uf getUf() {
        return uf;
    }

    public void setUf(Uf uf) {
        this.uf = uf;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final Access other = (Access) obj;
        return Objects.equals(this.id, other.id);
    }
}
