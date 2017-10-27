/*
 * MIT License
 *
 * Copyright (c) [2017] [Jake]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package freetimelabs.lifecycle.fx;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
        STRATEGIES.put(TitledPane.class, p -> nullableNode(((TitledPane) p)::getContent));
        STRATEGIES.put(ToolBar.class, b -> ((ToolBar) b).getItems());
        STRATEGIES.put(TabPane.class, t -> ((TabPane) t).getTabs()
                                                        .stream()
                                                        .map(Tab::getContent)
                                                        .filter(Objects::nonNull)
                                                        .collect(Collectors.toList()));
        STRATEGIES.put(Labeled.class, l -> nullableNode(((Labeled) l)::getGraphic));
        STRATEGIES.put(SplitPane.class, s -> ((SplitPane) s).getItems());
    }

    private static List<Node> nullableNode(Supplier<Node> supplier)
    {
        Node node = supplier.get();
        return Objects.nonNull(node) ? Arrays.asList(node) : Collections.emptyList();
    }

    private SceneGraphTraversal()
    {
        // No instance
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
        func.apply(node)
            .forEach(n -> loadAll(n, allNodes));
    }
}
