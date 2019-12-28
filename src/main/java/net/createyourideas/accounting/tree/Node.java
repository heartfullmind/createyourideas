package net.createyourideas.accounting.tree;

import java.util.ArrayList;
import java.util.List;

public class Node {
  private String id;           //Current node id
  private String parentId;     //Parent node id

  private String value;
  private Node parent;

  private List<Node> children;

  public Node() {
      super();
      this.children = new ArrayList<>();
  }

  public Node(String value, String childId, String parentId) {
      this.value = value;
      this.id = childId;
      this.parentId = parentId;
      this.children = new ArrayList<>();
  }

  public String getValue() {
      return value;
  }

  public void setValue(String value) {
      this.value = value;
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
               "\"direction\": \"right\", \n" +
               "\"selectedType\": false, \n" +
               "\"backgroundColor\": \"#64cfea\", \n" +
               "\"children\":" + children + "\n" +
             "}";
  }
}