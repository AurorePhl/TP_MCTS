package fr.uga.pddl4j.mcts;

import fr.uga.pddl4j.heuristics.state.StateHeuristic;
import fr.uga.pddl4j.parser.DefaultParsedProblem;
import fr.uga.pddl4j.parser.RequireKey;
import fr.uga.pddl4j.plan.Plan;
import fr.uga.pddl4j.plan.SequentialPlan;
import fr.uga.pddl4j.planners.AbstractPlanner;
import fr.uga.pddl4j.planners.Planner;
import fr.uga.pddl4j.planners.PlannerConfiguration;
import fr.uga.pddl4j.planners.ProblemNotSupportedException;
import fr.uga.pddl4j.planners.SearchStrategy;
import fr.uga.pddl4j.planners.statespace.search.StateSpaceSearch;
import fr.uga.pddl4j.problem.DefaultProblem;
import fr.uga.pddl4j.problem.Problem;
import fr.uga.pddl4j.problem.State;
import fr.uga.pddl4j.problem.operator.Action;
import fr.uga.pddl4j.problem.operator.ConditionalEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * The class implements the Monte-Carlo Tree Search (MCTS) planner with pure random walks in pddl4j.  
 * 
 * @author Laura DURET & Aurore PHILIPPE
 * @version 1.0 - 08/11/2024
 */
@CommandLine.Command(name = "PureRandomWalks",
    version = "MCTS 1.0",
    description = "Solves a specified planning problem using Monte-Carlo tree search strategy with pure random walks.",
    sortOptions = false,
    mixinStandardHelpOptions = true,
    headerHeading = "Usage:%n",
    synopsisHeading = "%n",
    descriptionHeading = "%nDescription:%n%n",
    parameterListHeading = "%nParameters:%n",
    optionListHeading = "%nOptions:%n")
public class PureRandomWalks extends AbstractPlanner {

	/**
     * The class logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(PureRandomWalks.class.getName());

    /**
     * The HEURISTIC property used for planner configuration.
     */
    public static final String HEURISTIC_SETTING = "HEURISTIC";

    /**
     * The default value of the HEURISTIC property used for planner configuration.
     */
    public static final StateHeuristic.Name DEFAULT_HEURISTIC = StateHeuristic.Name.FAST_FORWARD;

    /**
     * The EXPLORATION_HEURISTIC property used for planner configuration.
     */
    public static final String EXPLORATION_HEURISTIC_SETTING = "EXPLORATION_HEURISTIC";

    /**
     * The default value of the EXPLORATION_HEURISTIC property used for planner configuration.
     */
    public static final double DEFAULT_EXPLORATION_HEURISTIC = 1.0;

    /**
     * The NUM_WALK property used for planner configuration.
     */
    public static final String NUM_WALK_SETTING = "NUM_WALK";

    /**
     * The default value of the NUM_WALK property used for planner configuration.
     * */
    public static final int DEFAULT_NUM_WALK = 2000; 

    /**
     * The LENGTH_WALK property used for planner configuration.
     */
    public static final String LENGTH_WALK_SETTING = "LENGTH_WALK";

    /**
     * The default value of the LENGTH_WALK property used for planner configuration.
     * */
    public static final int DEFAULT_LENGTH_WALK = 10; 

    /**
     * The ALPHA property used for planner configuration.
     */
    public static final String ALPHA_SETTING = "ALPHA";

    /**
     * The default value of the ALPHA property used for planner configuration. 
     * */
    public static final double DEFAULT_ALPHA = 0.9;

    /**
     * The MAX_STEPS property used for planner configuration.
     */
    public static final String MAX_STEPS_SETTING = "MAX_STEPS";

    /**
     * The default value of the MAX_STEPS property used for planner configuration.
     * */
    private static final int DEAFULT_MAX_STEPS = 7; 


    /**
     * The exploration parameter of the heuristic.
     */
    private double c;

    /**
     * The name of the heuristic used by the planner.
     */
    private StateHeuristic.Name heuristic;

     /**
     * Creates a new A* search planner with the default configuration.
     */
    public PureRandomWalks() {
        this(PureRandomWalks.getDefaultConfiguration());
    }

    /**
     * Creates a new A* search planner with a specified configuration.
     *
     * @param configuration the configuration of the planner.
     */
    public PureRandomWalks(final PlannerConfiguration configuration) {
        super();
        this.setConfiguration(configuration);
    }

    /**
     * Sets the exploration parameter of the heuristic.
     *
     * @param exploration the exploration parameter of the heuristic. The exploration parameter must be greater than 0.
     * @throws IllegalArgumentException if the exploration parameter is strictly less than 0.
     */
    @CommandLine.Option(names = {"-exploration", "--exploration"}, defaultValue = "1.0",
        paramLabel = "<exploration>", description = "Set the exploration parameter of the heuristic (preset 1.0).")
    public void setHeuristicExploration(final double exploration) {
        if (exploration <= 0) {
            throw new IllegalArgumentException("Exploration <= 0");
        }
        this.c = exploration;
    }

