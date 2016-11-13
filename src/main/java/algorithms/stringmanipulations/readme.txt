String immutability
-------------------
http://stackoverflow.com/questions/1552301/immutability-of-strings-in-java

String is immutable and final. String reference's value cannot be changed and it cannot be overridden by another class.
If you try to change the value of string, it creates another reference.

JVM uses String Pool to store string literals. String literals are cached in pool. Strings are widely used in java, so it makes sense to cache them.
If you don't want to used cached value, use 'new String("hi")'.

Why String has to be immutable?
if string is mutable and if
s1="hi"
s2="hi"
then both s1 and s2 points to the same reference of "hi" stored in string pool.
when you do s1.toUppercase(), it would change the same reference that is shared by s1 and s2, which should not happen.
So, strings are immutable.


In String algorithms, never create another string because String is immutable. Every time, you do
String s1="hi"
s1= "hello"
it will actually create a new string object for "hello" and give s1 for garbage collection. Original s1 won't just change its point from "hi" to "hello" memory reference.

String str = new String();
This creates a new "remote control" called "str" and sets that to the value new String() (or "").

e.g. in memory this creates:

str --- > ""

str  = "Hello";
This then changes the remote control "str" but does not modify the original string "".

e.g. in memory this creates:

str -+   ""
     +-> "Hello"


str = "Help!";

This then changes the remote control "str" but does not modify the original string "" or the object that the remote control currently points to.

e.g. in memory this creates:

str -+   ""
     |   "Hello"
     +-> "Help!"

The string object that was first referenced by str was not altered, all that you did was make str refer to a new string object.
The variable str is a reference to an object, when you assign a new value to str you aren't changing the value of the object it references, you are referencing a different object.


String's intern() method
------------------------
String s1 = "hi"
String s2 = "hi"

One String object for "hi" will be created and added to String pool. Both s1 and s2 will reference to same object.
Both s1 and s2 will point to the same memory reference, so s1 == s2 will return true.

String s1 = new String("hi")
String s2 = new String("hi")
In this case, two String objects will be added to String pool. s1 and s2 will refer to different objects. So, s1 == s2 will return false, but s1.equals(s2) will return true.
System.out.println(s1 == s2); // false
System.out.println(s1.intern() == s2.intern()); // true - same as s1.equals(s2)
when you say String s1 = new String("hi").intern();, it will search "hi" into string pool and assign its reference to s1.


StringBuffer vs StringBuilder
StringBuffer is thread-safe and StringBuilder is not, so StringBuilder is faster.

+ vs string.concat() vs StringBuilder.append()
Before, it was discouraged to use + operator because it used to create lot many intermediate string objects.
But now + operator internally uses StringBuilder.append()
String.concat() is bad because it creates intermediate string objects that + operator used to do before.
Another thing is you cannot do "abc".concat(100). string cannot be concatenated with non-string, but you can use + operator or StringBuilder for that.


String algorithms
----------------

Rule of thumb:
    - Never ever work directly on String. Do not think of in-place manipulation for Strings because Strings are immutable. Any manipulation in String will result in new String object. Which will increase a space complexity of your algorithm.
    - use char[] or StringBuilder to store chars from string and operate on this array.
    - use str.charAt(index)
    - use str.toCharArray() - Always, try to convert String into CharArray and if required, then convert final CharArray into new String object.
    - use Arrays.sort(charArray) to sort char array // it uses 3way Quick Sort. so it takes only O(nlog n) execution time.
    - If you need a count of characters inside String, then create an array of ints with index mentioned by those characters and keep the count of those characters as array value.
    This is how basically you are maintaining a HashTable with keys as characters and values as count of those characters.
    Do not use Java's HashTable in algorithms because HashTable uses Separate Chaining algorithm to store key-value pairs that may increase insert/delete/search time.

 Remember: Total number of ASCII values are 128 that includes numbers, A-Z, a-z, special characters
                  A-Z are 65 to 90
                  a-z are 97 to 122
                  0-9 are 48 to 57