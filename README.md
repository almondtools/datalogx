DatalogX
========

DatalogX is an extension of datalog for queries:
- relational algebra operations (projection, selection, cross product, join, negation)
- aggregation operations (aggregation)
- data transformation operations (builtins)

Unlike Datalog DatalogX has its own syntax for manipulating the extensional database.  
- insertions/adds
- retractions/deletes

DatalogX basically consists (as Datalog) of facts, rules and queries:

* facts describe known explicit knowledge (extensional database)
* rules describe how knowledge could be derived (intensional database)
* queries describe results to get from the database

Traditional Datalog uses a Prolog-like Syntax. DatalogX extends Datalog semantics, but yet deviates from the syntax (the cryptic prolog operators are replaced by more common operators form modern programming languages).

Descriptive Operators
---------------------

We start with the __descriptive operators__, these are used for modelling relations between data. They are implicitly applied (triggered on database change or query, dependent on the database system). All effects of applying such an operator is reflected in the result of the operation.

The descriptive operators are:

``` 
    <-  : Follows from (equivalent to :- in Prolog)
    &   : And (Conjunction, equivalent to `,` in Prolog)
    |   : Or (Disjunction, equivalent to `;` in Prolog)
    ~   : Not (Negation)
```

These operators may combine clauses. A clause may be:

* a relation clause, like `son(X,Y)`, `forefather(X,Y)`
* a comparison clause, like `X < Y`
* a functional clause, like `X = Y + Z`, `X = average(Y,Z)`
* an aggregational clause, like `X = average{ (Z) : relation(Y,Z) }`

Query Operators
-----------------

Next we describe the __query operators__. Such operators are explicitly applied, yet they do not have side effects, i.e. every effect is reflected in the result of the applied operation.

The query operators are:

```
    ? <query>           : evaluate query  
```

Update Operators
---------------

Next we describe the __update operators__. Such operators are explicitly applied, and they rely heavily on side effects, i.e. the effect by applying such and operation might be realized not in the result of the operation but in the result of later queries.

The update operators are:

```
    + <fact or rule>    : add <fact or rule>
    - <fact or rule>    : remove <fact or rule>
```

Note that updates as in SQL are conceptually not supported but practically easily reflected by combining an add and a remove.

Examples
--------

A good sample for a changing database is the ancestry of the elven characters in J.R.R. Tolkiens Silmarillion.

### Facts

```prolog
son("Fingon", "Fingolfin");
son("Argon", "Fingolfin");
son("Orodreth", "Fingon");
son("Gil-Galad", "Orodreth");
```

### Rules

```prolog
forefather(F,S) <- son(S,F);
forefather(F,S) <- forefather(F,T) & son(S,T);
```

### Query

```prolog
?(F) <- forefather(F,"Gil-Galad");
```

### Add/Remove

```prolog
- son("Orodreth", "Fingon"); // according to later sources
+ son("Orodreth", "Argon"); // according to later sources
```


