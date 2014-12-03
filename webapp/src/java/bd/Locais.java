/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bd;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author nuno
 */
@Entity
@Table(name = "locais")
@NamedQueries({
    @NamedQuery(name = "Locais.findAll", query = "SELECT l FROM Locais l"),
    @NamedQuery(name = "Locais.findById", query = "SELECT l FROM Locais l WHERE l.id = :id"),
    @NamedQuery(name = "Locais.findByNome", query = "SELECT l FROM Locais l WHERE l.nome = :nome"),
    @NamedQuery(name = "Locais.findByMorada", query = "SELECT l FROM Locais l WHERE l.morada = :morada"),
    @NamedQuery(name = "Locais.findByLatitude", query = "SELECT l FROM Locais l WHERE l.latitude = :latitude"),
    @NamedQuery(name = "Locais.findByLongitude", query = "SELECT l FROM Locais l WHERE l.longitude = :longitude")})
public class Locais implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "morada")
    private String morada;
    @Basic(optional = false)
    @Column(name = "latitude")
    private float latitude;
    @Basic(optional = false)
    @Column(name = "longitude")
    private float longitude;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "locais")
    private Collection<Eventos> eventosCollection;

    public Locais() {
    }

    public Locais(Integer id) {
        this.id = id;
    }

    public Locais(Integer id, String nome, String morada, float latitude, float longitude) {
        this.id = id;
        this.nome = nome;
        this.morada = morada;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Collection<Eventos> getEventosCollection() {
        return eventosCollection;
    }

    public void setEventosCollection(Collection<Eventos> eventosCollection) {
        this.eventosCollection = eventosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Locais)) {
            return false;
        }
        Locais other = (Locais) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nome;
    }

}
