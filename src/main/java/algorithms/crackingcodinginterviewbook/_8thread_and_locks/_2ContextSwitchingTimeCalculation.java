package algorithms.crackingcodinginterviewbook._8thread_and_locks;

/*
Context Switch:
How would you measure the time spent in a context switch?

You need to first understand below process state diagram.

States in the process, how they are moved from one to another state using Schedulers and what is Context Switching:
------------------------------------------------------------------------------------------------------------------
    https://www.youtube.com/watch?v=ucVm_arB-fw
    https://www.youtube.com/watch?v=e8YxIdwodB8


    Process State Diagram:

            LTS                     STS/dispatcher
        new state ---> ready state ------------------> running state -> termination state
                       ^ |  ^   ^                          |   |
                       | |  |   |    priority              |   |
                   ----- |  |   ----------------------------   |
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

    LTS (Long Term Scheduler) - decides number of processes to be in ready state. It should powerful because it should decide the mixture of CPU bound and I/O bound processes.
                                If there are many I/O bound processes, then CPU will be idle for a long time.
                                If there are many CPU bound processes, then CPU will be busy for a long time.
                                So, Long Term Scheduler should be smart enough to decide the mixture of both.


    STS(Short Term Scheduler) - picks up a process from ready state and moves it to running state.
    Dispatcher                - creates a PCB (process control block) that holds information about that process.

                                PCB has properties of a process
                                    - process id
                                    - priority
                                    - process state
                                    - general purpose registers
                                    - list of open files
                                    - list of open devices
                                    - protection

                                PCBs of processes are connected to each other in a linked list.

    MTS(Medium Term Scheduler) - decides when to suspend and resume a process. When higher priority process comes, MTS has to move the process form RAM to HDD and empty resources for higher priority process.
                                 This is called CONTEXT SWITCHING. Context Switching is expensive because it has to save entire context of a process that needs to be suspended and restore the context of a process that needs to be run from RAM to HDD and vice-a-versa.
                                 I/O can continue to happen in suspended state also (in HDD also).



Let's say you want to calculate the context switching time between processes P1 and P2.
Let's say context switching from P1 to P2 happens when some nTH instruction is getting executed in P1.
In this case, we need to record time on every single instruction where context switch can happen. This is very inefficient.
And also you don't have a control over OS processes that has higher priority and that can force your processes to be suspended for a while.
So, basically you cannot calculate the time perfectly.

To count approx time, you can build an environment.

P1 sends token to P2. This time is recorded.
P2 receives a token from P1 and this point context switch happens. P1 goes to waiting state and P2 starts running.
At some instruction in P2, token is sent to P1.
P1 receives a token and context switch happens again. P2 goes to waiting state and P1 starts running. This time is recorded.

By recording the time of sending and receiving the tokens from and to processes, you can roughly calculate the context switching time.
*/
public class _2ContextSwitchingTimeCalculation {
}