    /**
     * Set the name of heuristic used by the planner to the solve a planning problem.
     *
     * @param heuristic the name of the heuristic.
     */
    @CommandLine.Option(names = {"-e", "--heuristic"}, defaultValue = "FAST_FORWARD",
        description = "Set the heuristic : AJUSTED_SUM, AJUSTED_SUM2, AJUSTED_SUM2M, COMBO, "
            + "MAX, FAST_FORWARD SET_LEVEL, SUM, SUM_MUTEX (preset: FAST_FORWARD)")
    public void setHeuristic(StateHeuristic.Name heuristic) {
        this.heuristic = heuristic;
    }

    /**
     * Returns the name of the heuristic used by the planner to solve a planning problem.
     *
     * @return the name of the heuristic used by the planner to solve a planning problem.
     */
    public final StateHeuristic.Name getHeuristic() {
        return this.heuristic;
    }

    /**
     * Returns the exploration parameter of the heuristic.
     *
     * @return the exploration parameter of the heuristic.
     */
    public final double getC() {
        return this.c;
    }

    /**
     * Returns the configuration of the planner.
     *
     * @return the configuration of the planner.
     */
    @Override
    public PlannerConfiguration getConfiguration() {
        final PlannerConfiguration config = super.getConfiguration();
        config.setProperty(PureRandomWalks.HEURISTIC_SETTING, this.getHeuristic().toString());
        config.setProperty(PureRandomWalks.EXPLORATION_HEURISTIC_SETTING, Double.toString(this.getC()));
        return config;
    }

    /**
     * Sets the configuration of the planner. If a planner setting is not defined in
     * the specified configuration, the setting is initialized with its default value.
     *
     * @param configuration the configuration to set.
     */
    @Override
    public void setConfiguration(final PlannerConfiguration configuration) {
        super.setConfiguration(configuration);
        /*if (configuration.getProperty(PureRandomWalks.EXPLORATION_HEURISTIC_SETTING) == null) {

        } else {
            this.setC(Double.parseDouble(configuration.getProperty(
                PureRandomWalks.EXPLORATION_HEURISTIC_SETTING)));
        }
        if (configuration.getProperty(PureRandomWalks.HEURISTIC_SETTING) == null) {
            this.setHeuristic(PureRandomWalks.DEFAULT_HEURISTIC);
        } else {
            this.setHeuristic(StateHeuristic.Name.valueOf(configuration.getProperty(
                PureRandomWalks.HEURISTIC_SETTING)));
        }*/
    }

    /**
     * 
     * @return the default arguments of the planner.
     * @see PlannerConfiguration
     */
    public static PlannerConfiguration getDefaultConfiguration() {
        PlannerConfiguration config = Planner.getDefaultConfiguration();
        config.setProperty(PureRandomWalks.HEURISTIC_SETTING, PureRandomWalks.DEFAULT_HEURISTIC.toString());
        config.setProperty(PureRandomWalks.EXPLORATION_HEURISTIC_SETTING,
            Double.toString(PureRandomWalks.DEFAULT_EXPLORATION_HEURISTIC));
        return config;
    }

    /**
     * Checks the planner configuration and returns if the configuration is valid.
     * A configuration is valid if (1) the domain and the problem files exist and
     * can be read, (2) the timeout is greater than 0, (3) the exploration parameter of the
     * heuristic is greater than 0 and (4) the heuristic is a not null.
     *
     * @return <code>true</code> if the configuration is valid <code>false</code> otherwise.
     */
    public boolean hasValidConfiguration() {
        return super.hasValidConfiguration()
            && this.getC() > 0.0
            && this.getHeuristic() != null;
    }


    /**
     * Instantiates the planning problem from a parsed problem.
     *
     * @param problem the problem to instantiate.
     * @return the instantiated planning problem or null if the problem cannot be instantiated.
     */
    @Override
    public Problem instantiate(DefaultParsedProblem problem) {
        final Problem pb = new DefaultProblem(problem);
        pb.instantiate();
        return pb;
    }

