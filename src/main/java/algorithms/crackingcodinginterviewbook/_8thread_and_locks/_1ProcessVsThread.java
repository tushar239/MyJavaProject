package algorithms.crackingcodinginterviewbook._8thread_and_locks;

/*

what is the difference between process and thread?
A process is an independent entity to which system resources (e.g. CPU and memory) are allocated.
Each process has its own address space. One process cannot access the variable and data structures of another process.
If a process wishes to access another process' resources, inter-process communications have to be used.
This include pipes, files, sockets, messaging etc.

A thread exists within a process and shares the process' resources (including its heap space).
Multiple threads within the same process will share the same heap space. This is very different from processes.
Each thread still has its own registers and its own stack, but other threads can read and write the heap memory.
When one thread modifies a process resource, the change is immediately visible to sibling threads.


Program vs Process:
    https://www.youtube.com/watch?v=ucVm_arB-fw

Process vs Thread:
    https://www.youtube.com/watch?v=brdp6d6KDso
*/

public class _1ProcessVsThread {
}
