package algorithms._6sort__search_merge.reordering;

// p.g. 398 of Cracking Coding Interview book.

/*
abc, bac, cab are anagrams because by changing the positions characters in these strings, they can be made identical to each other

You have an array of words/string, you need to put all anagrams next to each other in a array.

A=[abc,def,bac,efd,xyz,cab]
result should be
A=[abc,bac,cab,def,efd,xyz]

Solution:
convert each element to char[] and sort them using Arrays.sort. Make sorted word a key of a hashmap.

Map<String, List<String>> map = new HashMap<>();

step 1
    sort 'abc', which will be 'abc'
    key='abc', value = nilList ['abc']

step 2
    sort 'def', which will be 'def'
    key='def', value = nilList ['def']

step 3
    sort 'bac', which will be 'abc'
    key='abc', value = nilList ['abc','bac']

step 4
    sort 'cab', which will be 'abc'
    key='abc', value = nilList ['abc','bac','cab']

step 4
    sort 'efd', which will be 'def'
    key='def', value = nilList ['def','efd']
and so on

resulting map
map = 'abc' -> nilList['abc','bac','cab']
      'def' -> nilList ['def','efd']
      'xyz' -> nilList ['xyz']

put these lists to an array
   A=['abc','bac','cab','def','efd','xyz']

 */

public class GroupAnagrams {
}
