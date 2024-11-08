# TP_MCTS
### Implementation of the Monte-Carlo Tree Search (MCTS) planner with pure random walks in pddl4j for an university work.
Date : 8th November 2024  
Location : UFR SHS, Université Grenoble Alpes, Saint-Martin-d'Hères, France   
Authors : DURET Laura et PHILIPPE Aurore  

---
# Results


## Blocks
```
ASP - Temps d'exécution pour p001.pddl: 5405.38 ms
ASP - Taille du plan pour p001.pddl: 6 actions
MCTS - Temps d'exécution pour p001.pddl: 4714.31 ms
MCTS - Taille du plan pour p001.pddl: 7 actions
ASP - Temps d'exécution pour p005.pddl: 4965.99 ms
ASP - Taille du plan pour p005.pddl: 10 actions
MCTS - Temps d'exécution pour p005.pddl: 5453.75 ms
MCTS - Taille du plan pour p005.pddl: 13 actions
```


## Depots

```
ASP - Temps d'exécution pour p01.pddl: 6477.78 ms
ASP - Taille du plan pour p01.pddl: 10 actions
MCTS - Temps d'exécution pour p01.pddl: 7511.97 ms
MCTS - Taille du plan pour p01.pddl: 11 actions
ASP - Temps d'exécution pour p02.pddl: 8859.37 ms
ASP - Taille du plan pour p02.pddl: 15 actions
MCTS - Temps d'exécution pour p02.pddl: 8583.88 ms
MCTS - Taille du plan pour p02.pddl: 20 actions
```

## Gripper
```
ASP - Temps d'exécution pour p02.pddl: 5110.83 ms
ASP - Taille du plan pour p02.pddl: 17 actions
MCTS - Temps d'exécution pour p02.pddl: 6855.44 ms
MCTS - Taille du plan pour p02.pddl: 12 actions
ASP - Temps d'exécution pour p05.pddl: 86121.27 ms
ASP - Taille du plan pour p05.pddl: 35 actions
MCTS - Temps d'exécution pour p05.pddl: 6343.06 ms
MCTS - Taille du plan pour p05.pddl: 0 actions
```


## Logistics
```
ASP - Temps d'exécution pour p01.pddl: 13453.41 ms
ASP - Taille du plan pour p01.pddl: 25 actions
MCTS - Temps d'exécution pour p01.pddl: 14852.68 ms
MCTS - Taille du plan pour p01.pddl: 0 actions
ASP - Temps d'exécution pour p05.pddl: 19409.60 ms
ASP - Taille du plan pour p05.pddl: 21 actions
MCTS - Temps d'exécution pour p05.pddl: 18273.10 ms
MCTS - Taille du plan pour p05.pddl: 0 actions
```

# Run MCTS examples 

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

# Some exemples 

MCTS with depots :
```
java -cp "build/classes;build/libs/pddl4j-4.0.0.jar" fr.uga.pddl4j.mcts.PureRandomWalks src/test/resources/benchmarks/pddl/ipc2002/depots/strips-automatic/domain.pddl  src/test/resources/benchmarks/pddl/ipc2002/depots/strips-automatic/p01.pddl
```
ASP with depots : 
```
java -cp "build/classes;build/libs/pddl4j-4.0.0.jar" fr.uga.pddl4j.examples.asp.ASP src/test/resources/benchmarks/pddl/ipc2002/depots/strips-automatic/domain.pddl src/test/resources/benchmarks/pddl/ipc2002/depots/strips-automatic/p01.pddl -e FAST_FORWARD -w 1.2 -t 1000
```


---

