package algorithms._8thread_and_locks;

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


States in the process, how they are moved from one to another state using Schedulers and what is Context Switching:
https://www.youtube.com/watch?v=ucVm_arB-fw


Process State Diagram:

        LTS                     STS/dispatcher
    new state ---> ready state ------------------> running state -> termination state
                   ^ | ^    ^                          |   |
                   | |  |   |    priority              |   |
               ----- |  |   ---------------------------    |
               |     |  |       time quantum               |
               |     |  |                                  |
             r |  s  |  |                                  |
             e |  u  |  ----waiting for i/o state ----------
             s |  s  |         ^       |
             u |  p  |       r |     s |  MTS does
             m |  e  |       e |     u |  this job
             e |  n  |       s |     s |
               |  d  |       u |     p |
               |     |       m |     e |
               |     |       e |     n |
               |     v         |     d v
    suspend ready state <----  suspend wait state

Processes are of two types - CPU bound  (it requires more CPU to complete)
                           - I/O bound  (it has more I/O operations. For I/O operations, process is taken out from 'running' state to 'waiting for i/o' state. once i/o operation is done, it is brought back to 'ready' state, not running state.)

LTS (Long Term Scheduler) decides number of processes to be in ready state. It should powerful because it should decide the mixture of CPU bound and I/O bound processes.
If there are many I/O bound processes, then CPU will be idle for a long time.
If there are many CPU bound processes, then CPU will be busy for a long time.
So, Long Term Scheduler should be smart enough to decide the mixture of both.


STS(Short Term Scheduler) picks up a process from ready state and moves it to running state. Dispatcher creates a PCB (process control block) that holds information about that process.
PCB has properties of a process
- process id
- priority
- process state
- general purpose registers
- list of open files
- list of open devices
- protection

PCBs of processes are connected to each other in a linked list.

MTS(Medium Term Scheduler) decides when to suspend and resume a process. When higher priority process comes, MTS has to move the process form RAM to HDD and empty resources for higher priority process.
This is called CONTEXT SWITCHING. Context Switching is expensive because it has to save entire context of a process that needs to be suspended and restore the context of a process that needs to be run from RAM to HDD and vice-a-versa.

*/

public class _1ProcessVsThread {
}
