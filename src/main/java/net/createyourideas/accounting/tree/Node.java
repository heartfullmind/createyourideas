package net.createyourideas.accounting.tree;

import java.util.ArrayList;
import java.util.List;

public class Node {
  private String id;           //Current node id
  private String parentId;     //Parent node id

  private String value;
  private Float interest;
  private Float distribution;
  private Float investment;
  private Float profit;
  private Node parent;
  private String type;
  private byte[] logo;
  private String description;
  private Boolean active;

  private List<Node> children;

  public Node() {
      super();
      this.children = new ArrayList<>();
  }

  public Node(String value, Float interest, Float distribution, Float investment, String type, String childId, String parentId, String description, byte[] logo, Boolean active) {
      this.value = value;
      this.interest = interest;
      this.distribution = distribution;
      this.investment = investment;
      this.id = childId;
      this.parentId = parentId;
      this.children = new ArrayList<>();
      this.type = type;
      this.logo = logo;
      this.description = description;
      this.active = active;
  }

  public String getValue() {
      return value;
  }

  public void setValue(String value) {
      this.value = value;
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

  public byte[] getLogo() {
    return logo;
}

public void setLogo(byte[] logo) {
    this.logo = logo;
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

  @Override
  public String toString() {
      return "{ \n" +
               "\"id\": \"" + id + "\", \n" +
               "\"topic\": \"" + value + "\", \n" +
               "\"interest\": \"" + interest + "\", \n" +
               "\"distribution\": \"" + distribution + "\", \n" +
               "\"investment\": \"" + investment + "\", \n" +
               "\"profit\": \"" + profit + "\", \n" +
               "\"direction\": \"right\", \n" +
               "\"selectedType\": \"" + type + "\", \n" +
               "\"backgroundColor\": \"#64cfea\", \n" +
               "\"children\":" + children + "\n" +
             "}";
  }
}
