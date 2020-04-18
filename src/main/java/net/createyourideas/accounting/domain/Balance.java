package net.createyourideas.accounting.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Balance.
 */
@Entity
@Table(name = "balance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Balance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "daily_balance")
    private Float dailyBalance;

    @Column(name = "profit")
    private Float profit;

    @Column(name = "profit_to_spend")
    private Float profitToSpend;

    @Column(name = "net_profit")
    private Float netProfit;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JsonIgnoreProperties("balances")
    private Idea idea;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getDailyBalance() {
        return dailyBalance;
    }

    public Balance dailyBalance(Float dailyBalance) {
        this.dailyBalance = dailyBalance;
        return this;
    }

    public void setDailyBalance(Float dailyBalance) {
        this.dailyBalance = dailyBalance;
    }

    public Float getProfit() {
        return profit;
    }

    public Balance profit(Float profit) {
        this.profit = profit;
        return this;
    }

    public void setProfit(Float profit) {
        this.profit = profit;
    }

    public Float getProfitToSpend() {
        return profitToSpend;
    }

    public Balance profitToSpend(Float profitToSpend) {
        this.profitToSpend = profitToSpend;
        return this;
    }

    public void setProfitToSpend(Float profitToSpend) {
        this.profitToSpend = profitToSpend;
    }

    public Float getNetProfit() {
        return netProfit;
    }

    public Balance netProfit(Float netProfit) {
        this.netProfit = netProfit;
        return this;
    }

    public void setNetProfit(Float netProfit) {
        this.netProfit = netProfit;
    }

    public LocalDate getDate() {
        return date;
    }

    public Balance date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Idea getIdea() {
        return idea;
    }

    public Balance idea(Idea idea) {
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
        if (!(o instanceof Balance)) {
            return false;
        }
        return id != null && id.equals(((Balance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Balance{" +
            "id=" + getId() +
            ", dailyBalance=" + getDailyBalance() +
            ", profit=" + getProfit() +
            ", profitToSpend=" + getProfitToSpend() +
            ", netProfit=" + getNetProfit() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
