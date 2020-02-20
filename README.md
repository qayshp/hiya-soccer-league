![Scala CI](https://github.com/qayshp/hiya-soccer-league/workflows/Scala%20CI/badge.svg?branch=master)

# Soccer League Ranker

Doing this as a code sample for Hiya.

Note that this was kinda rushed, and I've been in the C# .NET world for ~2 years, so please excuse my rusty
non-idiomatic code, use of mutables, vars, and standard iteration.

## Use

This is a standard sbt/Scala project, so you can build and run it in any way that you are familiar.
One of many ways is to use `sbt run` from the top level directory (hint: where this file is).
The project currently reads from `stdin`, so you'll need to pipe, redirect, paste, or type your input.

For example:
1. ```cat src/test/resources/input-sample.txt | sbt run```  

2. ```sbt run < src/test/resources/input-sample.txt```

2. ```sbt run``` and type or copy/pasta, followed by a `ctrl+d` for `eof` on *nix.

## Considerations

The input is streamed and not completely read into memory, allowing for relatively large input.

It is setup for automated testing based on input and output files.

Debug logging to help verify behaviour.

## Known Limitations

Currently, the work is all sequential (as a part of reading `stdin`) but could be parallelized if instead it had
consumed a file. This would require some changes in how the overall league score is tallied and shared between or
collected from various nodes or threads.

Also, scores in matches and league wins/losses are `Int`s, which should be fine for real-world use, but could have been
larger.

Readability of tuples... I didn't get around to refactoring and using `Types` or `case classes` for everything I
would have liked to.

Also didn't get to break out as many individually testable methods.

Logging is minimal.

Planned but unused sbt dependencies from additional pieces I wanted to implement.

## Example Run
```
➜  hiya-soccer-league git:(master) ✗ sbt run < src/test/resources/input-sample.txt 
[info] Loading global plugins from /Users/qpoonawala/.sbt/1.0/plugins
[info] Loading project definition from /Users/qpoonawala/IdeaProjects/hiya-soccer-league/project
[info] Loading settings for project hiya-soccer-league from build.sbt ...
[info] Set current project to hiya-soccer-league (in build file:/Users/qpoonawala/IdeaProjects/hiya-soccer-league/)
[info] Compiling 1 Scala source to /Users/qpoonawala/IdeaProjects/hiya-soccer-league/target/scala-2.13/classes ...
[info] Done compiling.
[info] running Ranking 
10:33:54.635 [run-main-0] DEBUG Ranking$ - Match between Robots and Spammers
Winner: tie

10:33:54.637 [run-main-0] DEBUG Ranking$ - Match between Thieves and FC Fraudsters
Winner: Thieves

10:33:54.637 [run-main-0] DEBUG Ranking$ - Match between Robots and FC Fraudsters
Winner: tie

10:33:54.637 [run-main-0] DEBUG Ranking$ - Match between Thieves and Spammers
Winner: Thieves

10:33:54.637 [run-main-0] DEBUG Ranking$ - Match between Robots and Grandparents
Winner: Robots

10:33:54.638 [run-main-0] DEBUG Ranking$ - HashMap(FC Fraudsters -> 1, Spammers -> 1, Robots -> 5, Thieves -> 6, Grandparents -> 0)
10:33:54.639 [run-main-0] DEBUG Ranking$ - List(((Thieves,6),0), ((Robots,5),1), ((FC Fraudsters,1),2), ((Spammers,1),3), ((Grandparents,0),4))
1. Thieves, 6 pts
2. Robots, 5 pts
3. FC Fraudsters, 1 pt
3. Spammers, 1 pt
5. Grandparents, 0 pts
[success] Total time: 3 s
```
