# TP_MCTS
### Implementation of the Monte-Carlo Tree Search (MCTS) planner with pure random walks in pddl4j for an university work.
Date : 8th November 2024  
Location : UFR SHS, Université Grenoble Alpes, Saint-Martin-d'Hères, France   
Authors : DURET Laura et PHILIPPE Aurore  

---
## Results

# Depots

ASP - Temps d'exécution pour p01.pddl: 6477.78 ms
ASP - Taille du plan pour p01.pddl: 10 actions
MCTS - Temps d'exécution pour p01.pddl: 7511.97 ms
MCTS - Taille du plan pour p01.pddl: 11 actions
ASP - Temps d'exécution pour p02.pddl: 8859.37 ms
ASP - Taille du plan pour p02.pddl: 15 actions
MCTS - Temps d'exécution pour p02.pddl: 8583.88 ms
MCTS - Taille du plan pour p02.pddl: 20 actions


## Run MCTS examples 

```
java -cp build/classes;build/libs/pddl4j-4.0.0.jar fr.uga.pddl4j.mcts.PureRandomWalks --help 
```

```
java -cp build/classes;build/libs/pddl4j-4.0.0.jar fr.uga.pddl4j.mcts.PureRandomWalks ^
src/test/resources/benchmarks/pddl/ipc2002/depots/strips-automatic/domain.pddl ^
src/test/resources/benchmarks/pddl/ipc2002/depots/strips-automatic/p01.pddl
```
# Run scripts 

## Verify planners

ASP : 
```
 javac -d build/classes -cp build/libs/pddl4j-4.0.0.jar src\main\java\fr\uga\pddl4j\examples\asp\ASP.java  src\main\java\fr\uga\pddl4j\examples\asp\Node.java 
```
MCTS : 
```
javac -d build/classes -cp build/libs/pddl4j-4.0.0.jar src\main\java\fr\uga\pddl4j\mcts\PureRandomWalks.java  src\main\java\fr\uga\pddl4j\mcts\Node.java
```

## Execute script  

```
python src/scripts/scriptDepots.py
```
---

## Folder contents
Please find the java scripts for our planner in the “mcts” folder, the analysis scripts, i.e. for comparison and visualization, in the “scripts” folder, our report in the “doc” folder and the figures used in our report in the “figs” folder. 
