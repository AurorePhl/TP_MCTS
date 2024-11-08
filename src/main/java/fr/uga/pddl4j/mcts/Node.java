package fr.uga.pddl4j.mcts;

import fr.uga.pddl4j.problem.State;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a node of the tree search.
 *
 * @author DURET Laura & PHILIPPE Aurore
 * @version 1.0 - 08/11/2024
 */

public final class Node extends State {

    /**
     * The parent node of this node.
     */
    private Node parent;

    /**
     * The list of children nodes of this node.
     */
    private List<Node> children;

    /**
     * The number of times this node has been visited.
     */
    private int visits;

    /**
     * The reward accumulated for this node.
     */
    private double reward;

    /**
     * The cost to reach this node from the root node.
     */
    private double cost;

    /**
     * The action apply to reach this node.
     */
    private int action;

    /**
     * The evaluation of random walk end-states. UCT ? 
     */
    private double heuristic;

    /**
     * The depth of the node.
     */
    private int depth;


    /**
     * Creates a new node from a specified state.
     *
     * @param state the state.
     */
    public Node(State state) {
        super(state);
    }

    /**
     * Creates a new node with a specified state, parent node, children nodes, 
     * operator, reward, number of visits and heuristic value.
     *
     * @param state     the logical state of the node.
     * @param parent    the parent node of the node.
     * @param children  the list of children nodes of the current node
     * @param action    the action applied to reached the node from its parent.
     * @param heuristic the evaluation of random walk end-states.
     */
    public Node(State state, Node parent, List<Node> children, int action, double heuristic) {
        super(state);
        this.parent = parent;
        this.children = children != null ? children : new ArrayList<>();
        this.action = action;
        this.reward = 0.0;
        this.visits = 0;
        this.heuristic = heuristic;
        this.depth = -1;
    }

    /**
     * Creates a new node with a specified state, parent node, children nodes, 
     * operator, reward, number of visits, depth and heuristic value.
     *
     * @param state     the logical state of the node.
     * @param parent    the parent node of the node.
     * @param children  the list of children nodes of the current node
     * @param action    the action applied to reached the node from its parent.
     * @param depth     the depth of the node.
     * @param heuristic the evaluation of random walk end-states.
     */
    public Node(State state, Node parent, List<Node> children, int action, int depth, double heuristic) {
        super(state);
        this.parent = parent;
        this.children = children != null ? children : new ArrayList<>();
        this.action = action;
        this.reward = 0.0;
        this.visits = 0;
        this.depth = depth;
        this.heuristic = heuristic;
    }

    /**
     * Returns the action applied to reach the node.
     *
     * @return the action applied to reach the node.
     */
    public final int getAction() {
        return this.action;
    }

    /**
     * Sets the action applied to reach the node.
     *
     * @param action the action to set.
     */
    public final void setAction(final int action) {
        this.action = action;
    }

    /**
     * Returns the parent node of the node.
     *
     * @return the parent node.
     */
    public final Node getParent() {
        return parent;
    }

    /**
     * Sets the parent node of the node.
     *
     * @param parent the parent to set.
     */
    public final void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Returns the list of children nodes of the current node.
     *
     * @return the list of children nodes of the current node.
     */
    public final List<Node> getChildren() {
        return this.children;
    }

    /**
     * Add a child at the list of children nodes of the current node.
     *
     * @param child the child to add.
     */
    public final void addChild(Node child) {
        child.setParent(this);
        this.children.add(child);
    }

    /**
     * Returns the reward accumulated for the current node.
     *
     * @return the reward accumulated for the current node.
     */
    public final double getReward() {
        return reward;
    }

    /**
     * Add a reward at the reward accumulated for the current node.
     *
     * @param reward the reward to add.
     */
    public final void addReward(double reward) {
        this.reward += reward;
    }

    /**
     * Returns the cost to reach the node from the root node.
     *
     * @return the cost to reach the node from the root node.
     */
    public final double getCost() {
        return cost;
    }

    /**
     * Sets the cost needed to reach the node from the root node.
     *
     * @param cost the cost needed to reach the node from the root nod to set.
     */
    public final void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Returns the number of times the current node has been visited.
     *
     * @return the number of times the current node has been visited.
     */
    public final int getVisits() {
        return this.visits;
    }

    /**
     * Increment the number of times the current node has been visited.
     *
     */
    public final void incrementVisits() {
        this.visits++;
    }


    /**
     * Returns the evaluation of random walk end-states. 
     *
     * @return the evaluation of random walk end-states.
     */
    public final double getHeuristic() {
        return heuristic;
    }

    /**
     * Sets the estimated distance to the goal from the node.
     *
     * @param estimates the estimated distance to the goal from the node to set.
     */
    public final void setHeuristic(double estimates) {
        this.heuristic = estimates;
    }

    /**
     * Calculate the UCT value and set the cost value.
     *
     * @param c the exploration parameter.
     */
    public final void calculateUCT(final double c) {
        if (this.visits == 0)
            return Double.MAX_VALUE; 
        double exploitation = this.reward / this.visits;
        double exploration = c * Math.sqrt(Math.log(this.parent.getVisits()) / this.visits);
        this.setCost(exploitation + exploration);
    }

    /**
     * Returns the depth of this node.
     *
     * @return the depth of this node.
     */
    public int getDepth() {
        return this.depth;
    }

    /**
     * Set the depth of this node.
     *
     * @param depth the depth of this node.
     */
    public void setDepth(final int depth) {
        this.depth = depth;
    }

}