    /**
     * Search a solution plan to a specified domain and problem using Monte-Carlo tree search strategy with pure random walks.
     *
     * @param problem the problem to solve.
     * @return the plan found or null if no plan was found.
     */
    @Override
    public Plan solve(final Problem problem) {
    	StateSpaceSearch search = StateSpaceSearch.getInstance(SearchStrategy.Name.MCTS,
            this.getHeuristic(), this.getC(), this.getTimeout());
        /*StateSpaceSearch search = StateSpaceSearch.getInstance(SearchStrategy.Name.ASTAR,
            this.getHeuristic(), this.getHeuristicWeight(), this.getTimeout());*/
        LOGGER.info("* Starting MCTS pure random walks \n");
    	Plan plan = search.searchPlan(problem);
        // Search a solution
        final long begin = System.currentTimeMillis();
        //final Plan plan = this.mcts(problem);
        final long end = System.currentTimeMillis();
        // If a plan is found update the statistics of the planner
        // and log search information
    	if (plan != null) {
            LOGGER.info("* MCTS pure random walks succeeded\n");
            /*this.getStatistics().setTimeToSearch(search.getSearchingTime());
            this.getStatistics().setMemoryUsedToSearch(search.getMemoryUsed());*/
            this.getStatistics().setTimeToSearch(end - begin);
        } else {
            LOGGER.info("* MCTS pure random walks failed\n");
        }
        // Return the plan found or null if the search fails.
    	return plan;
    }

    /**
     * Search a solution plan for a planning problem using an Monte-Carlo tree search strategy with pure random walks.
     * 
     * Features of the Search Algorithm
     * Selection: Choose the node to explore using the UCT heuristic.
     * Expansion: Add new child nodes from the selected node.
     * Simulation: Perform random simulations to estimate reward.
     * Backpropagation: Update rewards and node visits on the return path.
     *
     * @param problem the problem to solve.
     * @return a plan solution for the problem or null if there is no solution
     * @throws ProblemNotSupportedException if the problem to solve is not supported by the planner.
     */
    public Plan mcts(Problem problem) throws ProblemNotSupportedException {
        // Check if the problem is supported by the planner
        if (!this.isSupported(problem)) {
            throw new ProblemNotSupportedException("Problem not supported");
        }

        // First we create an instance of the heuristic to use to guide the search
        final StateHeuristic heuristic = StateHeuristic.getInstance(this.getHeuristic(), problem);

        // We get the initial state from the planning problem
        final State init = new State(problem.getInitialState());

        // We initialize the closed list of nodes (store the nodes explored)
        final Set<Node> close = new HashSet<>();

        // We initialize the opened list to store the pending node according to function f
        final double weight = this.getC();
        final PriorityQueue<Node> open = new PriorityQueue<>(100, new Comparator<Node>() {
            public int compare(Node n1, Node n2) {
                double f1 = weight * n1.getHeuristic() + n1.getCost();
                double f2 = weight * n2.getHeuristic() + n2.getCost();
                return Double.compare(f1, f2);
            }
        });

        Plan plan = null;
        // Finally, we return the search computed or null if no search was found
        return plan;
    }

    /**
     * Returns if a specified problem is supported by the planner. Just ADL problem can be solved by this planner.
     *
     * @param problem the problem to test.
     * @return <code>true</code> if the problem is supported <code>false</code> otherwise.
     */
    @Override
    public boolean isSupported(Problem problem) {
        return (problem.getRequirements().contains(RequireKey.ACTION_COSTS)
            || problem.getRequirements().contains(RequireKey.CONSTRAINTS)
            || problem.getRequirements().contains(RequireKey.CONTINOUS_EFFECTS)
            || problem.getRequirements().contains(RequireKey.DERIVED_PREDICATES)
            || problem.getRequirements().contains(RequireKey.DURATIVE_ACTIONS)
            || problem.getRequirements().contains(RequireKey.DURATION_INEQUALITIES)
            || problem.getRequirements().contains(RequireKey.FLUENTS)
            || problem.getRequirements().contains(RequireKey.GOAL_UTILITIES)
            || problem.getRequirements().contains(RequireKey.METHOD_CONSTRAINTS)
            || problem.getRequirements().contains(RequireKey.NUMERIC_FLUENTS)
            || problem.getRequirements().contains(RequireKey.OBJECT_FLUENTS)
            || problem.getRequirements().contains(RequireKey.PREFERENCES)
            || problem.getRequirements().contains(RequireKey.TIMED_INITIAL_LITERALS)
            || problem.getRequirements().contains(RequireKey.HIERARCHY))
            ? false : true;
    }

    /**
     * Extracts a search from a specified node.
     *
     * @param node    the node.
     * @param problem the problem.
     * @return the search extracted from the specified node.
     */
    private Plan extractPlan(final Node node, final Problem problem) {
        Node n = node;
        final Plan plan = new SequentialPlan();
        while (n.getAction() != -1) {
            final Action a = problem.getActions().get(n.getAction());
            plan.add(0, a);
            n = n.getParent();
        }
        return plan;
    }

    /**
     * The main method of the <code>PureRandomWalks</code> planner.
     *
     * @param args the arguments of the command line.
     */
    public static void main(String[] args) {
        try {
            final PureRandomWalks planner = new PureRandomWalks();
            CommandLine cmd = new CommandLine(planner);
            cmd.execute(args);
        } catch (IllegalArgumentException e) {
            LOGGER.fatal(e.getMessage());
        }
    }
}