package net.createyourideas.accounting.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import net.createyourideas.accounting.domain.enumeration.Ideatype;

/**
 * JHipster JDL model for Accounting
 */
@ApiModel(description = "JHipster JDL model for Accounting")
@Entity
@Table(name = "idea")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Idea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "ideatype")
    private Ideatype ideatype;

    @Column(name = "interest")
    private Float interest;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "idea")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Income> incomes = new HashSet<>();

    @OneToMany(mappedBy = "idea")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Outgoings> outgoings = new HashSet<>();

    @OneToMany(mappedBy = "idea")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Worksheet> worksheets = new HashSet<>();

    @OneToMany(mappedBy = "idea")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Idea> parents = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("ideas")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("parents")
    private Idea idea;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Idea title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Idea description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Ideatype getIdeatype() {
        return ideatype;
    }

    public Idea ideatype(Ideatype ideatype) {
        this.ideatype = ideatype;
        return this;
    }

    public void setIdeatype(Ideatype ideatype) {
        this.ideatype = ideatype;
    }

    public Float getInterest() {
        return interest;
    }

    public Idea interest(Float interest) {
        this.interest = interest;
        return this;
    }

    public void setInterest(Float interest) {
        this.interest = interest;
    }

    public Boolean isActive() {
        return active;
    }

    public Idea active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Income> getIncomes() {
        return incomes;
    }

    public Idea incomes(Set<Income> incomes) {
        this.incomes = incomes;
        return this;
    }

    public Idea addIncomes(Income income) {
        this.incomes.add(income);
        income.setIdea(this);
        return this;
    }

    public Idea removeIncomes(Income income) {
        this.incomes.remove(income);
        income.setIdea(null);
        return this;
    }

    public void setIncomes(Set<Income> incomes) {
        this.incomes = incomes;
    }

    public Set<Outgoings> getOutgoings() {
        return outgoings;
    }

    public Idea outgoings(Set<Outgoings> outgoings) {
        this.outgoings = outgoings;
        return this;
    }

    public Idea addOutgoings(Outgoings outgoings) {
        this.outgoings.add(outgoings);
        outgoings.setIdea(this);
        return this;
    }

    public Idea removeOutgoings(Outgoings outgoings) {
        this.outgoings.remove(outgoings);
        outgoings.setIdea(null);
        return this;
    }

    public void setOutgoings(Set<Outgoings> outgoings) {
        this.outgoings = outgoings;
    }

    public Set<Worksheet> getWorksheets() {
        return worksheets;
    }

    public Idea worksheets(Set<Worksheet> worksheets) {
        this.worksheets = worksheets;
        return this;
    }

    public Idea addWorksheet(Worksheet worksheet) {
        this.worksheets.add(worksheet);
        worksheet.setIdea(this);
        return this;
    }

    public Idea removeWorksheet(Worksheet worksheet) {
        this.worksheets.remove(worksheet);
        worksheet.setIdea(null);
        return this;
    }

    public void setWorksheets(Set<Worksheet> worksheets) {
        this.worksheets = worksheets;
    }

    public Set<Idea> getParents() {
        return parents;
    }

    public Idea parents(Set<Idea> ideas) {
        this.parents = ideas;
        return this;
    }

    public Idea addParent(Idea idea) {
        this.parents.add(idea);
        idea.setIdea(this);
        return this;
    }

    public Idea removeParent(Idea idea) {
        this.parents.remove(idea);
        idea.setIdea(null);
        return this;
    }

    public void setParents(Set<Idea> ideas) {
        this.parents = ideas;
    }

    public User getUser() {
        return user;
    }

    public Idea user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Idea getIdea() {
        return idea;
    }

    public Idea idea(Idea idea) {
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
        if (!(o instanceof Idea)) {
            return false;
        }
        return id != null && id.equals(((Idea) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Idea{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", ideatype='" + getIdeatype() + "'" +
            ", interest=" + getInterest() +
            ", active='" + isActive() + "'" +
            "}";
    }
}
