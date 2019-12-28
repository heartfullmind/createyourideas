package net.createyourideas.accounting.tree;

public class Pair {
    private Long childId ;
    private Long parentId;
    private String title;


    public Pair() {
        this.childId = null;
        this.parentId = null;
        this.title = "";
    }

    public Pair(final Long childId, final Long parentId, final String title) {
        this.childId = childId;
        this.parentId = parentId;
        this.title = title;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(final Long childId) {
        this.childId = childId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(final Long parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

}