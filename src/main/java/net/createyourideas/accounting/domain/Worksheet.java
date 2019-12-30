package net.createyourideas.accounting.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Duration;

/**
 * A Worksheet.
 */
@Entity
@Table(name = "worksheet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Worksheet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jobtitle", nullable = false)
    private String jobtitle;

    
    @Lob
    @Column(name = "jobdescription", nullable = false)
    private String jobdescription;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "cost_hour", nullable = false)
    private Float costHour;

    @NotNull
    @Column(name = "hours", nullable = false)
    private Duration hours;

    @NotNull
    @Column(name = "total", nullable = false)
    private Float total;

    @ManyToOne
    @JsonIgnoreProperties("worksheets")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("worksheets")
    private Idea idea;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public Worksheet jobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
        return this;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getJobdescription() {
        return jobdescription;
    }

    public Worksheet jobdescription(String jobdescription) {
        this.jobdescription = jobdescription;
        return this;
    }

    public void setJobdescription(String jobdescription) {
        this.jobdescription = jobdescription;
    }

    public LocalDate getDate() {
        return date;
    }

    public Worksheet date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getCostHour() {
        return costHour;
    }

    public Worksheet costHour(Float costHour) {
        this.costHour = costHour;
        return this;
    }

    public void setCostHour(Float costHour) {
        this.costHour = costHour;
    }

    public Duration getHours() {
        return hours;
    }

    public Worksheet hours(Duration hours) {
        this.hours = hours;
        return this;
    }

    public void setHours(Duration hours) {
        this.hours = hours;
    }

    public Float getTotal() {
        return total;
    }

    public Worksheet total(Float total) {
        this.total = total;
        return this;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public User getUser() {
        return user;
    }

    public Worksheet user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Idea getIdea() {
        return idea;
    }

    public Worksheet idea(Idea idea) {
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
        if (!(o instanceof Worksheet)) {
            return false;
        }
        return id != null && id.equals(((Worksheet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Worksheet{" +
            "id=" + getId() +
            ", jobtitle='" + getJobtitle() + "'" +
            ", jobdescription='" + getJobdescription() + "'" +
            ", date='" + getDate() + "'" +
            ", costHour=" + getCostHour() +
            ", hours='" + getHours() + "'" +
            ", total=" + getTotal() +
            "}";
    }
}
