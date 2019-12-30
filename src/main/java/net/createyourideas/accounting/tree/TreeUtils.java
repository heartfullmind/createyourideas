package net.createyourideas.accounting.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeUtils {

     //Private method to create the ideafunnel-tree (node-tree)
     public static String createTree(List<Node> nodes) {
 
        Map<String, Node> mapTmp = new HashMap<>();
        
        //Save all nodes to a map
        for (Node current : nodes) {
            mapTmp.put(current.getId(), current);
        }
 
        //loop and assign parent/child relationships
        for (Node current : nodes) {
            String parentId = current.getParentId();
 
            if (parentId != null) {
                Node parent = mapTmp.get(parentId);
                if (parent != null) {
                    current.setParent(parent);
                    parent.addChild(current);
                    mapTmp.put(parentId, parent);
                    mapTmp.put(current.getId(), current);
                }
            }
 
        }
 
    
        //get the root
        Node root = null;
        for (Node node : mapTmp.values()) {
            if(node.getParent() == null) {
                root = node;
                break;
            }
        }
 
        return root.toString();
    }
}
