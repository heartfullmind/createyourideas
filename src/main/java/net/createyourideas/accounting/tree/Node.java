package net.createyourideas.accounting.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.createyourideas.accounting.domain.Balance;
import net.createyourideas.accounting.domain.Idea;

public class Node {
    private String id; // Current node id
    private String parentId; // Parent node id

    private String title;
    private Float interest;
    private Float distribution;
    private Float investment;
    private Float profit;
    private String type;
    private String logo;
    private String logoContentType;
    private String description;
    private Boolean active;
    private Float dailyBalance;
    private Float profitToSpend;
    private Float netProfit;

    private Idea idea;

    private Node parent;
    private List<Node> children;

    public Node() {
        super();
        this.children = new ArrayList<>();
    }

    public Node(String childId, String parentId, String title, Float interest, Float distribution, Float investment,
            String type, String description, String logo, Boolean active, String logoContentType) {
        this.id = childId;
        this.parentId = parentId;
        this.title = title;
        this.interest = interest;
        this.distribution = distribution;
        this.investment = investment;
        this.children = new ArrayList<>();
        this.type = type;
        this.logo = logo;
        this.description = description;
        this.active = active;
        this.logoContentType = logoContentType;
    }

    public Node(String id, String parentId, Idea idea) {
        this.id = id;
        this.parentId = parentId;
        this.idea = idea;
        this.children = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getInterest() {
        return interest;
    }

    public void setInterest(Float interest) {
        this.interest = interest;
    }

    public Float getDistribution() {
        return this.distribution;
    }

    public void setDistribution(Float distribution) {
        this.distribution = distribution;
    }

    public Float getInvestment() {
        return this.investment;
    }

    public void setInvestment(Float investment) {
        this.investment = investment;
    }

    public Float getProfit() {
        return profit;
    }

    public void setProfit(Float profit) {
        this.profit = profit;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Float getDailyBalance() {
        return dailyBalance;
    }

    public void setDailyBalance(Float dailyBalance) {
        this.dailyBalance = dailyBalance;
    }

    public Float getProfitToSpend() {
        return profitToSpend;
    }

    public void setProfitToSpend(Float profitToSpend) {
        this.profitToSpend = profitToSpend;
    }

    public Float getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(Float netProfit) {
        this.netProfit = netProfit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void addChild(Node child) {
        if (!this.children.contains(child) && child != null)
            this.children.add(child);
    }

    public Idea getIdea() {
        return idea;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }

    @Override
    public String toString() {
        String json = "";
        String image = "hallo"; // DatatypeConverter.printBase64Binary(idea.getLogo());
        String descEscaped = idea.getDescription().replace("\"", "\\\"");
      Balance[] balanceArray = idea.getBalances().toArray(new Balance[idea.getBalances().size()]);
      if(idea != null) {
        json = "{ \n" +
        "\"id\": \"" + idea.getId() + "\", \n" +
        "\"topic\": \"" + idea.getTitle() + "\", \n" +
        "\"interest\": \"" + idea.getInterest() + "\", \n" +
        "\"distribution\": \"" + idea.getDistribution() + "\", \n" +
        "\"investment\": \"" + idea.getInvestment() + "\", \n" +
        "\"balances\": { \n";
            for(Balance balance : balanceArray) {
    json += "\"balance\": {" +
            "   \"id\": \"" + balance.getId() + "\", \n" +
            "   \"profit\": \"" + balance.getProfit() + "\", \n" +
            "   \"profitToSpend\": \"" + balance.getProfitToSpend() + "\", \n" +
            "   \"netProfit\": \"" + balance.getNetProfit() + "\", \n" +
            "   \"dailyBalance\": \"" + balance.getDailyBalance() + "\" \n" +
            "}";
            }
        json += "}, \n" +
        "\"direction\": \"right\", \n" +
        "\"selectedType\": \"" + idea.getIdeatype() + "\", \n" +
        "\"backgroundColor\": \"#64cfea\", \n" +
        "\"logo\": \"" + image + "\", \n" +
        "\"logoContentType\": \"" + idea.getLogoContentType() + "\", \n" +
        "\"active\": \"" + idea.isActive() + "\", \n" +
        "\"descripition\": \"" + descEscaped + "\", \n" +
        "\"children\":" + children + "\n" +
      "}";
      return json;
      } else {
        return "{ \n" +
        "\"id\": \"" + id + "\", \n" +
        "\"topic\": \"" + title + "\", \n" +
        "\"interest\": \"" + interest + "\", \n" +
        "\"distribution\": \"" + distribution + "\", \n" +
        "\"investment\": \"" + investment + "\", \n" +
        "\"profit\": \"" + profit + "\", \n" +
        "\"profitToSpend\": \"" + profitToSpend + "\", \n" +
        "\"netProfit\": \"" + netProfit + "\", \n" +
        "\"dailyBalance\": \"" + dailyBalance + "\", \n" +
        "\"direction\": \"right\", \n" +
        "\"selectedType\": \"" + type + "\", \n" +
        "\"backgroundColor\": \"#64cfea\", \n" +
        "\"logo\": \"" + logo + "\", \n" +
        "\"logoContentType\": \"" + logoContentType + "\", \n" +
        "\"active\": \"" + active + "\", \n" +
        "\"descripition\": \"" + descEscaped + "\", \n" +
        "\"children\":" + children + "\n" +
      "}";
      }
  }
}
