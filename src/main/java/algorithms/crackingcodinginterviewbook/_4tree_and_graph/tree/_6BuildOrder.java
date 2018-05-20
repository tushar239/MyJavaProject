package algorithms.crackingcodinginterviewbook._4tree_and_graph.tree;


/*
pg. 250 of Cracking Coding Interview book

Build Order:
You are given a list of projects and a list of dependencies (which is a list of pairs of projects,
where the first project is dependent on the second project).
All of a project's dependencies must be built before the project is.
Find a build order that will allow the projects to be built.
If there is no valid build order, return an error.

Example
Input
projects a, b, c, d, e, f
dependencies: (da, a), (b, f), (d, b), (a, f), (c ,d)

output: f,e,a,b,d,c

IMPORTANT:
When you are looking for topological sort order, use DFS.
 */
public class _6BuildOrder {
}
