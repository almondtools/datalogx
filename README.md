DatalogX
========
DatalogX is an extension of datalog for queries:
- relational algebra operations (projection, selection, cross product, join, negation)
- aggregation operations (aggregation)
- data transformation operations (builtins)

Additionally Datalogx can be used for managing a database, providing: 
- insertions
- deletes

Traditional Datalog uses a Prolog-like Syntax. DatalogX deviates from the syntax by replacing some Prolog Operators by some that are more common in modern programming languages.

Basic Syntax
------------
Besides Syntax adjustements DatalogX is quite comparable to Datalog. A program may consist of facts, rules and queries:

// facts

son(Isaac, Abraham).
son(Jacob, Isaac).

// rules

forefather(F,S) <- son(S,F).
forefather(F,S) <- forefather(F,T) & son(S,T).

// queries

?(F) : forefather(F,Jacob);


Basic Operators, Functions, Aggregations
----------------------------------------
It was already mentioned that the syntax deviates from traditional Datalog/Prolog. So here are the main operators rules are built of.   

& : And (Conjunction, equivalent to ',' in Prolog)
| : Or (Disjunction, equivalent to ';' in Prolog)
~ : Not (Negation)

These operators may combine clauses. A clause may be:

* a relation clause, like son(X,Y), forefather(X,Y)
* a comparison clause, like X < Y
* a functional clause, like X = Y + Z, X = average(Y,Z)
* an aggregational clause, like X = average{ (Z) : relation(Y,Z) }


Extended Syntax
---------------
Additional to the different types of operators and clauses there are some operations that allow you to interact with an underlying database:

* + <fact or rule>: add <fact or rule>
* - <fact or rule>: remove <fact or rule>
* ? <query>: evaluate query  