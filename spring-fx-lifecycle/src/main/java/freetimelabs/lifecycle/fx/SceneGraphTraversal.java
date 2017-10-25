package freetimelabs.lifecycle.fx;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;

import java.util.*;
import java.util.function.Function;

/**
 * Used to load all {@link javafx.scene.Node}
 */
public final class SceneGraphTraversal
{
    private static final Map<Class<? extends Node>, Function<? super Node, List<? extends Node>>> STRATEGIES = new HashMap<>();
    private static final Function<? super Node, List<? extends Node>> DEFAULT = node -> node instanceof Parent ? ((Parent) node).getChildrenUnmodifiable() : Collections.emptyList();

    static
    {
        STRATEGIES.put(Accordion.class, a -> ((Accordion) a).getPanes());
        STRATEGIES.put(ButtonBar.class, b -> ((ButtonBar) b).getButtons());
        STRATEGIES.put(TitledPane.class, p -> FXCollections.observableArrayList(((TitledPane) p).getContent()));
        STRATEGIES.put(ToolBar.class, b -> ((ToolBar) b).getItems());

    }

    private SceneGraphTraversal()
    {
        // Nothing
    }


    /**
     * Traverse a JavaFX hierarchy starting with the root Node of the argument scene.
     *
     * @param scene - The Scene to traverse.
     * @return A list of all nodes that are within the argument scene.
     */
    public static List<Node> load(Scene scene)
    {
        return load(scene.getRoot());
    }

    /**
     * Traverses a JavaFX hierarchy starting with the argument Node and descending through its children.
     *
     * @param node - The root Node to search for children.
     * @return A List of all Nodes that are descendant of the argument node.
     */
    public static List<Node> load(Node node)
    {
        List<Node> allNodes = new ArrayList<>();
        loadAll(node, allNodes);
        return allNodes;
    }

    private static void loadAll(Node node, List<Node> allNodes)
    {
        allNodes.add(node);
        Function<? super Node, List<? extends Node>> func = STRATEGIES.getOrDefault(node.getClass(), DEFAULT);
        func.apply(node).forEach(n -> loadAll(n, allNodes));
    }


}
