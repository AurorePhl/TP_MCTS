# TP_MCTS
### Implementation of the Monte-Carlo Tree Search (MCTS) planner with pure random walks in pddl4j for an university work.
Date : 8th November 2024  
Location : UFR SHS, Université Grenoble Alpes, Saint-Martin-d'Hères, France   
Authors : DURET Laura et PHILIPPE Aurore  

---

## Run examples on windows cmd

```
java -cp classes;lib/pddl4j-4.0.0.jar fr.uga.pddl4j.mcts.PureRandomWalks --help 
```

```
java -cp classes;lib/pddl4j-4.0.0.jar fr.uga.pddl4j.mcts.PureRandomWalks ^
src/test/resources/benchmarks/pddl/ipc2002/depots/strips-automatic/domain.pddl ^
src/test/resources/benchmarks/pddl/ipc2002/depots/strips-automatic/p01.pddl ^
-e FAST_FORWARD ^
-w 1.2 ^
-t 1000
```

---

## Folder contents
Please find the java scripts for our planner in the “mcts” folder, the analysis scripts, i.e. for comparison and visualization, in the “analysis” folder, our report in the “doc” folder and the figures used in our report in the “figs” folder. 
