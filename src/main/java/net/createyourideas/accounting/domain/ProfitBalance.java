package net.createyourideas.accounting.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ProfitBalance.
 */
@Entity
@Table(name = "profit_balance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProfitBalance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profit")
    private Float profit;

    @Column(name = "profit_to_spend")
    private Float profitToSpend;

    @Column(name = "net_profit")
    private Float netProfit;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JsonIgnoreProperties("profitBalances")
    private Idea idea;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getProfit() {
        return profit;
    }

    public ProfitBalance profit(Float profit) {
        this.profit = profit;
        return this;
    }

    public void setProfit(Float profit) {
        this.profit = profit;
    }

    public Float getProfitToSpend() {
        return profitToSpend;
    }

    public ProfitBalance profitToSpend(Float profitToSpend) {
        this.profitToSpend = profitToSpend;
        return this;
    }

    public void setProfitToSpend(Float profitToSpend) {
        this.profitToSpend = profitToSpend;
    }

    public Float getNetProfit() {
        return netProfit;
    }

    public ProfitBalance netProfit(Float netProfit) {
        this.netProfit = netProfit;
        return this;
    }

    public void setNetProfit(Float netProfit) {
        this.netProfit = netProfit;
    }

    public LocalDate getDate() {
        return date;
    }

    public ProfitBalance date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Idea getIdea() {
        return idea;
    }

    public ProfitBalance idea(Idea idea) {
        this.idea = idea;
        return this;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfitBalance)) {
            return false;
        }
        return id != null && id.equals(((ProfitBalance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProfitBalance{" +
            "id=" + getId() +
            ", profit=" + getProfit() +
            ", profitToSpend=" + getProfitToSpend() +
            ", netProfit=" + getNetProfit() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
