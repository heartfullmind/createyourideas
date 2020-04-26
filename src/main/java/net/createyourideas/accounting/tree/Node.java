package net.createyourideas.accounting.tree;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import net.createyourideas.accounting.domain.Idea;

public class Node {
    private String id; // Current node id
    private String parentId; // Parent node id

    private Node parent;
    private List<Node> children;

    private String logo;

    private Idea idea;

    public Node() {
        super();
        this.children = new ArrayList<>();
    }

    public Node(String childId, String parentId, String title, Float interest, Float distribution, Float investment,
            String type, String description, String logo, Boolean active, String logoContentType) {
        this.id = childId;
        this.parentId = parentId;
        this.children = new ArrayList<>();
        this.logo = logo;
    }

    public Node(String id, String parentId, Idea idea) {
        this.id = id;
        this.parentId = parentId;
        this.idea = idea;
        this.children = new ArrayList<>();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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
        String image = DatatypeConverter.printBase64Binary(idea.getLogo());
        String descEscaped = idea.getDescription().replace("\"", "\\\"");

        json = "{ \n" +
        "\"id\": \"" + idea.getId() + "\", \n" +
        "\"topic\": \"" + idea.getTitle() + "\", \n" +
        "\"interest\": \"" + idea.getInterest() + "\", \n" +
        "\"distribution\": \"" + idea.getDistribution() + "\", \n" +
        "\"investment\": \"" + idea.getInvestment() + "\", \n" +
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
    }
}
