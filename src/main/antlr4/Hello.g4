grammar Hello;

// parser rule
hello : 'hi' HELLOWORLDCOMMAIDWSID;

// lexers (tokens)
HELLOWORLD : 'hello world';

ID : [a-z]+;

WS : [\t\n]+ -> skip; // do not consider tabs and new lines as a token

COMMA : ',' -> skip; // do not consider , as a token