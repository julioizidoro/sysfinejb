package br.com.financemate.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "planocontatipo")
public class Planocontatipo implements Serializable{
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idplanocontatipo")
    private Integer idplanocontatipo;
    @JoinColumn(name = "tipoplanocontas_idtipoplanocontas", referencedColumnName = "idtipoplanocontas")
    @ManyToOne(optional = false)
    private Tipoplanocontas tipoplanocontas;
    @JoinColumn(name = "planoContas_idplanoContas", referencedColumnName = "idplanoContas")
    @ManyToOne(optional = false)
    private Planocontas planocontas;
    
	public Planocontatipo() {
		
	}

	

	public Integer getIdplanocontatipo() {
		return idplanocontatipo;
	}



	public void setIdplanocontatipo(Integer idplanocontatipo) {
		this.idplanocontatipo = idplanocontatipo;
	}



	public Tipoplanocontas getTipoplanocontas() {
		return tipoplanocontas;
	}

	public void setTipoplanocontas(Tipoplanocontas tipoplanocontas) {
		this.tipoplanocontas = tipoplanocontas;
	}

	public Planocontas getPlanocontas() {
		return planocontas;
	}

	public void setPlanocontas(Planocontas planocontas) {
		this.planocontas = planocontas;
	}

	
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idplanocontatipo!= null ? idplanocontatipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // 
        if (!(object instanceof Planocontatipo)) {
            return false;
        }
        Planocontatipo other = (Planocontatipo) object;
        if ((this.idplanocontatipo == null && other.idplanocontatipo != null) || (this.idplanocontatipo != null && !this.idplanocontatipo.equals(other.idplanocontatipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getPlanocontas().getDescricao();
    }
	
	
    
    

}
